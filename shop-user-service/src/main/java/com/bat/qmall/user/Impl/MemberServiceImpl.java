package com.bat.qmall.user.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.qmall.Const.RedisConst;
import com.bat.qmall.utils.RedisUtil;
import com.bat.qmall.utils.Validator;
import com.bat.shop.api.bean.ums.UmsMember;
import com.bat.shop.api.mapper.ums.UmsMemberMapper;
import com.bat.shop.api.service.ums.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 16:04
 */
@Service
@Component
public class MemberServiceImpl implements UmsMemberService {

	@Autowired
	UmsMemberMapper umsMemberMapper;
	@Autowired
	RedisUtil redisUtil;


	@Override
	public UmsMember selectById(int i) {
		return umsMemberMapper.selectById(i);
	}

	@Override
	public List<UmsMember> selectAll() {
		return umsMemberMapper.selectList(null);
	}


	/**
	 * 用户登录  （缓存）
	 *
	 * @param umsMember
	 * @return
	 */
	@Override
	public UmsMember login(UmsMember umsMember) {
		if (Validator.isEmpty(umsMember)) return null;
		String username = umsMember.getUsername();
		String password = umsMember.getPassword();
		if (Validator.isEmpty(username) && Validator.isEmpty(password)) return null;


		Jedis jedis = null;
		try {
			jedis = redisUtil.getJedis();

			String memberInfoKey = RedisConst.getMemberInfoKey(username, password);
			//查询缓存
			if (jedis != null) {
				String s1 = jedis.get(memberInfoKey);
				if (Validator.isNotEmpty(s1)) {
					//缓存中有
					String s = jedis.get(memberInfoKey);
					return JSON.parseObject(s, UmsMember.class);

				} else {
					//缓存中没有，去数据库查询
					UmsMember umsMemberFromDb = this.loginFromDb(umsMember);
					//如果数据查到了，存入redis
					if (umsMemberFromDb != null) {
						jedis.setex(memberInfoKey, 60 * 60 * 24, JSON.toJSONString(umsMemberFromDb));
					}
					return umsMemberFromDb;
				}

			} else {
				//缓存连接异常，开数据查询
				return this.loginFromDb(umsMember);
			}


		} finally {
			if (jedis != null)
				jedis.close();
		}

	}

	/**
	 * 根据用户id  将token存入redis
	 * @param token
	 * @param memberId
	 */
	@Override
	public void addMemberTokenToRedisByMemberId(String token, Integer memberId) {

		Jedis jedis = redisUtil.getJedis();

		try {
			String key = RedisConst.getMembertokenKey(memberId);
			jedis.setex(key,60*60*2,token);
		} finally {
			jedis.close();
		}

	}

	/**
	 * 从数据库中查询用户登录信息
	 *
	 * @param umsMember
	 * @return
	 */
	private UmsMember loginFromDb(UmsMember umsMember) {

		QueryWrapper<UmsMember> wrapper = new QueryWrapper<>(umsMember);
		List<UmsMember> umsMembers = umsMemberMapper.selectList(wrapper);

		return umsMembers.isEmpty() ? null : umsMembers.get(0);

	}


}
