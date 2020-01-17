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

	/**
	 * 根据spuid 查询spu销售属性列表
	 * @param spuId productId
	 * @return
	 */
	@Override
	public List<PmsProductSaleAttr> spuSaleAttrListBySpuId(String spuId) {

		//获取商品属性集合
		PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
		pmsProductSaleAttr.setProductId(spuId);
		QueryWrapper<PmsProductSaleAttr> aqw = new QueryWrapper<>(pmsProductSaleAttr);
		List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectList(aqw);

		//获取商品属性值集合
		for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs) {
			PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
			//销售属性用的是系统的字典表中的id，不是销售属性表的主键
			pmsProductSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
			pmsProductSaleAttrValue.setProductId(spuId);
			QueryWrapper<PmsProductSaleAttrValue> avqw = new QueryWrapper<>(pmsProductSaleAttrValue);
			List<PmsProductSaleAttrValue> pmsProductSaleAttrValueList = pmsProductSaleAttrValueMapper.selectList(avqw);
			productSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValueList);
		}

		return pmsProductSaleAttrs;
	}

	/**
	 * 根据spuid 查询spu的图片列表
	 * @param spuId productId
	 * @return
	 */
	@Override
	public List<PmsProductImage> spuImageListBySpuId(String spuId) {

		PmsProductImage pmsProductImage = new PmsProductImage();
		pmsProductImage.setProductId(spuId);
		QueryWrapper<PmsProductImage> queryWrapper = new QueryWrapper<>(pmsProductImage);
		return pmsProductImage.selectList(queryWrapper);

	}

	/**
	 * 根据productId 获取商品销售属性
	 * @param productId
	 * @return
	 */
	@Override
	public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId) {

		//销售属性
		QueryWrapper<PmsProductSaleAttr> wapper = new QueryWrapper<>();
		wapper.eq("product_id",productId);
		List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectList(wapper);

		//销售属性值
		for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs) {
			String saleAttrId = productSaleAttr.getId();
			QueryWrapper<PmsProductSaleAttrValue> qw = new QueryWrapper<>();
			//对应的商品id  和对应的属性id
			qw.eq("sale_attr_id",saleAttrId).eq("product_id",productId);
			List<PmsProductSaleAttrValue> productSaleAttrValueList = pmsProductSaleAttrValueMapper.selectList(qw);
			productSaleAttr.setSpuSaleAttrValueList(productSaleAttrValueList);
		}

		return pmsProductSaleAttrs;
	}





}
