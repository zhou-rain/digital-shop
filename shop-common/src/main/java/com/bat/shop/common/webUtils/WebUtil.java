package com.bat.shop.common.webUtils;

import com.bat.shop.common.utils.Validator;

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



	public static String getCurrentInfo(HttpServletRequest request){

		String requestURI = request.getRequestURI();
		int port = request.getServerPort();
		String contextPath = request.getContextPath();
		String ret ="当前项目："+contextPath+":"+port+requestURI;
		return ret;
	}







}



