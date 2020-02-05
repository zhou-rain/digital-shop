package com.bat.qmall.Const;

/**
 * @author: zhouR
 * @date: Create in 2020/1/22 - 20:50
 * @function:	oms订单模块的常量池
 */
public class OmsConst {

	public static final int CART_DELETED = 1;			//购物车删除
	public static final int CART_NOT_DELETED = 0;		//购物车没删除

	public static final String CHECK = "1";				//购物车选中状态
	public static final String CHECK_NO = "0";			//购物车没选中


	public static final String CART_DB = "cartListDb";				//数据库中的购物车（有主键和用户id）
	public static final String CART_REDIS = "cartListRedis";		//redis缓存中的购物车（有主键和用户id）
	public static final String CART_COOKIE = "cartListCookie";		//cookie中的购物车（有主键和用户id）


	public static final int COOKIE_MAXAGE = 60*60*24*3;		//cookie过期时间  3天  单位/秒


	/**
	 * 确认收货状态：0->未确认；1->已确认
	 */
	public static final int CONFIRM_STATUS_NOT_SURE = 0;
	public static final int CONFIRM_STATUS_SURE = 1;

	/**
	 * delete_status  删除状态：0->未删除；1->已删除
	 */
	public static final int ORDER_NOT_DELETED = 0;
	public static final int ORDER_DELETED = 1;

	/**
	 * status 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单 6->支付失败
	 */
	public static final int STATUS_WILL_PAY = 0;
	public static final int STATUS_WILL_SEND = 1;
	public static final int STATUS_HAVE_SEND = 2;
	public static final int STATUS_FINISH = 3;
	public static final int STATUS_CLOSED = 4;
	public static final int STATUS_DISABLED = 5;
	public static final int STATUS_PAY_FAIL = 6;

	/**
	 * order_type	订单类型：0->正常订单；1->秒杀订单
	 */
	public static final int ORDERR_TYPE_ZHENG_CHANG = 0;
	public static final int ORDERR_TYPE_MIAO_SHA = 1;

	/**
	 * source_type int(1) NULL订单来源：0->PC订单；1->app订单
	 */
	public static final int ORDERR_SOURCE_TYPE_PC = 0;
	public static final int ORDERR_SOURCE_TYPE_APP = 1;






}
