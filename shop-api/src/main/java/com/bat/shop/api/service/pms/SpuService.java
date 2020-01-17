package com.bat.shop.api.service.pms;

import com.bat.shop.api.bean.pms.PmsProductImage;
import com.bat.shop.api.bean.pms.PmsProductInfo;
import com.bat.shop.api.bean.pms.PmsProductSaleAttr;

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
	 * @param catalog3Id 三级分类
	 * @return
	 */
	List<PmsProductInfo> getSpuListByCatalog3Id(String catalog3Id);

	/**
	 * 保存商品spu信息  基本信息，图片信息，属性信息  属性值信息
	 * @param pmsProductInfo 保存的信息
	 * @return
	 */
	String saveSpuInfo(PmsProductInfo pmsProductInfo);

	/**
	 * 根据spuid 查询spu销售属性列表
	 * @param spuId productId
	 * @return
	 */
	List<PmsProductSaleAttr> spuSaleAttrListBySpuId(String spuId);

	/**
	 * 根据spuid 查询spu的图片列表
	 * @param spuId productId
	 * @return
	 */
	List<PmsProductImage> spuImageListBySpuId(String spuId);

	/**
	 * 根据productId 获取商品销售属性
	 * @param productId
	 * @return
	 */
	List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId);
}
