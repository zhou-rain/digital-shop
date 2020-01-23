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

	private String id;
	private String outTradeNo;
	private String orderId;
	private String alipayTradeNo;
	private BigDecimal totalAmount;
	private String Subject;
	private String paymentStatus;
	private Date createTime;
	private Date callbackTime;
	private String callbackContent;

}
