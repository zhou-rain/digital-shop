package com.bat.shop.api.service.impl;

import com.bat.shop.api.bean.UmsAdmin;
import com.bat.shop.api.mapper.UmsAdminMapper;
import com.bat.shop.api.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 13:49
 * @function:
 */
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	UmsAdminMapper adminMapper;


	@Override
	public UmsAdmin getEntityById(int id) {
		return adminMapper.selectByPrimaryKey(id);
	}
}
