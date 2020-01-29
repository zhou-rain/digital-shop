package com.bat.qmall.userpass.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.bat.qmall.Const.RedisConst;
import com.bat.qmall.Const.UmsConst;
import com.bat.qmall.utils.Validator;
import com.bat.qmall.webUtils.JwtUtil;
import com.bat.qmall.webUtils.Msg;
import com.bat.qmall.webUtils.WebUtil;
import com.bat.shop.api.bean.ums.UmsMember;
import com.bat.shop.api.service.ums.UmsMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhouR
 * @date: Create in 2020/1/27 - 1:07
 * @function:
 */
@Controller
public class UserPassController {

	@Reference
	private UmsMemberService memberService;

	/**
	 * jwt校验token
	 * @param token
	 * @return
	 */
	@RequestMapping("/verify")
	@ResponseBody
	public String verify(String token,String memberIp){

		//通过jwt校验token
		Map<String, Object> decode = JwtUtil.decode(token, UmsConst.USER_JWT_KEY, memberIp);

		if(decode.isEmpty()){
			decode.put("result","fail");
		}else {
			decode.put("result","success");
		}
		return JSON.toJSONString(decode);
	}

	/**
	 * 登录
	 * @param umsMember
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String login(UmsMember umsMember, HttpServletRequest request){
		String token = "";
		//调用用户服务，验证用户名和密码
		UmsMember loginMember = memberService.login(umsMember);

		if(loginMember!=null){
			//登录成功

			//用jwt制作token
			Integer memberId = loginMember.getId();
			String nickname = loginMember.getNickname();
			Map<String,Object> map = new HashMap<>();
			map.put("memberId",memberId);
			map.put("nickname",nickname);

			String ip = WebUtil.getIp(request);
			token = JwtUtil.encode(UmsConst.USER_JWT_KEY,map,ip);

			//将token存入redis一份
			memberService.addMemberTokenToRedisByMemberId(token,memberId);
		}else {
			//登录失败
			token="fail";
		}

		return token;
	}
	


	/**
	 * 去登录页面  进行验证
	 * @return
	 */
	@RequestMapping("/index")
	public String index(String originUrl, Model model){
		model.addAttribute("originUrl",originUrl);
		return "index";
	}




}
