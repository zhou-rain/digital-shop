package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PmsSkuAttrValue extends Model<PmsSkuAttrValue> {

    String id;

    String attrId;

    String valueId;

    String skuId;


}
