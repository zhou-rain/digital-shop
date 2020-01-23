package com.bat.shop.api.service.oms;

import com.bat.shop.api.bean.oms.OmsCartItem;
import com.bat.qmall.exception.EmptyException;
import com.bat.qmall.exception.ErrException;

/**
 * @author: zhouR
 * @date: Create in 2020/1/15 - 9:44
 * @function:
 */
public interface CartService {

	/**
	 * 根据用户id 和商品skuId  获取购物车对象
	 * @param memberId
	 * @param skuId
	 * @return
	 */
	OmsCartItem getOmsCartItemByMemberIdAndSkuId(Integer memberId, String skuId);

	/**
	 * 保存/更新  购物车
	 * @param cartItem
	 */
	void save(OmsCartItem cartItem) throws EmptyException, ErrException;

	/**
	 * 根据用户id同步缓存
	 * @param memberId
	 */
	void flushCacheByMemberId(Integer memberId);

}
