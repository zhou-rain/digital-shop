package com.bat.gmall.product.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.pms.PmsSkuInfo;
import com.bat.shop.api.service.pms.SkuService;
import com.bat.shop.common.exception.ErrException;
import com.bat.shop.common.utils.Validator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhouR
 * @date: Create in 2020/1/16 - 10:26
 * @function:
 */
@RestController
@CrossOrigin
public class SkuController {

	@Reference
	SkuService skuService;


	//保存sku信息
	@RequestMapping("/saveSkuInfo")
	public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){

		//前端传值有误，设置默认值
		pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());

		//处理默认图片
		if(Validator.isEmpty(pmsSkuInfo.getSkuDefaultImg())){
			pmsSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuImageList().get(0).getImgUrl());
		}

		try {

			skuService.saveSkuInfo(pmsSkuInfo);
		} catch (ErrException e) {
			return e.getMessage();
		}
		return "ok";
	}
}
