package com.bat.shop.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

public class WebUtil {
	
	/**
	 * 获取参数的值
	 * @param param	map集合
	 * @param getparam	需要获取的参数
	 * @param defaultStr	设置默认值
	 * @return
	 */
	public static String getParam(Map<String, Object> param,String getparam,String defaultStr) {
		Object obj = param.get(getparam);
		obj = Validator.isEmpty(obj) ? defaultStr : obj;
		return obj.toString();
	}
	/**
	 *
	 * @param param  map集合
	 * @param getparam 需要获取的参数
	 * @return
	 */
	public static String getParam(Map<String, Object> param,String getparam) {
		Object obj = param.get(getparam);
		obj = Validator.isEmpty(obj) ? "" : obj;
		return obj.toString();
	}

	/**
	 * 获取int类型的map值
	 * @param param
	 * @param getparam
	 * @return
	 */
	public static Integer getIntParam(Map<String, Object> param,String getparam) {
		Object obj = param.get(getparam);
		obj = Validator.isEmpty(obj) ? "" : obj;
		return Integer.parseInt(obj.toString());
	}

	/**
	 * 获取int类型的map值
	 * @param param	map集合
	 * @param getparam	需要获取的参数
	 * @param defaultStr	设置默认值
	 * @return
	 */
	public static Integer getIntParam(Map<String, Object> param,String getparam,int defaultStr) {
		Object obj = param.get(getparam);
		obj = Validator.isEmpty(obj) ? defaultStr : obj;
		return Integer.parseInt(obj.toString());
	}


	/**
	 * 获取登录用户的IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		if (ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}




	/**
	 * 每次登陆都给用户分配一个token
	 * 获取用户ip地址
	 *
	 * 拼上当前时间戳
	 * MD5加密后返回字符串
	 *
	 * 以后每次ajax访问都带上token和用户id，通过拦截器进行验证
	 * @return token
	 */
	public static String getToken(String ipaddr,Integer userId){

		StringBuffer token = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replaceAll(" ","").replaceAll("-","");
		token.append(uuid, 0, 8);//截取uuid前8位

		String digest = MD5Util.digest(ipaddr + userId);
		token.append(digest);
		return token.toString();
	}







}



