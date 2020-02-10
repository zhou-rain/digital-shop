package com.bat.shop.api.bean.search;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.bat.shop.api.bean.pms.PmsSkuAttrValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/2/9 - 12:49
 * @function:  与es交互的sku类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsSearchSkuInfo extends Model<PmsSearchSkuInfo> {

	private String id;
	private String skuName;
	private String skuDesc;
	private String catalog3Id;
	private BigDecimal price;
	private String skuDefaultImg;
	private double hotScore;
	private String productId;
	private List<PmsSkuAttrValue> skuAttrValueList;

}
