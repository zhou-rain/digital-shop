package com.bat.shop.api.service.pms;

import com.bat.shop.api.bean.pms.PmsBaseAttrValue;
import com.bat.shop.api.bean.pms.PmsProductSaleAttr;
import com.bat.shop.api.bean.pms.PmsSkuInfo;
import com.bat.shop.common.exception.ErrException;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/16 - 11:15
 * @function: sku
 */
public interface SkuService {

	/**
	 * 保存商品sku信息
	 * @param pmsSkuInfo
	 * @return
	 */
	void saveSkuInfo(PmsSkuInfo pmsSkuInfo) throws ErrException;


	/**
	 * 根据skuid获取信息
	 * @param skuId
	 * @return
	 */
	PmsSkuInfo getSkuById(String skuId);



}
