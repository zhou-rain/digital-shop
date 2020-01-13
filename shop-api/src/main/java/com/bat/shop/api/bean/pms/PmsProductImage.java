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
public class PmsProductImage extends Model<PmsProductImage> {

    private String id;
    private String productId;
    private String imgName;
    private String imgUrl;


}