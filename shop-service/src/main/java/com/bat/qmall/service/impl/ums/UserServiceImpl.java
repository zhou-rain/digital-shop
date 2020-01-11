package com.bat.qmall.service.impl.ums;

import com.alibaba.dubbo.config.annotation.Service;
import com.bat.shop.api.bean.User;
import com.bat.shop.api.mapper.UserMapper;
import com.bat.shop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/11 - 17:27
 * @function:
 */
@Service
@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Override
	public User selectById(int i) {
		System.out.println("userMapper = " + userMapper);

		return userMapper.selectById(i);
	}

	@Override
	public List<User> selectAll() {
		return userMapper.selectList(null);
	}
}
