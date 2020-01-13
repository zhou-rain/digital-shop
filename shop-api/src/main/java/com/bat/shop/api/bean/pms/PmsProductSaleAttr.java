package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PmsProductSaleAttr extends Model<PmsProductSaleAttr> {

    String id ;

    String productId;

    String saleAttrId;

    String saleAttrName;

	@TableField(exist = false)
    List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList;


}
