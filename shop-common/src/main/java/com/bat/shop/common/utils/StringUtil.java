package com.bat.shop.common.utils;

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
	public static String uuid(){
		return UUID.randomUUID().toString().replaceAll("-","").substring(16)+DateUtil.getTimesPlus();
	}


	/**
	 * 打印当前方法
	 */
	public static void printMethod(){

		StackTraceElement element = Thread.currentThread().getStackTrace()[2];
		String className = element.getClassName();//调用的类名
		String methodName = element.getMethodName();//调用的方法名
		int lineNumber = element.getLineNumber();//调用的行数

		System.out.print("\n当前类名 = " + className);
		System.out.print("  当前方法名 = " + methodName);
		System.out.println("  当前行数 = " + lineNumber);

	}


}
