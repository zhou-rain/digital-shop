package com.bat.shop.api.controller;

import com.bat.shop.api.bean.UmsAdmin;
import com.bat.shop.api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 11:43
 * @function:
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@RequestMapping("/tomain")
	public String tomain(){

		UmsAdmin admin = adminService.getEntityById(1);

		return "main";
	}

}
