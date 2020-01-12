package com.bat.qmall.manager.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bat.shop.api.service.commons.MessageService;
import org.springframework.stereotype.Component;

/**
 * @author: zhouR
 * @date: Create in 2020/1/12 - 23:37
 * @function: 发送验证码
 */
@Service
@Component
public class MseeageServiceImpl implements MessageService {

	/**
	 * 返回短信验证码
	 * @return
	 */
	public String getPhoneCode(){
		return "123456";
	}

	/**
	 * 返回邮箱验证码
	 * @return
	 */
	public String getEmailCode(){
		return "123456";
	}
}
