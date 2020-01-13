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
public class PmsSkuImage extends Model<PmsSkuImage> {

    String id;
    String skuId;
    String imgName;
    String imgUrl;
    String productImgId;
    String isDefault;


}