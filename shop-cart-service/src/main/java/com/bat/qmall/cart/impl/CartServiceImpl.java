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

import java.util.ArrayList;
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


	//根据用户id 和商品skuId  获取购物车对象
	@Override
	public OmsCartItem getOmsCartItemByMemberIdAndSkuId(Integer memberId, String skuId) {

		QueryWrapper<OmsCartItem> wapper = new QueryWrapper<>();
		wapper.eq("member_id", memberId)
				.eq("product_sku_id", skuId);
		return omsCartItemMapper.selectOne(wapper);
	}

	//保存/更新  购物车
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

	//根据用户id同步缓存
	@Override
	public void flushCacheByMemberId(Integer memberId) {
		OmsCartItem omsCartItem = new OmsCartItem();
		omsCartItem.setMemberId(memberId);
		QueryWrapper<OmsCartItem> wrapper = new QueryWrapper<>(omsCartItem);
		List<OmsCartItem> cartItemList = omsCartItemMapper.selectList(wrapper);


		//同步到redis缓存中
		Jedis jedis = redisUtil.getJedis();

		//将数据以 user:memberId:cart	skuId-skuinfo 存入redis
		Map<String, String> map = new HashMap<>();
		for (OmsCartItem cartItem : cartItemList) {
			map.put(cartItem.getId(), JSON.toJSONString(cartItem));
		}
		//先将缓存删除
		jedis.del(RedisConst.getCartKey(memberId));
		//将缓存加入
		jedis.hmset(RedisConst.getCartKey(memberId), map);

		jedis.close();
	}

	//根据用户id，从redis中获取购物车信息
	@Override
	public List<OmsCartItem> getCartListByMemberId(Integer memberId) {

		List<OmsCartItem> omsCartItems = new ArrayList<>();
		Jedis jedis = null;

		try {
			jedis = redisUtil.getJedis();

			List<String> hvals = jedis.hvals(RedisConst.getCartKey(memberId));
			for (String hval : hvals) {
				OmsCartItem omsCartItem = JSON.parseObject(hval, OmsCartItem.class);
				omsCartItems.add(omsCartItem);
			}

		} catch (Exception e) {
			//logService.addErrLog(e.getMessage());
			return null;
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return omsCartItems;
	}



	//更改购物车状态
	@Override
	public void checkCart(OmsCartItem entity) {

		OmsCartItem omsCartItem = new OmsCartItem();
		omsCartItem.setProductSkuId(entity.getProductSkuId());
		omsCartItem.setMemberId(entity.getMemberId());

		QueryWrapper<OmsCartItem> wrapper = new QueryWrapper<>(omsCartItem);

		omsCartItemMapper.update(entity,wrapper);


		//缓存同步
		flushCacheByMemberId(entity.getMemberId());

	}




}
