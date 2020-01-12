package com.bat.shop.api.service.commons;

/**
 * @author: zhouR
 * @date: Create in 2020/1/12 - 23:38
 * @function:
 */
public interface MessageService {

	/**
	 * 返回短信验证码
	 * @return
	 */
	public String getPhoneCode();

	/**
	 * 返回邮箱验证码
	 * @return
	 */
	public String getEmailCode();
}
