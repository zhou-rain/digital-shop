package com.bat.shop.api.service.pms;

import com.bat.shop.api.bean.pms.PmsProductInfo;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/15 - 14:58
 * @function:
 */
public interface SpuService {
	/**
	 * 根据三级分类，查询Spu （商品分类集合）
	 * 如：电脑分类下面有很多品牌的电脑
	 * @param catalog3Id
	 * @return
	 */
	List<PmsProductInfo> getSpuListByCatalog3Id(String catalog3Id);

	/**
	 * 保存商品spu信息  基本信息，图片信息，属性信息  属性值信息
	 * @param pmsProductInfo
	 * @return
	 */
	String saveSpuInfo(PmsProductInfo pmsProductInfo);
}
