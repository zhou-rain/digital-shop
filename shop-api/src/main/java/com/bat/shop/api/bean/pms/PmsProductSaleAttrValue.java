package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PmsProductSaleAttrValue extends Model<PmsProductSaleAttrValue> {

	private String id ;
	private String productId;
	private String saleAttrId;
	private String saleAttrValueName;

    @TableField(exist = false)
	private String isChecked;


}
