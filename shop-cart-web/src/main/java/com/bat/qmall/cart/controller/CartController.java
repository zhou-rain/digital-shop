package com.bat.qmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.bat.shop.api.bean.oms.OmsCartItem;
import com.bat.shop.api.bean.pms.PmsSkuInfo;
import com.bat.shop.api.service.oms.CartService;
import com.bat.shop.api.service.pms.SkuService;
import com.bat.qmall.Const.OmsConst;
import com.bat.qmall.exception.MsgException;
import com.bat.qmall.utils.Validator;
import com.bat.qmall.webUtils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 18:59
 * @function:
 */
@Controller
public class CartController {

	@Reference
	CartService cartService;
	@Reference
	SkuService skuService;

	/**
	 * 购物车添加功能
	 *
	 * @return 购物车类型：
	 * DB：		cartListDb
	 * Redis：	cartListRedis
	 * Cookie：cartListCookie
	 */
	@RequestMapping("addToCart")
	public String addToCart(String skuId, int quantity, HttpServletRequest request, HttpServletResponse response) {
		List<OmsCartItem> cartList = new ArrayList<>();

		//1、传递参数（商品skuId，商品数量）
		//根据skuId调用skuService查询商品详情信息
		PmsSkuInfo skuInfo = skuService.getSkuById(skuId);

		//2、将商品详细信息封装成购物车信息
		OmsCartItem cartItem = new OmsCartItem();

		Date date = new Date();
		cartItem.setCreateDate(date);                            //新创建时间
		cartItem.setModifyDate(date);                            //修改时间
		cartItem.setDeleteStatus(OmsConst.CART_NOT_DELETED);     //删除状态
		cartItem.setPrice(skuInfo.getPrice());                   //价格
		cartItem.setProductAttr("");                             //销售属性
		cartItem.setProductBrand("");                            //商标
		cartItem.setProductCategoryId(skuInfo.getCatalog3Id());  //三级分类id
		cartItem.setProductId(skuInfo.getProductId());           //商品id
		cartItem.setProductName(skuInfo.getSkuName());           //商品名称
		cartItem.setProductPic(skuInfo.getSkuDefaultImg());      //图片
		cartItem.setProductSkuCode("11111111111");               //条形码
		cartItem.setProductSkuId(skuId);                         //skuId
		cartItem.setQuantity(quantity);                          //购买数量


		//3、判断用户是否登录
		//Integer memberId = null;    //没登录
		Integer memberId = 1; 		//登录了
		String memberNickName = "测试用户";

		//4、根据用户登录信息，判断走cookie的分支还是db，购物车数据的写入操作
		if (Validator.isEmpty(memberId)) {
			//用户没登录

			String cartListCookie = CookieUtil.getCookieValue(request, OmsConst.CART_COOKIE, true);

			if (Validator.isEmpty(cartListCookie)) {
				//如果cookie为空，直接添加
				cartList.add(cartItem);
			} else {
				//cookie不为空
				cartList = JSON.parseArray(cartListCookie, OmsCartItem.class);

				boolean exist = isInArray(skuId, cartList);
				if(exist){
					//有添加过相同的商品，将数量+新加的数量
					for (OmsCartItem item : cartList) {
						if(item.getProductSkuId().equals(skuId)){
							item.setQuantity(item.getQuantity()+quantity);
							break;
						}
					}
				}else {
					//新商品
					cartList.add(cartItem);
				}

			}

			//设置进cookie
			String cartListJson = JSON.toJSON(cartList).toString();
			CookieUtil.setCookie(request, response, OmsConst.CART_COOKIE, cartListJson, OmsConst.COOKIE_MAXAGE, true);


		} else {
			//用户已经登录

			//去数据库中，根据用户id和要添加的商品，其查询购物车有没有该项
			OmsCartItem cart = cartService.getOmsCartItemByMemberIdAndSkuId(memberId,skuId);

			if (Validator.isEmpty(cart)) {
				//如果该用户没添加过这个商品

				//直接添加购物车
				cartItem.setMemberId(memberId);
				cartItem.setMemberNickname(memberNickName);
				try {
					cartService.save(cartItem);
				} catch (MsgException e) {
					e.printStackTrace();
				}


			}else {
				//该用户添加过这个商品

				//数量相加
				cart.setQuantity(cart.getQuantity()+quantity);
				try {
					cartService.save(cart);
				} catch (MsgException e) {
					e.printStackTrace();
				}
			}

			//同步缓存
			cartService.flushCacheByMemberId(memberId);






		}

		return "redirect:/success.html";
	}


	/**
	 * 判断要添加的商品是否在cookie内
	 * true/存在  false/不存在
	 * @param skuId
	 * @param list
	 * @return
	 */
	boolean isInArray(String skuId, List<OmsCartItem> list) {
		for (OmsCartItem item : list) {
			if (item.getProductSkuId().equals(skuId)) {
				return true;
			}
		}
		return false;
	}


	@RequestMapping("/GotoShoppingCart")
	public String GotoShoppingCart() {

		return "";
	}


}
