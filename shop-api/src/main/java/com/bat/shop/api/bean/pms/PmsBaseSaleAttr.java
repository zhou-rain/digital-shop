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
public class PmsBaseSaleAttr extends Model<PmsBaseSaleAttr> {

	private String id ;

	private String name;


}