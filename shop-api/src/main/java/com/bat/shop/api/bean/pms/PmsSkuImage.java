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

    private String id;
    private String skuId;
    private String imgName;
    private String imgUrl;
    private String spuImgId;
    private String isDefault;


}