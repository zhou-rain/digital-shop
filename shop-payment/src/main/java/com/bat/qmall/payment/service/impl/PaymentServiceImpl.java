package com.bat.qmall.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.qmall.Const.MqConst;
import com.bat.qmall.Const.OmsConst;
import com.bat.qmall.utils.ActiveMQUtil;
import com.bat.qmall.utils.Validator;
import com.bat.shop.api.bean.pay.PaymentInfo;
import com.bat.shop.api.mapper.pay.PaymentInfoMapper;
import com.bat.shop.api.service.payment.PaymentService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author: zhouR
 * @date: Create in 2020/2/2 - 14:44
 * @function:
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentInfoMapper paymentInfoMapper;
	@Autowired
	ActiveMQUtil activeMQUtil;

	/**
	 * 保存支付宝交易信息
	 *
	 * @param paymentInfo
	 */
	@Override
	public void savePaymentInfo(PaymentInfo paymentInfo) {

		paymentInfoMapper.insert(paymentInfo);

	}

	/**
	 * 根据对外订单号 修改支付状态
	 *
	 * mq——>提供给订单服务
	 *
	 * @param paymentInfo
	 */
	@Override
	public void updatePaymentInfoByOutTradeNo(PaymentInfo paymentInfo) {

		String outTradeNo = paymentInfo.getOrderSn();
		Integer payment_status = Integer.parseInt(paymentInfo.getPaymentStatus());

		PaymentInfo entity = new PaymentInfo();
		entity.setOrderSn(outTradeNo);
		QueryWrapper<PaymentInfo> wapper = new QueryWrapper<>(entity);


		//幂等性检查
		System.out.println("幂等性检查");
		PaymentInfo payment = paymentInfoMapper.selectOne(wapper);
		String paymentStatus = payment.getPaymentStatus();
		if(Validator.isNotEmpty(paymentStatus)&&paymentStatus.equals("1")){
			//还未申请  或 支付成功  则不进行更新操作
			System.out.println("还未申请  或 支付成功  则不进行更新操作");
			return;
		}
		System.out.println("幂等性检查完成   根据对外订单号 修改支付状态");

		//支付成功后，引起的系统服务——>订单服务更新——>库存服务——>物流服务
		//调用mq发送支付成功的消息

		Session session = null;
		try (Connection connection = activeMQUtil.getConnectionFactory().createConnection()){
			paymentInfoMapper.update(paymentInfo, wapper);

			session = connection.createSession(true, Session.SESSION_TRANSACTED);

			if (session != null) {
				//创建消息队列
				Queue queue = session.createQueue(MqConst.PAYMENT_SUCCESS_QUEUE);
				MessageProducer producer = session.createProducer(queue);
				//TextMessage textMessage=new ActiveMQTextMessage();//字符串文本

				//传输数据
				MapMessage mapMessage = new ActiveMQMapMessage();// hash结构
				mapMessage.setString("out_trade_no", outTradeNo);
				mapMessage.setInt("status",payment_status);
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
				System.err.println("com.bat.qmall.payment.service.impl.PaymentServiceImpl.updatePaymentInfoByOutTradeNo方法出错");
			}
		}

	}

	/**
	 * 延迟队列，查询支付宝的订单消息
	 * @param outTradeNo
	 */
	@Override
	public void sendDelayPaymentResulQueue(String outTradeNo,Integer checkCount) {

		Session session = null;
		try (Connection connection = activeMQUtil.getConnectionFactory().createConnection()){

			session = connection.createSession(true, Session.SESSION_TRANSACTED);

			if (session != null) {
				//创建 检查支付状态的 消息队列
				Queue queue = session.createQueue(MqConst.PAYMENT_CHECK_QUEUE);
				MessageProducer producer = session.createProducer(queue);

				//传输数据
				MapMessage mapMessage = new ActiveMQMapMessage();// hash结构
				/*
				 *在这里设置延迟队列，
				 *  args1：类型
				 *  args2：延迟时间，毫秒
				 */
				mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,1000*10);
				mapMessage.setString("out_trade_no", outTradeNo);
				mapMessage.setInt("check_count",checkCount);
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
				System.err.println("com.bat.qmall.payment.service.impl.PaymentServiceImpl.sendDelayPaymentResulQueue方法出错");
			}
		}
	}

	/**
	 * 调用阿里支付接口，根据订单查询支付状态
	 *
	 * 返回  TRADE_SUCCESS  代表支付成功
	 * 返回  TRADE_FAIL  代表支付失败
	 *
	 * @param outTradeNo
	 * @return
	 */
	@Override
	public Map<String, Object> checkPaymentStatus(String outTradeNo) {
		Map<String, Object> map = new HashMap<>();

		//这里使用随机数模拟一下

		int number = new Random().nextInt(10) + 1;

		System.out.println("Random = " + number);

		if(number%2==0){
			map.put("trade_status","TRADE_SUCCESS");
		}else {
			map.put("trade_status","TRADE_FAIL");
		}

		return map;
	}


}
