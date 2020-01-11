package com.bat.shop.common.exception;

/**
 * @author: zhouR
 * @date: Create in 2019/12/30 - 10:04
 * @function: 系统异常
 */
public class ErrException extends MsgException{

	public ErrException(){
		super("系统异常，请刷新或联系管理员！");
	}

	public ErrException(String message) {
		super(message);
	}

}
