package com.bat.qmall.order.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bat.shop.api.bean.oms.OmsOrder;
import com.bat.shop.api.mapper.oms.OrderMapper;
import com.bat.shop.api.service.oms.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 15:03
 * @function:
 */
@Service
@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;


	@Override
	public OmsOrder getEntityById(int i) {
		return orderMapper.selectById(i);
	}
}
