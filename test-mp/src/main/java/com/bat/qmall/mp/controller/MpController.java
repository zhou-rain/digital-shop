package com.bat.qmall.mp.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.User;
import com.bat.shop.api.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author: zhouR
 * @date: Create in 2020/1/10 - 19:26
 * @function:
 */
@Controller
public class MpController {

	@Reference(check = false)
	UserService userService;

	@RequestMapping("/index")
	public String index(){
		return "index.html";
	}


	@RequestMapping("/ok")
	@ResponseBody
	public User ok(){
		System.out.println("userService = " + userService);
		User user = userService.selectById(8);


		/*PageHelper.startPage(2,2);
		List<User> users = userService.selectAll();
		PageInfo<User> pageInfo = new PageInfo<>(users,5);
		System.out.println("pageInfo = " + pageInfo);

		for (User user1 : pageInfo.getList()) {
			System.out.println("user1 = " + user1);
		}
*/
		return user;
	}

}
