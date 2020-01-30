package com.bat.qmall.interceptor;

import com.alibaba.fastjson.JSON;
import com.bat.qmall.annotations.LoginRequired;
import com.bat.qmall.utils.Validator;
import com.bat.qmall.webUtils.CookieUtil;
import com.bat.qmall.webUtils.HttpclientUtil;
import com.bat.qmall.webUtils.WebUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {


	/**
	 * 拦截器
	 * @param request
	 * @param response
	 * @param handler  请求中所携带的方法
	 * @return
	 * @throws Exception
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*
			判断handler的类型是否为controller里面的方法，如果不是则直接放行
			请求里面还带有js/css/images等请求的时候，那么它转换类型的时候就报错了。
		 */
		//静态资源放行
		if (!(handler instanceof HandlerMethod)) return true;

		//将请求的方法强转成子类，这个子类可以通过反射得到方法上面的注解
		HandlerMethod hm = (HandlerMethod) handler;

		// 判断被拦截的请求的访问的方法的注解(是否时需要拦截的)
		LoginRequired loginRequired = hm.getMethodAnnotation(LoginRequired.class);

		//如果没有标注注解，就不用拦截，直接放行
		if (loginRequired == null) return true;


		String token = "";
		String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
		String newToken = request.getParameter("token");

		if(Validator.isNotEmpty(oldToken)){
			token = oldToken;
		}
		if(Validator.isNotEmpty(newToken)){
			token = newToken;
		}


		//调用认证中心进行验证
		String result = "";
		Map<String,String> map = null;

		if(Validator.isNotEmpty(token)){
			String memberIp = WebUtil.getIp(request); //用户id地址（盐值）
			String resultJson = HttpclientUtil.doGet("http://userpass.qmall.com:8882/verify?token=" + token+"&memberIp="+memberIp);
			map = JSON.parseObject(resultJson, Map.class);
			if(Validator.isNotEmpty(map)){
				result = map.get("result");
			}
		}

		//注解上的标志  true/false
		boolean value = loginRequired.value();
		if (value) {
			System.out.println("true——>必须验证");

			if(! result.equals("success")){
				//验证不通过，重定向到验证中心去验证
				StringBuffer originUrl = request.getRequestURL();
				response.sendRedirect("http://userpass.qmall.com:8882/index?originUrl="+originUrl);
				return false;
			}

			request.setAttribute("memberId",map.get("memberId"));
			request.setAttribute("nickname",map.get("nickname"));
			//验证通过，覆盖cookie中的token,有效时间2小时
			if(Validator.isNotEmpty(token)){
				CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);
			}

		} else {
			System.out.println("false——>无需验证");
			//没有登录也能用，但是必须验证一下

			if(result.equals("success")){

				request.setAttribute("memberId",map.get("memberId"));
				request.setAttribute("nickname",map.get("nickname"));
				//验证通过，覆盖cookie中的token,有效时间2小时
				if(Validator.isNotEmpty(token)){
					CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);
				}
			}

		}

		return true;
	}
}
