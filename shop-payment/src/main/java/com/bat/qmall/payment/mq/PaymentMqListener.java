package com.bat.qmall.payment.mq;

import com.bat.qmall.Const.MqConst;
import com.bat.shop.api.bean.pay.PaymentInfo;
import com.bat.shop.api.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.Date;
import java.util.Map;

/**
 * @author: zhouR
 * @date: Create in 2020/2/4 - 17:58
 * @function:
 */
@Component
public class PaymentMqListener {

	@Autowired
	PaymentService paymentService;

	/**
	 * 监听 检查支付状态的延迟检查(支付服务)
	 * @param mapMessage
	 * @throws JMSException
	 */
	@JmsListener(destination = MqConst.PAYMENT_CHECK_QUEUE,containerFactory = "jmsQueueListener")
	public void consumePaymentResult(MapMessage mapMessage) throws JMSException {

		String outTradeNo = mapMessage.getString("out_trade_no");
		Integer checkCount = mapMessage.getInt("check_count");

		if (checkCount==null) {
			checkCount=5;
		}

		//调用支付宝检查接口，去支付宝服务器查询该订单的支付情况
		System.out.println("进行延迟检查，调用支付检查的接口服务");
		Map<String,Object> resultMap = paymentService.checkPaymentStatus(outTradeNo);



		if(resultMap==null || resultMap.isEmpty()){
			//结果为空
			if(checkCount>0){
				System.out.println("支付失败，继续发送延迟检查任务,剩余次数："+checkCount);
				checkCount -= 1;
				paymentService.sendDelayPaymentResulQueue(outTradeNo,checkCount);
			}else {
				System.out.println("检查次数用尽");
			}

		}else {
			//结果不为空
			String trade_status = (String) resultMap.get("trade_status");

			//根据查询的支付结果，判断是否进行下一次的延迟任务 还是 支付成功更新数据
			if(trade_status.equals("TRADE_SUCCESS")){
				//查询到支付成功的信息，


				//生成并保存用户的支付信息，
				//这里的代码跟controller中/alipay/submit方法是一样的，在更新的方法里做了幂等性检查
				//这里的数据是从上面的paymentService.checkPaymentStatus(outTradeNo);方法中获取到的
				//由于本系统没做，所以关于支付宝的多余数据都没设置
				PaymentInfo paymentInfo = new PaymentInfo();
				paymentInfo.setCreateTime(new Date());	//支付创建时间
				paymentInfo.setOrderSn(outTradeNo);
				paymentInfo.setPaymentStatus("1");//1-成功  6-失败

				//成功后发送支付队列
				paymentService.updatePaymentInfoByOutTradeNo(paymentInfo);
				System.out.println("支付成功，调用支付服务，修改支付信息和发送支付成功的队列");

			}else {
				//没查到支付成功的信息
				//继续发送延迟检查任务，计算延迟时间等
				if(checkCount>0){
					System.out.println("支付失败，继续发送延迟检查任务，剩余次数："+checkCount);
					checkCount -= 1;
					paymentService.sendDelayPaymentResulQueue(outTradeNo,checkCount);
				}else {
					System.out.println("检查次数用尽");
				}

			}
		}




	}




}
