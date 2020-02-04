package com.bat.qmall.Const;

/**
 * @author: zhouR
 * @date: Create in 2020/2/4 - 12:27
 * @function:
 */
public class MqConst {

	public static final String PAYMENT_CHECK_QUEUE = "PAYMENT_CHECK_QUEUE";			//提交订单的延迟检查（支付服务）
	public static final String PAYMENT_SUCCESS_QUEUE = "PAYMENT_SUCCESS_QUEUE";		//支付完成（支付服务）
	public static final String ORDER_PAY_QUEUE = "ORDER_PAY_QUEUE";					//订单已支付（订单系统）
	public static final String SKU_DEDUCT_QUEUE = "SKU_DEDUCT_QUEUE";				//库存锁定（库存系统）
	public static final String ORDER_SUCCESS_QUEUE = "ORDER_SUCCESS_QUEUE";			//订单已出库（订单服务）

}
