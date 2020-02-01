package com.bat.qmall.userpass.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.bat.qmall.Const.RedisConst;
import com.bat.qmall.Const.UmsConst;
import com.bat.qmall.plugin.SinaUtil;
import com.bat.qmall.utils.Validator;
import com.bat.qmall.webUtils.JwtUtil;
import com.bat.qmall.webUtils.Msg;
import com.bat.qmall.webUtils.WebUtil;
import com.bat.shop.api.bean.ums.UmsMember;
import com.bat.shop.api.service.ums.UmsMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhouR
 * @date: Create in 2020/1/27 - 1:07
 * @function:  登录、授权中心
 *
 */
@Controller
public class UserPassController {

	@Reference
	private UmsMemberService memberService;

	/**
	 * 引导用户跳转到新浪授权页
	 * @return
	 */
	@RequestMapping("/toGetCode")
	@ResponseBody
	public String toGetCode(){
		return SinaUtil.GET_CODE_URL;
	}

	/**
	 * 新浪授权成功回调函数
	 * @param code
	 * @return
	 */
	@RequestMapping("/sinalogin")
	public String sinalogin(String code,HttpServletRequest request){

		//http://userpass.qmall.com:8882/sinalogin?code=687713f4464adfd249177d53a78a714f

		//授权码换取token
		String resultMapJson = SinaUtil.getAccessTokenAndUidByCode(code);
		Map<String,String> map = JSON.parseObject(resultMapJson, Map.class);
		String access_token = map.get("access_token");
		String uid = map.get("uid");

		//accessToken换取用户信息
		Map<String, String> memberInfo = SinaUtil.getMemberInfo(access_token, uid);

		//将用户信息存入我们的数据库
		UmsMember member = new UmsMember();
		member.setAccessCode(code);
		member.setAccessToken(access_token);
		member.setSourceType(UmsConst.SOURCE_SINA);							//设置成新浪用户
		member.setSourceUid(memberInfo.get("idstr"));						//微博用户id
		member.setCity(memberInfo.get("location"));							//地址
		member.setNickname(memberInfo.get("screen_name"));					//昵称
		member.setIcon(memberInfo.get("profile_image_url"));				//头像
		member.setGender(SinaUtil.formatGender(memberInfo.get("gender")));	//设置性别

		UmsMember savedEntity = memberService.addOauthMember(member);

		//生成jwt的token
		String token = null;
		if(savedEntity!=null){
			String ip = WebUtil.getIp(request);
			token = getJwtToken(savedEntity,ip);
		}

		//并且重定向到某个页面，携带该token
		return "redirect:http://cart.qmall.com:8884/cartList?token="+token;
	}




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

		if(Validator.isEmpty(decode)){
			decode.put("result","fail");
		}else {
			decode.put("result","success");
		}
		return JSON.toJSONString(decode);
	}

	/**
	 * 登录
	 *
	 * 1、记日志
	 * 2、合并购物车
	 * 3、发短信等
	 * @param umsMember
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String login(UmsMember umsMember, HttpServletRequest request){
		String token;

		//调用用户服务，验证用户名和密码
		UmsMember loginMember = memberService.login(umsMember);

		if(loginMember!=null){
			//登录成功

			String ip = WebUtil.getIp(request);

			token = getJwtToken(loginMember,ip);

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


	/**
	 * 使用jwt制作token
	 * @param entity
	 * @param ip
	 * @return
	 */
	public String getJwtToken(UmsMember entity,String ip){
		Integer memberId = entity.getId();
		String nickname = entity.getNickname();
		Map<String,Object> map = new HashMap<>();
		map.put("memberId",memberId);
		map.put("nickname",nickname);

		String token = JwtUtil.encode(UmsConst.USER_JWT_KEY,map,ip);

		//将token存入redis一份
		memberService.addMemberTokenToRedisByMemberId(token,memberId);

		return token;
	}



}
