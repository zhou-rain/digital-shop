package com.bat.qmall.mp.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.ums.UmsMember;
import com.bat.shop.api.service.ums.UmsMemberService;
import com.bat.shop.common.commons.Msg;
import com.bat.shop.common.utils.WebUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author: zhouR
 * @date: Create in 2020/1/10 - 19:26
 * @function:
 */
@Controller
public class MpController {

	@Reference(check = false)
	UmsMemberService userService;

	@RequestMapping("/index")
	public String index(){
		return "index.html";
	}


	@RequestMapping("/ok")
	@ResponseBody
	public UmsMember ok(HttpServletRequest request){
		String currentInfo = WebUtil.getCurrentInfo(request);


		UmsMember user = userService.selectById(8);



		PageHelper.startPage(2,2);
		List<UmsMember> users = userService.selectAll();
		PageInfo<UmsMember> pageInfo = new PageInfo<>(users,5);
		System.out.println("pageInfo = " + pageInfo);

		for (UmsMember user1 : pageInfo.getList()) {
			System.out.println("user1 = " + user1);
		}

		return user;
	}

	@RequestMapping("/list")
	@ResponseBody
	public Msg list(){

		PageHelper.startPage(2,2);
		List<UmsMember> users = userService.selectAll();
		PageInfo<UmsMember> pageInfo = new PageInfo<>(users,5);
		System.out.println("pageInfo = " + pageInfo);

		for (UmsMember user1 : pageInfo.getList()) {
			System.out.println("user1 = " + user1);
		}

		return Msg.success().add("list",pageInfo);
	}




}
