package com.bat.shop.common.exception;

/**
 * @author: zhouR
 * @date: Create in 2019/12/30 - 10:04
 * @function: 空指针异常
 */
public class EmptyException extends MsgException{

	public EmptyException(){
		super("必填项不能为空！");
	}

	public EmptyException(String message) {
		super(message);
	}

}
