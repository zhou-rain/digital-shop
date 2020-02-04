package com.bat.qmall.order.mq;

import com.bat.qmall.Const.MqConst;
import com.bat.shop.api.service.oms.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @author: zhouR
 * @date: Create in 2020/2/4 - 12:55
 * @function:
 */
@Component
public class OrderMqListener {

	@Autowired
	OrderService orderService;

	/**
	 * destination：要监听的消息名称
	 * containerFactory：配置文件内 交给spring容器管理的监听工厂
	 * @param mapMessage
	 */
	@JmsListener(destination = MqConst.PAYMENT_SUCCESS_QUEUE,containerFactory = "jmsQueueListener")
	public void consumePaymentResult(MapMessage mapMessage) throws JMSException {

		String outTradeNo = mapMessage.getString("out_trade_no");
		Integer status = mapMessage.getInt("status");

		//更新订单状态
		orderService.updateStatusByOutTradeNo(outTradeNo,status);

	}


}
