package com.bat.qmall.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.qmall.Const.MqConst;
import com.bat.qmall.Const.OmsConst;
import com.bat.qmall.utils.ActiveMQUtil;
import com.bat.shop.api.bean.pay.PaymentInfo;
import com.bat.shop.api.mapper.pay.PaymentInfoMapper;
import com.bat.shop.api.service.payment.PaymentService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;

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
		Integer payment_ststus = Integer.parseInt(paymentInfo.getPaymentStatus());

		PaymentInfo entity = new PaymentInfo();
		entity.setOrderSn(outTradeNo);
		QueryWrapper<PaymentInfo> wapper = new QueryWrapper<>(entity);

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
				mapMessage.setInt("status",payment_ststus);
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









}
