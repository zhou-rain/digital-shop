package com.bat.shop.api.bean.oms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmsOrder extends Model<OmsOrder> {

	private Integer id;
	private Integer memberId;
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
	private Integer payType;
	private Integer sourceType;
	private Integer status;
	private Integer orderType;
	private String deliveryCompany;
	private String deliverySn;
	private Integer autoConfirmDay;
	private Integer integration;
	private Integer growth;
	private String promotionInfo;
	private Integer billType;
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
	private Integer confirmStatus;
	private Integer deleteStatus;
	private Integer useIntegration;
	private Date paymentTime;
	private Date deliveryTime;
	private Date receiveTime;
	private Date commentTime;
	private Date modifyTime;

	@TableField(exist = false)
	private List<OmsOrderItem> omsOrderItemList;

}
