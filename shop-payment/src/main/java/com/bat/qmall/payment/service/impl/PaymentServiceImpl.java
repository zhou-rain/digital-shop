package com.bat.qmall.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.shop.api.bean.pay.PaymentInfo;
import com.bat.shop.api.mapper.pay.PaymentInfoMapper;
import com.bat.shop.api.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhouR
 * @date: Create in 2020/2/2 - 14:44
 * @function:
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentInfoMapper paymentInfoMapper;

	/**
	 * 保存支付宝交易信息
	 * @param paymentInfo
	 */
	@Override
	public void savePaymentInfo(PaymentInfo paymentInfo) {

		paymentInfoMapper.insert(paymentInfo);

	}

	/**
	 * 根据对外订单号 修改支付状态
	 * @param paymentInfo
	 */
	@Override
	public void updatePaymentInfoByOutTradeNo(PaymentInfo paymentInfo) {

		PaymentInfo entity = new PaymentInfo();
		entity.setOrderSn(paymentInfo.getOrderSn());
		QueryWrapper<PaymentInfo> wapper = new QueryWrapper<>(entity);

		paymentInfoMapper.update(paymentInfo,wapper);
	}
}
