package com.bat.shop.api.service;

import com.bat.shop.api.bean.User;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/12 - 23:47
 * @function:
 */
public interface UserService {
	User selectById(int i);

	List<User> selectAll();
}
