package com.bat.shop.api.service.oms;

import com.bat.shop.api.bean.oms.OmsCartItem;
import com.bat.shop.api.bean.oms.OmsOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 15:03
 * @function:
 */
public interface OrderService {

	/**
	 * 生成交易码
	 * @param memberId
	 * @param cartListChecked
	 * @return
	 */
	String createTradeCode(Integer memberId,List<OmsCartItem> cartListChecked);

	/**
	 * 检查交易码
	 * @param memberId
	 * @param cartListChecked
	 * @return
	 */
	boolean checkTradeCode(Integer memberId,List<OmsCartItem> cartListChecked,String tradeCode);

	/**
	 * 保存订单
	 * @param omsOrder
	 */
	void saveOrder(OmsOrder omsOrder);
}
