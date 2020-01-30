package com.bat.qmall.Const;

/**
 * @author: zhouR
 * @date: Create in 2020/1/13 - 17:03
 * @function:
 */
public class UmsConst {

	/**
	 * 冻结状态
	 */
	public static final int USER_STATUS_ENABLE = 1;		//用户状态-可用
	public static final int USER_STATUS_DISABLE = 0;	//用户状态-冻结

	/**
	 * 性别
	 */
	public static final int USER_SEX_UNKNOWN = 0;		//性别-未知
	public static final int USER_SEX_MAN = 1;			//性别-男
	public static final int USER_SEX_WOMAN = 2;			//性别-女

	/**
	 * 删除状态
	 */
	public static final int USER_DELETED = 0;			//用户删除
	public static final int USER_NOT_DELETED = 1;		//用户没删


	/**
	 * 邮箱短信验证码
	 */
	public static final String USER_CODE = "code";	//验证码


	/**
	 * 用户来源
	 */
	public static final String SOURCE_SELF = "1";	 //本网站用户
	public static final String SOURCE_SINA = "2";	 //新浪用户
	public static final String SOURCE_TENCENT = "3"; //腾讯用户
	public static final String SOURCE_WX = "4";		 //微信用户
	public static final String SOURCE_BAIDU = "4";	 //百度用户



	public static final String USER_JWT_KEY = "wosuibianxiederenzhengzhongxindekeyzhi";	//jwt认证中心的key值




}
