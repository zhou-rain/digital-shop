package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @param
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsSkuInfo extends Model<PmsSkuInfo> {

    String id;
    String productId;
    BigDecimal price;
    String skuName;
    BigDecimal weight;
    String skuDesc;
    String catalog3Id;
    String skuDefaultImg;

	@TableField(exist = false)
    List<PmsSkuImage> pmsSkuImageList;

	@TableField(exist = false)
    List<PmsSkuAttrValue> pmsSkuAttrValueList;

	@TableField(exist = false)
    List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList;




}