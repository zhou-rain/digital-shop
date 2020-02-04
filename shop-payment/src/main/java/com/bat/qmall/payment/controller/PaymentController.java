package com.bat.qmall.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.qmall.Const.OmsConst;
import com.bat.qmall.annotations.LoginRequired;
import com.bat.shop.api.bean.oms.OmsOrder;
import com.bat.shop.api.bean.pay.PaymentInfo;
import com.bat.shop.api.service.oms.OrderService;
import com.bat.shop.api.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: zhouR
 * @date: Create in 2020/2/1 - 17:43
 * @function: 支付系统
 */
@Controller
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	@Reference
	OrderService orderService;


	/**
	 * 流程：
	 * 	订单页面——>确定支付宝还是微信——>将订单数据带到扫码页面——>扫完码跳转到回调函数——>根据支付信息，处理后续订单业务
	 */

	/**
	 * 支付页面的回调函数
	 * @param outTradeNo
	 * @param paymentStatus
	 * @param model
	 * @return
	 */
	@RequestMapping("/alipay/callback/return")
	@LoginRequired
	public String aliPayCallBackReturn(String outTradeNo,String paymentStatus, Model model){

		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setOrderSn(outTradeNo);
		paymentInfo.setPaymentStatus(paymentStatus); //1-成功  6-失败
		paymentInfo.setAlipayTradeNo("这是支付宝返回的交易编号");
		paymentInfo.setConfirmTime(new Date());		//提交时间

		paymentService.updatePaymentInfoByOutTradeNo(paymentInfo);


		//支付成功后，引起的系统服务——>订单服务更新——>库存服务——>物流服务
		//调用mq发送支付成功的消息

		model.addAttribute("paymentStatus",paymentStatus);
		return "finish";
	}


	/**
	 * 将数据提交到支付页面
	 *
	 * @param outTradeNo
	 * @param totalAmount
	 * @param model
	 * @return
	 */
	@RequestMapping("/alipay/submit")
	@LoginRequired
	public String alipay(String outTradeNo, BigDecimal totalAmount,Model model){

		String subject = getSubjectByOutTradeNo(outTradeNo);

		OmsOrder omsOrder = orderService.getOrderByOutTradeNo(outTradeNo);

		if(omsOrder!=null){
			//生成并保存用户的支付信息
			PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setCreateTime(new Date());	//支付创建时间
			paymentInfo.setOrderId(omsOrder.getId());
			paymentInfo.setOrderSn(outTradeNo);
			paymentInfo.setPaymentStatus("未支付");
			paymentInfo.setSubject(subject);
			paymentInfo.setTotalAmount(totalAmount);
			paymentService.savePaymentInfo(paymentInfo);

		}

		model.addAttribute("out_trade_no",outTradeNo);	//订单号
		model.addAttribute("subject",subject);			//商品信息
		model.addAttribute("total_amount",totalAmount);		//支付金额


		// 提交请求到支付页面*/
		return "pay";
	}



	/**
	 * 跳转到支付页面
	 * @param outTradeNo
	 * @return
	 */
	@RequestMapping("/index")
	@LoginRequired
	public String index(String outTradeNo, String totalAmount, HttpServletRequest request, Model model){
		Integer memberId = (Integer)request.getAttribute("memberId");
		String nickname = (String)request.getAttribute("nickname");

		model.addAttribute("nickName",nickname);
		model.addAttribute("outTradeNo",outTradeNo);
		model.addAttribute("totalAmount",totalAmount);

		return "index";
	}



	/**
	 * // todo 根据订单号获取订单名称
	 * 根据订单号获取订单名称
	 * @return
	 */
	private String getSubjectByOutTradeNo(String outTradeNo){

		return "这是商品信息简述"+outTradeNo;
	}
}
