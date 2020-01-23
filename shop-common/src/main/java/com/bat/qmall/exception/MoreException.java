package com.bat.qmall.exception;

/**
 * @author: zhouR
 * @date: Create in 2019/12/30 - 10:04
 * @function: 重复性校验异常
 */
public class MoreException extends MsgException{

	public MoreException(){
		super("重复性校验");
	}

	public MoreException(String message) {
		super(message);
	}

}
