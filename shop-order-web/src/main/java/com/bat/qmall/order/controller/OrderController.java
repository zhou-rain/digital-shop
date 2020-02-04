package com.bat.qmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.qmall.Const.OmsConst;
import com.bat.qmall.annotations.LoginRequired;
import com.bat.qmall.utils.CalcUtil;
import com.bat.qmall.utils.DateUtil;
import com.bat.qmall.webUtils.WebUtil;
import com.bat.shop.api.bean.oms.OmsCartItem;
import com.bat.shop.api.bean.oms.OmsOrder;
import com.bat.shop.api.bean.oms.OmsOrderItem;
import com.bat.shop.api.bean.ums.UmsMember;
import com.bat.shop.api.bean.ums.UmsMemberReceiveAddress;
import com.bat.shop.api.service.oms.CartService;
import com.bat.shop.api.service.oms.OrderService;
import com.bat.qmall.webUtils.Msg;
import com.bat.shop.api.service.pms.SkuService;
import com.bat.shop.api.service.ums.UmsMemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 15:16
 * @function: 订单系统
 */
@Controller
public class OrderController {

	@Reference
	OrderService orderService;
	@Reference
	UmsMemberService umsMemberService;
	@Reference
	CartService cartService;
	@Reference
	SkuService skuService;

	/**
	 * 提交订单
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/submitOrder")
	@LoginRequired
	public String submitOrder(@RequestParam Map<String,Object> param, HttpServletRequest request, Model model) {

		Integer receiveAddressId = WebUtil.getIntParam(param,"receiveAddressId");//收货地址id
		String totalAmount = WebUtil.getParam(param,"totalAmount");//总金额
		String tradeCode = WebUtil.getParam(param,"tradeCode");//交易码
		Integer orderType = WebUtil.getIntParam(param,"orderType");//订单类型：0->正常订单；1->秒杀订单
		Integer sourceType = WebUtil.getIntParam(param,"sourceType");//订单来源：0->PC订单；1->app订单
		String orderComment = WebUtil.getParam(param,"orderComment");//订单备注

		Integer memberId = (Integer) request.getAttribute("memberId");
		String nickname = (String) request.getAttribute("nickname");

		// 获取所有购物车
		List<OmsCartItem> cartList = cartService.getCartListByMemberId(memberId);
		// 筛选购物车中勾选的商品
		List<OmsCartItem> cartListChecked = cartList.stream()
				.filter((x) -> x.getIsChecked().equals(OmsConst.CHECK))
				.collect(Collectors.toList());
		//检查交易码
		boolean flag = orderService.checkTradeCode(memberId, cartListChecked, tradeCode);

		if (flag) {
			//交易码可用

			List<OmsOrderItem> omsOrderItemList = new ArrayList<>();
			OmsOrder omsOrder = new OmsOrder();
			omsOrder.setAutoConfirmDay(7); //自动确认收货时间
			omsOrder.setConfirmStatus(OmsConst.CONFIRM_STATUS_NOT_SURE);    //订单确认状态
			omsOrder.setStatus(OmsConst.STATUS_WILL_PAY);                    //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
			omsOrder.setCreateTime(new Date());							//订单创建时间

			String outTradeNo = outTradeNo();
			omsOrder.setOrderSn(outTradeNo);                                    //订单编号


			OmsOrderItem omsOrderItem = new OmsOrderItem();
			//根据用户id获得要购买的商品列表   即购物车  还有勾选的总价格
			for (OmsCartItem omsCartItem : cartListChecked) {

				//验价,根据商品id 和 商品价格 检验
				boolean ok = skuService.checkPrice(omsCartItem.getProductSkuId(), omsCartItem.getPrice());
				if (!ok) {
					model.addAttribute("errMsg", "价格有误，请刷新后重新添加购物车");
					return "tradeFail";
				}

				//验库存，远程调用库存系统

				omsOrderItem.setProductPic(omsCartItem.getProductPic());        //商品图片
				omsOrderItem.setProductName(omsCartItem.getProductName());        //商品名称
				omsOrderItem.setProductPrice(omsCartItem.getPrice());            //单一商品的价格
				omsOrderItem.setProductQuantity(omsCartItem.getQuantity());        //单一商品的数量
				omsOrderItem.setOrderSn(outTradeNo);                            //订单号  供其他系统使用
				omsOrderItem.setProductCategoryId(omsCartItem.getProductCategoryId());
				omsOrderItem.setRealAmount(calcRealAmount(omsCartItem));        //减免后的最终总价格
				omsOrderItem.setProductSkuCode("11111");                        //商品条形码
				omsOrderItem.setProductSn("2222");                                //在仓库中的skuid
				omsOrderItem.setProductSkuId(omsCartItem.getProductSkuId());    //skuid
				omsOrderItem.setProductId(omsCartItem.getProductId());            //spuid

				omsOrderItemList.add(omsOrderItem);
			}


			omsOrder.setOmsOrderItemList(omsOrderItemList);

			//todo calcPayAmount(omsOrder) 方法待完成
			omsOrder.setPayAmount(new BigDecimal(totalAmount));       //应付金额（实际支付金额）
			//todo getTotalAmount(omsOrder) 方法待完成
			omsOrder.setTotalAmount(new BigDecimal(totalAmount));     //实际商品金额
			omsOrder.setDeleteStatus(OmsConst.ORDER_NOT_DELETED);     //订单删除状态
			omsOrder.setMemberId(memberId);                           //用户id
			omsOrder.setReceiverName(nickname);                       //用户昵称姓名
			//todo 用户登录名
			//omsOrder.setMemberUsername();							  //用户登录名
			omsOrder.setNote(orderComment);                       	  //订单备注
			//omsOrder.setFreightAmount();							  //运费，在订单生成后物流系统写进来
			omsOrder.setOrderType(orderType);                         //订单类型：0->正常订单；1->秒杀订单

			//获取用户地址
			UmsMemberReceiveAddress address = umsMemberService.getReceiveAddressById(receiveAddressId);
			omsOrder.setReceiverCity(address.getCity());
			omsOrder.setReceiverDetailAddress(address.getDetailAddress());
			omsOrder.setReceiverName(address.getName());
			omsOrder.setReceiverPhone(address.getPhoneNumber());
			omsOrder.setReceiverPostCode(address.getPostCode());
			omsOrder.setReceiverProvince(address.getProvince());
			omsOrder.setReceiverRegion(address.getRegion());

			omsOrder.setSourceType(sourceType);//订单来源：0->PC订单；1->app订单

			//将订单和订单详情写入数据库
			//将购物车删除
			orderService.saveOrder(omsOrder);


			//重定向到支付系统
			return "redirect:http://payment.qmall.com:8881/index?outTradeNo="+outTradeNo+"&totalAmount="+totalAmount;

		} else {
			//交易码过期

			//返回错误页面
			model.addAttribute("errMsg", "请勿重复提交订单");
			return "tradeFail";


		}
	}


	/**
	 * 跳转订单页
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toTrade")
	@LoginRequired
	public String toTrade(HttpServletRequest request, Model model) {

		Integer memberId = (Integer) request.getAttribute("memberId");
		UmsMember entity = umsMemberService.selectById(memberId);

		//获取用户地址
		List<UmsMemberReceiveAddress> address = umsMemberService.getReceiveAddressByMemberId(memberId);

		//先更新缓存
		cartService.flushCacheByMemberId(memberId);
		// 获取所有购物车
		List<OmsCartItem> cartList = cartService.getCartListByMemberId(memberId);
		// 筛选购物车中勾选的商品
		List<OmsCartItem> cartListChecked = cartList.stream()
				.filter((x) -> x.getIsChecked().equals(OmsConst.CHECK))
				.collect(Collectors.toList());

		model.addAttribute("omsOrderItems", cartListChecked);
		model.addAttribute("userAddressList", address);
		model.addAttribute("totalAmount", getTotalAmount(cartListChecked));
		model.addAttribute("entity", entity);

		//生成交易码，服务器上放一份，结算页面放一份
		String tradeCode = orderService.createTradeCode(memberId, cartListChecked);
		model.addAttribute("tradeCode", tradeCode);
		System.out.println("tradeCode = " + tradeCode);
		return "trade";
	}


	/**
	 * 计算订单页面的总价格
	 *
	 * @return
	 */
	private BigDecimal getTotalAmount(List<OmsCartItem> cartList) {
		BigDecimal total = new BigDecimal("0");
		for (OmsCartItem omsCartItem : cartList) {
			BigDecimal quantity = new BigDecimal(omsCartItem.getQuantity());
			BigDecimal multiply = omsCartItem.getPrice().multiply(quantity);
			total = total.add(multiply);
		}
		return total;
	}

