package com.bat.qmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.oms.OmsOrder;
import com.bat.shop.api.service.oms.OrderService;
import com.bat.shop.common.commons.Msg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 15:16
 * @function:
 */
@RestController
public class OrderController {

	@Reference(check = false)
	OrderService orderService;

	@RequestMapping("/ok")
	public Msg ok(HttpServletRequest request){

		OmsOrder omsOrder = orderService.getEntityById(12);

		return Msg.success();
	}

}
