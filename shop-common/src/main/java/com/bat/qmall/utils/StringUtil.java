package com.bat.qmall.utils;

import java.util.UUID;

/**
 * @author: zhouR
 * @date: Create in 2019/12/18 - 15:09
 * @function:
 */
public class StringUtil {

	/**
	 * 获取uuid
	 * @return
	 */
	public static String getUuid(){
		return UUID.randomUUID().toString().replaceAll("-","").substring(16)+DateUtil.getTimesPlus();
	}



}
