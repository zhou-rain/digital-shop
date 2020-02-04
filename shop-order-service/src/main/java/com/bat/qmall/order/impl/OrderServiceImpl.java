package com.bat.qmall.order.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bat.qmall.Const.MqConst;
import com.bat.qmall.utils.ActiveMQUtil;
import com.bat.qmall.utils.RedisUtil;
import com.bat.shop.api.bean.oms.OmsCartItem;
import com.bat.shop.api.bean.oms.OmsOrder;
import com.bat.shop.api.bean.oms.OmsOrderItem;
import com.bat.shop.api.mapper.oms.OmsCartItemMapper;
import com.bat.shop.api.mapper.oms.OmsOrderItemMapper;
import com.bat.shop.api.mapper.oms.OmsOrderMapper;
import com.bat.shop.api.service.oms.OrderService;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.jms.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 15:03
 * @function: 订单服务
 */
@Service
@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	OmsOrderMapper omsOrderMapper;
	@Autowired
	OmsOrderItemMapper omsOrderItemMapper;
	@Autowired
	OmsCartItemMapper omsCartItemMapper;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	ActiveMQUtil activeMQUtil;

	/**
	 * 生成交易码
	 * 有效时间10分钟
	 *
	 * @param memberId
	 * @param orderList
	 * @return
	 */
	@Override
	public String createTradeCode(Integer memberId, List<OmsCartItem> orderList) {

		try (Jedis jedis = redisUtil.getJedis()) {

			String tradeCodeKey = getTradeCodeKey(memberId, orderList);
			String tradeCodeValue = UUID.randomUUID().toString();

			jedis.setex(tradeCodeKey, 60 * 10, tradeCodeValue);

			return tradeCodeValue;
		}

	}

	/**
	 * 检查交易码
	 *
	 * @param memberId
	 * @param orderList
	 * @return
	 */
	@Override
	public boolean checkTradeCode(Integer memberId, List<OmsCartItem> orderList, String tradeCode) {


		String tradeCodeKey = getTradeCodeKey(memberId, orderList);
		try (Jedis jedis = redisUtil.getJedis()) {

			//String tradeCodeFromCache = jedis.get(tradeCodeKey);// 使用lua脚本在发现key的同时将key删除，防止并发订单攻击

			String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
			Long eval = (Long) jedis.eval(
					script,
					Collections.singletonList(tradeCodeKey),
					Collections.singletonList(tradeCode)
			);

			if (eval != null && eval != 0) {
				//jedis.del(tradeCodeKey);
				return true;
			} else {
				return false;
			}

		}
	}

	/**
	 * 保存订单
	 * @param omsOrder
	 */
	@Override
	public void saveOrder(OmsOrder omsOrder) {

		//1.保存订单表
		omsOrderMapper.insert(omsOrder);
		Integer orderId = omsOrder.getId();
		Integer memberId = omsOrder.getMemberId();  //用户id
		//2.保存订单详情
		List<OmsOrderItem> omsOrderItemList = omsOrder.getOmsOrderItemList();
		for (OmsOrderItem omsOrderItem : omsOrderItemList) {
			omsOrderItem.setOrderId(orderId);
			omsOrderItemMapper.insert(omsOrderItem);
			//3.删除购物车，根据用户id和商品skuId
			String productSkuId = omsOrderItem.getProductSkuId();
			OmsCartItem omsCartItem = new OmsCartItem();
			omsCartItem.setMemberId(memberId);
			omsCartItem.setProductSkuId(productSkuId);
			QueryWrapper<OmsCartItem> wrapper = new QueryWrapper<>(omsCartItem);
			omsCartItemMapper.delete(wrapper);

		}



	}

	/**
	 * 根据对外订单号获取订单详情
	 * @param outTradeNo
	 * @return
	 */
	@Override
	public OmsOrder getOrderByOutTradeNo(String outTradeNo) {
		OmsOrder omsOrder = new OmsOrder();
		omsOrder.setOrderSn(outTradeNo);
		QueryWrapper<OmsOrder> wrapper = new QueryWrapper<>(omsOrder);
		return omsOrderMapper.selectOne(wrapper);
	}

	/**
	 * 根据对外订单号修改订单状态
	 *
	 * mq——>提供给库存服务
	 *
	 * @param outTradeNo
	 * @param status
	 */
	@Override
	public void updateStatusByOutTradeNo(String outTradeNo,Integer status) {

		OmsOrder entity = new OmsOrder();
		entity.setStatus(status);
		entity.setPaymentTime(new Date());	//支付时间

		QueryWrapper<OmsOrder> wrapper = new QueryWrapper<>();
		wrapper.eq("order_sn",outTradeNo);


		//支付成功后，引起的系统服务——>订单服务更新——>库存服务——>物流服务
		//发送一个订单已支付的队列，提供给库存消费
		Session session = null;
		try (Connection connection = activeMQUtil.getConnectionFactory().createConnection()){
			session = connection.createSession(true, Session.SESSION_TRANSACTED);

			if (session != null) {
				//创建消息队列
				Queue queue = session.createQueue(MqConst.ORDER_PAY_QUEUE);
				MessageProducer producer = session.createProducer(queue);
				//TextMessage textMessage=new ActiveMQTextMessage();//字符串文本

				//传输数据
				MapMessage mapMessage = new ActiveMQMapMessage();// hash结构
				mapMessage.setString("out_trade_no", outTradeNo);

				//更新和发消息一起来
				omsOrderMapper.update(entity,wrapper);
				producer.send(mapMessage);

				//提交
				session.commit();
			}

		} catch (Exception e) {
			// 消息回滚
			try {
				if (session != null) {
					session.rollback();
				}
			} catch (JMSException e1) {
				e1.printStackTrace();
				System.err.println("com.bat.qmall.order.impl.OrderServiceImpl.updateStatusByOutTradeNo方法出错");
			}
		}

	}


	/**
	 * 交易码 key：
	 *
	 * key ： user:memberId + skuIds : tradeCode
	 *
	 * @param memberId
	 * @param orderList
	 * @return
	 */
	private String getTradeCodeKey(Integer memberId, List<OmsCartItem> orderList) {

		List<String> skuIds = orderList.stream()
				.map(OmsCartItem::getProductSkuId)
				.sorted()
				.collect(Collectors.toList());

		StringBuilder tradeCodeKey = new StringBuilder();
		tradeCodeKey.append("user:").append(memberId);
		for (String skuId : skuIds) {
			tradeCodeKey.append(skuId);
		}
		tradeCodeKey.append(":tradeCode");

		return tradeCodeKey.toString();
	}


}
