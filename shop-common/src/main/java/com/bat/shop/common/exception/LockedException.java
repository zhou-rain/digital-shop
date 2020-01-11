package com.bat.shop.common.exception;

/**
 * @author: zhouR
 * @date: Create in 2019/12/30 - 10:04
 * @function: 对象锁定异常
 */
public class LockedException extends MsgException{

	public LockedException(){
		super("当前对象被锁定");
	}

	public LockedException(String message) {
		super(message);
	}

}
