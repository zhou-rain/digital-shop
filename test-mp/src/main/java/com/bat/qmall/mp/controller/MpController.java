package com.bat.qmall.mp.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.service.ums.UmsMemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: zhouR
 * @date: Create in 2020/1/10 - 19:26
 * @function:
 */
@RestController
public class MpController {

	@Reference(check = false)
	UmsMemberService memberService;

	@RequestMapping("/index")
	public String index(){
		System.out.println(memberService);
		memberService.selectAll();
		return "index.html";
	}


	/*@RequestMapping("/ok")
	public UmsMember ok(HttpServletRequest request){
		String currentInfo = WebUtil.getCurrentInfo(request);
		System.out.println("currentInfo = " + currentInfo);

		UmsMember user = memberService.selectById(8);
		System.out.println("user = " + user);
		
		PageHelper.startPage(2,2);
		List<UmsMember> users = memberService.selectAll();
		PageInfo<UmsMember> pageInfo = new PageInfo<>(users,5);
		System.out.println("pageInfo = " + pageInfo);

		for (UmsMember user1 : pageInfo.getList()) {
			System.out.println("user1 = " + user1);
		}

		return user;
	}

	@RequestMapping("/list")
	public Msg list(){

		PageHelper.startPage(2,2);
		List<UmsMember> users = memberService.selectAll();
		PageInfo<UmsMember> pageInfo = new PageInfo<>(users,5);

		return Msg.success().add("list",pageInfo);
	}*/




}
