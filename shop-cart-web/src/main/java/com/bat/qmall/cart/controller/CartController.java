package com.bat.qmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.service.oms.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 18:59
 * @function:
 */
@Controller
public class CartController {
	
	@Reference(check = false)
	CartService cartService;

	@RequestMapping("addToCart")
	@ResponseBody
	public String addToCart(){
		

		System.out.println("cartService = " + cartService);

		return "redirect:/success.html";
	}


}
