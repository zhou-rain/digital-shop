package com.bat.shop.api.service.payment;

import com.bat.shop.api.bean.pay.PaymentInfo;

import java.util.Map;

/**
 * @author: zhouR
 * @date: Create in 2020/2/2 - 14:01
 * @function:
 */
public interface PaymentService {
	/**
	 * 保存用户的支付信息
	 * @param paymentInfo
	 */
	void savePaymentInfo(PaymentInfo paymentInfo);

	/**
	 * 根据对外订单号更新状态
	 * @param paymentInfo
	 */
	void updatePaymentInfoByOutTradeNo(PaymentInfo paymentInfo);

	/**
	 * 延迟队列，查询支付宝的订单消息
	 * @param outTradeNo
	 */
	void sendDelayPaymentResulQueue(String outTradeNo,Integer checkCount);

	/**
	 * 调用阿里支付接口，根据订单查询支付状态
	 * @param outTradeNo
	 * @return
	 */
	Map<String,Object> checkPaymentStatus(String outTradeNo);





}
