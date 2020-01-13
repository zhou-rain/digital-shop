package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @param
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsProductInfo extends Model<PmsProductInfo> {

    private String id;

    private String productName;

    private String description;

    private  String catalog3Id;

    @TableField(exist = false)
    private List<PmsProductSaleAttr> pmsProductSaleAttrList;
	@TableField(exist = false)
    private List<PmsProductImage> pmsProductImageList;

}


