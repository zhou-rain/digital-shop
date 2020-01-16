package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PmsProductSaleAttrValue extends Model<PmsProductSaleAttrValue> {
    String id ;

    String productId;

    String saleAttrId;

    String saleAttrValueName;
    //String isChecked;


}
