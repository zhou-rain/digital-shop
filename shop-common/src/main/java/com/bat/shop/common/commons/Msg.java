package com.bat.shop.common.commons;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhour
 * @Date: Create in 17:36 2019/8/16
 */
public class Msg {

	//状态码 1-成功  -1-失败
	private int code;
	//提示信息
	private String msg;
	//返回的数据
	private Map<String, Object> extend = new HashMap<>();

	//成功
	public static Msg success() {
		Msg msg = new Msg();
		msg.setCode(1);
		msg.setMsg("处理成功");
		return msg;
	}

	//失败
	public static Msg fail() {
		Msg msg = new Msg();
		msg.setCode(-1);
		msg.setMsg("处理失败");
		return msg;
	}

	//失败
	public static Msg fail(String errInfo) {
		Msg msg = new Msg();
		msg.setCode(-1);
		msg.setMsg(errInfo);
		return msg;
	}

	public  Msg add(String key, Object value) {
		this.getExtend().put(key, value);
		return this;
	}



	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
}
