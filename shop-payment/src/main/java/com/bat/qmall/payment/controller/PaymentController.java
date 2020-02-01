package com.bat.qmall.payment.controller;

import com.bat.qmall.annotations.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhouR
 * @date: Create in 2020/2/1 - 17:43
 * @function: 支付系统
 */
@Controller
public class PaymentController {



	/**
	 * 跳转到支付页面
	 * @param orderTradeNo
	 * @return
	 */
	@RequestMapping("/index")
	@LoginRequired
	public String index(String orderTradeNo, String totalAmount, HttpServletRequest request, Model model){
		Integer memberId = (Integer)request.getAttribute("memberId");
		String nickname = (String)request.getAttribute("nickname");


		model.addAttribute("nickName",nickname);
		model.addAttribute("orderId",orderTradeNo);
		model.addAttribute("totalAmount",totalAmount);

		return "index";
	}

}
