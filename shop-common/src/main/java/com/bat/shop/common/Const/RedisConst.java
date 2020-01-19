package com.bat.shop.common.Const;

/**
 * @author: zhouR
 * @date: Create in 2020/1/20 - 0:28
 * @function: Redis的常量池
 */
public class RedisConst {

	/**
	 * 获取redis中sku的key值
	 * @param skuId
	 * @return
	 */
	public static String getSkuKey(String skuId){
		return "sku:"+skuId+":info";
	}








}
