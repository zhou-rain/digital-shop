package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 销售属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsProductSaleAttr extends Model<PmsProductSaleAttr> {

	private String id ;

	private String productId;

	private String saleAttrId;

	private String saleAttrName;

	@TableField(exist = false)
	private List<PmsProductSaleAttrValue> spuSaleAttrValueList;


}
