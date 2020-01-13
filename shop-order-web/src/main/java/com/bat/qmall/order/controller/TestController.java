package com.bat.qmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.User;
import com.bat.shop.api.service.UserService;
import com.bat.shop.common.commons.Msg;
import com.bat.shop.common.utils.WebUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/13 - 14:29
 * @function:
 */
@RestController
public class TestController {

	@Reference(check = false)
	UserService userService;

	@RequestMapping("/index")
	public String index(){
		return "index.html";
	}


	@RequestMapping("/ok")
	@ResponseBody
	public User ok(HttpServletRequest request){
		String currentInfo = WebUtil.getCurrentInfo(request);


		User user = userService.selectById(8);



		PageHelper.startPage(2,2);
		List<User> users = userService.selectAll();
		PageInfo<User> pageInfo = new PageInfo<>(users,5);
		System.out.println("pageInfo = " + pageInfo);

		for (User user1 : pageInfo.getList()) {
			System.out.println("user1 = " + user1);
		}

		return user;
	}

	@RequestMapping("/list")
	@ResponseBody
	public Msg list(){

		PageHelper.startPage(2,2);
		List<User> users = userService.selectAll();
		PageInfo<User> pageInfo = new PageInfo<>(users,5);
		System.out.println("pageInfo = " + pageInfo);

		for (User user1 : pageInfo.getList()) {
			System.out.println("user1 = " + user1);
		}

		return Msg.success().add("list",pageInfo);
	}

}
