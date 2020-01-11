package com.bat.shop.common.exception;

/**
 * @author: zhouR
 * @date: Create in 2019/12/30 - 10:02
 * @function: 自定义父类异常
 */
public class MsgException extends Exception {

	public MsgException() {}

	public MsgException(String message) {
		super(message);
	}

}