package com.bat.shop.api.bean.oms;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmsCartItem extends Model<OmsCartItem> {

	private String id;
	private String productId;
	private String productSkuId;
	private String memberId;
	private int quantity;
	private BigDecimal price;
	private String sp1;
	private String sp2;
	private String sp3;
	private String productPic;
	private String productName;
	private String productSubTitle;
	private String productSkuCode;
	private String memberNickname;
	private Date createDate;
	private Date modifyDate;
	private int deleteStatus;
	private String productCategoryId;
	private String productBrand;
	private String productSn;
	private String productAttr;

}
