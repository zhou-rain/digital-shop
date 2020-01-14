package com.bat.qmall.cart.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bat.shop.api.bean.ums.UmsMember;
import com.bat.shop.api.mapper.ums.UmsMemberMapper;
import com.bat.shop.api.service.ums.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/13 - 14:52
 * @function:
 */
@Service
@Component
public class TestImpl implements UmsMemberService {
	@Autowired
	UmsMemberMapper userMapper;

	@Override
	public UmsMember selectById(int i) {
		System.out.println("userMapper = " + userMapper);

		return userMapper.selectById(i);
	}

	@Override
	public List<UmsMember> selectAll() {
		return userMapper.selectList(null);
	}

}
