package com.bat.shop.api.bean.search;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: zhouR
 * @date: Create in 2020/2/9 - 12:50
 * @function: 搜索功能的前台参数接收封装类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsSearchParam extends Model<PmsSearchParam> {

	private String catalog3Id;
	private String keyword;
	private String[] valueId;
}

