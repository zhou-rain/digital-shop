package com.bat.shop.api.bean.search;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: zhouR
 * @date: Create in 2020/2/9 - 12:52
 * @function: 面包屑功能
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsSearchCrumb extends Model<PmsSearchCrumb> {

	private String valueId;
	private String valueName;
	private String urlParam;

}
