package com.bat.shop.api.bean.oms;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmsOrderItem extends Model<OmsOrderItem> {

    private Integer id;
    private Integer orderId;
    private String orderSn;							//订单号，全世界唯一，供其他系统使用
    private String productId;
    private String productPic;
    private String productName;
    private String productBrand;
    private String productSn;
    private BigDecimal productPrice;				//销售价格
    private Integer productQuantity;				//购买数量
    private String productSkuId;					//商品sku编号
    private String productSkuCode;					//商品sku条码
    private String productCategoryId;				//商品分类id
    private String sp1;
    private String sp2;
    private String sp3;
    private String promotionName;
    private BigDecimal promotionAmount;
    private BigDecimal couponAmount;
    private BigDecimal integrationAmount;			//积分优惠分解金额
    private BigDecimal realAmount;						//该商品经过优惠后的分解金额
    private Integer giftIntegration;
    private Integer giftGrowth;
    private String productAttr;

}
