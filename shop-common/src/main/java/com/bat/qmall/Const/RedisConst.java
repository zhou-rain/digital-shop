package com.bat.qmall.Const;

/**
 * @author: zhouR
 * @date: Create in 2020/1/20 - 0:28
 * @function: Redis的常量池
 */
public class RedisConst {

	public static final int SKU_NULL_3MINS = 60*3;			//设置sku空值存入缓存的失效时间
	public static final int SKU_LOCK_TIME = 10*1000;		//设置redis锁的失效时间（毫秒）
	public static final String SKU_LOCK_RESULT = "OK";		//redis锁设置成功后的结果

	/**
	 * 获取redis中sku的key值
	 */
	public static String getSkuKey(String skuId){
		return "sku:"+skuId+":info";
	}


	/**
	 * 获取redis分布式锁中sku的key值
	 */
	public static String getLockSkuKey(String skuId){
		return "sku:"+skuId+":lock";
	}


	/**
	 * 获取购物车的key值
	 */
	public static String getCartKey(int memberId){
		return "user:"+memberId+":cart";
	}

	/**
	 * 获取用户信息的key值
	 *
	 * key:  username+password
	 * val:	 用户信息
	 *
	 */
	public static String getMemberInfoKey(String username,String password){
		return "member:"+username+password+":info";
	}

	/**
	 * 获取用户token的key值
	 *
	 * key:  memberId
	 * val:	 token
	 *
	 */
	public static String getMembertokenKey(Integer memberId){
		return "member:"+memberId+":token";
	}









}
