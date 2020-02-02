package com.bat.shop.api.service.payment;

import com.bat.shop.api.bean.pay.PaymentInfo;

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
}
