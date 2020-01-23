package com.bat.qmall.Const;

/**
 * @author: zhouR
 * @date: Create in 2020/1/22 - 20:50
 * @function:	oms订单模块的常量池
 */
public class OmsConst {

	public static final int CART_DELETED = 1;			//购物车删除
	public static final int CART_NOT_DELETED = 0;		//购物车没删除

	public static final String CART_DB = "cartListDb";				//数据库中的购物车（有主键和用户id）
	public static final String CART_REDIS = "cartListRedis";		//redis缓存中的购物车（有主键和用户id）
	public static final String CART_COOKIE = "cartListCookie";		//cookie中的购物车（有主键和用户id）


	public static final int COOKIE_MAXAGE = 60*60*24*3;		//cookie过期时间  3天  单位/秒



}