	/**
	 * 计算单一商品的总价格，设置进omsOrderItem中
	 * <p>
	 * 数量 * 单价 - 商品促销分解金额 - 优惠券优惠分解金额 - 积分优惠分解金额
	 * <p>
	 * promotion_amount		商品促销分解金额
	 * coupon_amount		优惠券优惠分解金额
	 * integration_amount	积分优惠分解金额
	 *
	 * @return
	 */
	private BigDecimal calcRealAmount(OmsCartItem omsCartItem) {
		//todo  计算单一商品的总价格
		BigDecimal quantity = new BigDecimal(omsCartItem.getQuantity());    //数量
		BigDecimal price = omsCartItem.getPrice();                            //价格

		return price.multiply(quantity);    //价格 * 数量
	}


	/**
	 * 计算应付订单价格
	 * <p>
	 * 每一项的omsOrderItem 中的 real_amount 相加 + 运费金额 - 管理员后台调整订单使用的折扣金额
	 *
	 * @param omsOrder
	 * @return
	 */
	private BigDecimal calcPayAmount(OmsOrder omsOrder) {
		List<OmsOrderItem> omsOrderItemList = omsOrder.getOmsOrderItemList();
		for (OmsOrderItem omsOrderItem : omsOrderItemList) {

		}
		//这是item的字段
		//promotion_amount	促销优化金额（促销价、满减、阶梯价）
		//integration_amount	积分抵扣金额
		//coupon_amount	优惠券抵扣金额

		//freight_amount  运费金额
		//discount_amount	管理员后台调整订单使用的折扣金额


		return null;
	}

	/**
	 * 计算实际商品金额 总订单价格
	 *
	 * @param omsOrder
	 * @return
	 */
	private BigDecimal getTotalAmount(OmsOrder omsOrder) {
		BigDecimal total = new BigDecimal("0");
		List<OmsOrderItem> omsOrderItemList = omsOrder.getOmsOrderItemList();
		for (OmsOrderItem omsOrderItem : omsOrderItemList) {
			BigDecimal quantity = new BigDecimal(omsOrderItem.getProductQuantity());
			BigDecimal multiply = omsOrderItem.getProductPrice().multiply(quantity);
			total = total.add(multiply);
		}
		return total;
	}


	/**
	 * 生成订单号
	 *
	 * @return
	 */
	private String outTradeNo() {

		return "digital" + UUID.randomUUID().toString().replaceAll("-", "").substring(16) + DateUtil.getTimesPlus();


	}


}
