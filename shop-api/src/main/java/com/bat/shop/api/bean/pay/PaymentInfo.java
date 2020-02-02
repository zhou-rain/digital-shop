package com.bat.shop.api.bean.pay;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @param
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentInfo extends Model<PaymentInfo> {

	private Integer id;
	private String orderSn;			//对外订单号
	private Integer orderId;		//订单id
	private String alipayTradeNo;	//支付宝交易编号
	private BigDecimal totalAmount;	//支付金额
	private String Subject;			//交易内容
	private String paymentStatus;	//交易状态
	private Date createTime;		//创建时间
	private Date confirmTime;		//提交时间
	private String callbackContent;	//回调信息
	private Date callbackTime;

}
