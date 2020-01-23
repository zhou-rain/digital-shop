package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @param
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsSkuSaleAttrValue extends Model<PmsSkuSaleAttrValue> {

    private String id;
    private String skuId;
    private String saleAttrId;
    private String saleAttrValueId;
    private String saleAttrName;
    private String saleAttrValueName;


}
