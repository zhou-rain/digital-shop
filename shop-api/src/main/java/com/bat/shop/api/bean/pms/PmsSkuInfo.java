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

    private String id;
    private String productId;
    private BigDecimal price;
    private String skuName;
    private BigDecimal weight;
    private String skuDesc;
    private String catalog3Id;
    private String skuDefaultImg;



	@TableField(exist = false)
	private String spuId;	//  SkuController下saveSkuInfo方法的实际接收productId值

	@TableField(exist = false)
	private List<PmsSkuImage> skuImageList;

	@TableField(exist = false)
	private List<PmsSkuAttrValue> skuAttrValueList;

	@TableField(exist = false)
	private List<PmsSkuSaleAttrValue> skuSaleAttrValueList;




}
