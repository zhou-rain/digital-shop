package com.bat.shop.api.bean.oms;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmsOrder extends Model<OmsOrder> {

	private String id;
	private String memberId;
	private String couponId;
	private String orderSn;
	private Date createTime;
	private String memberUsername;
	private BigDecimal totalAmount;
	private BigDecimal payAmount;
	private BigDecimal freightAmount;
	private BigDecimal promotionAmount;
	private BigDecimal integrationAmount;
	private BigDecimal couponAmount;
	private BigDecimal discountAmount;
	private int payType;
	private int sourceType;
	private int status;
	private int orderType;
	private String deliveryCompany;
	private String deliverySn;
	private int autoConfirmDay;
	private int integration;
	private int growth;
	private String promotionInfo;
	private int billType;
	private String billHeader;
	private String billContent;
	private String billReceiverPhone;
	private String billReceiverEmail;
	private String receiverName;
	private String receiverPhone;
	private String receiverPostCode;
	private String receiverProvince;
	private String receiverCity;
	private String receiverRegion;
	private String receiverDetailAddress;
	private String note;
	private int confirmStatus;
	private int deleteStatus;
	private int useIntegration;
	private Date paymentTime;
	private Date deliveryTime;
	private Date receiveTime;
	private Date commentTime;
	private Date modifyTime;

}
