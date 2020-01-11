package com.bat.qmall.mp.controller;

import com.bat.qmall.mp.mapper.MpMapper;
import com.bat.shop.api.bean.User;
import com.bat.shop.common.commons.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author: zhouR
 * @date: Create in 2020/1/10 - 19:26
 * @function:
 */
@Controller
public class MpController {

	@Autowired
	MpMapper mpMapper;

	@RequestMapping("/index")
	public String index(){
		return "index.html";
	}


	@RequestMapping("/ok")
	@ResponseBody
	public User ok(){
		User user = mpMapper.selectById(8);


		PageHelper.startPage(1,2);
		List<User> users = mpMapper.selectList(null);
		PageInfo<User> pageInfo = new PageInfo<>(users,5);
		System.out.println("pageInfo = " + pageInfo);

		for (User user1 : pageInfo.getList()) {
			System.out.println("user1 = " + user1);
		}

		return user;
	}

}
