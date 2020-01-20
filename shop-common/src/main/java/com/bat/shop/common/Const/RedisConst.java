package com.bat.shop.common.Const;

/**
 * @author: zhouR
 * @date: Create in 2020/1/20 - 0:28
 * @function: Redis的常量池
 */
public class RedisConst {

	public static final int SKU_NULL_3MINS = 60*3;			//设置sku空值存入缓存的失效时间
	public static final int SKU_LOCK_TIME = 10*1000;			//设置redis锁的失效时间（毫秒）
	public static final String SKU_LOCK_RESULT = "OK";		//redis锁设置成功后的结果
	/**
	 * 获取redis中sku的key值
	 * @param skuId
	 * @return
	 */
	public static String getSkuKey(String skuId){
		return "sku:"+skuId+":info";
	}

	/**
	 * 获取redis分布式锁中sku的key值
	 * @param skuId
	 * @return
	 */
	public static String getLockSkuKey(String skuId){
		return "sku:"+skuId+":lock";
	}








}
