package com.bat.qmall.product.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.shop.api.bean.pms.*;
import com.bat.shop.api.mapper.pms.*;
import com.bat.shop.api.service.pms.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/15 - 15:00
 * @function:	销售属性
 */
@Component
@Service
public class SpuServiceImpl implements SpuService {

	@Autowired
	PmsProductInfoMapper pmsProductInfoMapper;
	@Autowired
	PmsProductImageMapper pmsProductImageMapper;
	@Autowired
	PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
	@Autowired
	PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;


	/**
	 * 根据三级分类，查询Spu  商品分类集合  电脑下面有很多品牌的电脑
	 * @param catalog3Id
	 * @return
	 */
	@Override
	public List<PmsProductInfo> getSpuListByCatalog3Id(String catalog3Id) {

		PmsProductInfo pmsProductInfo = new PmsProductInfo();
		pmsProductInfo.setCatalog3Id(catalog3Id);

		QueryWrapper<PmsProductInfo> wrapper = new QueryWrapper<>(pmsProductInfo);
		return pmsProductInfo.selectList(wrapper);
	}

	/**
	 *	保存spu信息
	 * @param pmsProductInfo
	 * @return
	 */
	@Override
	public String saveSpuInfo(PmsProductInfo pmsProductInfo) {

		//保存productInfo表信息
		pmsProductInfoMapper.insert(pmsProductInfo);
		String productId = pmsProductInfo.getId();//主键已回填

		//保存productImage表
		List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
		for (PmsProductImage pmsProductImage : spuImageList) {
			pmsProductImage.setProductId(productId);
			pmsProductImageMapper.insert(pmsProductImage);
		}

		List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
		for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {

			//保存属性信息
			pmsProductSaleAttr.setProductId(productId);
			pmsProductSaleAttrMapper.insert(pmsProductSaleAttr);

			String saleAttrId = pmsProductSaleAttr.getId();

			List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
			for (PmsProductSaleAttrValue pmsProductSaleAttrValue : spuSaleAttrValueList) {
				//保存属性值信息
				pmsProductSaleAttrValue.setProductId(productId);
				pmsProductSaleAttrValue.setSaleAttrId(saleAttrId);
				pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValue);
			}
		}

		return "success";
	}


}
