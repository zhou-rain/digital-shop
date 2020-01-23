package com.bat.qmall.cart.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.shop.api.bean.oms.OmsCartItem;
import com.bat.shop.api.mapper.oms.OmsCartItemMapper;
import com.bat.shop.api.service.oms.CartService;
import com.bat.qmall.Const.RedisConst;
import com.bat.qmall.exception.EmptyException;
import com.bat.qmall.exception.ErrException;
import com.bat.qmall.utils.RedisUtil;
import com.bat.qmall.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhouR
 * @date: Create in 2020/1/15 - 9:43
 * @function:
 */
@Service
@Component
public class CartServiceImpl implements CartService {

	@Autowired
	OmsCartItemMapper omsCartItemMapper;
	@Autowired
	RedisUtil redisUtil;


	/**
	 * 根据用户id 和商品skuId  获取购物车对象
	 *
	 * @param memberId
	 * @param skuId
	 * @return
	 */
	@Override
	public OmsCartItem getOmsCartItemByMemberIdAndSkuId(Integer memberId, String skuId) {

		QueryWrapper<OmsCartItem> wapper = new QueryWrapper<>();
		wapper.eq("member_id",memberId)
				.eq("product_sku_id",skuId);
		return omsCartItemMapper.selectOne(wapper);
	}

	/**
	 * 保存/更新  购物车
	 *
	 * @param cartItem
	 */
	@Override
	public void save(OmsCartItem cartItem) throws ErrException, EmptyException {


		if (Validator.isEmpty(cartItem.getId())) {
			//新增
			if (Validator.isEmpty(cartItem.getMemberId())) {
				throw new EmptyException("用户id不能为空");
			}
			try {
				omsCartItemMapper.insert(cartItem);
			} catch (Exception e) {
				throw new ErrException();
			}

		} else {
			//更新
			try {
				omsCartItemMapper.updateById(cartItem);
			} catch (Exception e) {
				throw new ErrException();
			}

		}

	}

	/**
	 * 根据用户id同步缓存
	 *
	 * @param memberId
	 */
	@Override
	public void flushCacheByMemberId(Integer memberId) {
		OmsCartItem omsCartItem = new OmsCartItem();
		omsCartItem.setMemberId(memberId);
		QueryWrapper<OmsCartItem> wrapper = new QueryWrapper<>(omsCartItem);
		List<OmsCartItem> cartItemList = omsCartItemMapper.selectList(wrapper);

		System.out.println("cartItemList =》》》》》》》》》 " + cartItemList);

		//同步到redis缓存中
		Jedis jedis = redisUtil.getJedis();

		//将数据以 user:memberId:cart	skuId-skuinfo 存入redis
		Map<String,String> map = new HashMap<>();
		for (OmsCartItem cartItem : cartItemList) {
			map.put(cartItem.getId(), JSON.toJSONString(cartItem));
		}
		jedis.hmset(RedisConst.getCartKey(memberId),map);

		System.out.println("map =》》》》》》》》》》》》》》》 " + map);

		jedis.close();
	}
}
