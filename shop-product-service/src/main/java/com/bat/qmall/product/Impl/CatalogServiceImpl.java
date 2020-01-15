package com.bat.qmall.product.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.shop.api.bean.pms.PmsBaseCatalog1;
import com.bat.shop.api.bean.pms.PmsBaseCatalog2;
import com.bat.shop.api.bean.pms.PmsBaseCatalog3;
import com.bat.shop.api.mapper.pms.PmsBaseCatalog1Mapper;
import com.bat.shop.api.mapper.pms.PmsBaseCatalog2Mapper;
import com.bat.shop.api.mapper.pms.PmsBaseCatalog3Mapper;
import com.bat.shop.api.service.pms.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 15:34
 *
 * 三级分类
 */
@Service
@Component
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    PmsBaseCatalog1Mapper catalog1Mapper;
    @Autowired
	PmsBaseCatalog2Mapper catalog2Mapper;
    @Autowired
	PmsBaseCatalog3Mapper catalog3Mapper;



	@Override
	public List<PmsBaseCatalog1> getCatalog1List() {
		return catalog1Mapper.selectList(null);
	}

	@Override
	public List<PmsBaseCatalog2> getCatalog2List(String catalog1Id) {
		PmsBaseCatalog2 catalog2 = new PmsBaseCatalog2();
		catalog2.setCatalog1Id(catalog1Id);
		QueryWrapper<PmsBaseCatalog2>  wrapper = new QueryWrapper<>(catalog2);
		return catalog2Mapper.selectList(wrapper);
	}

	@Override
	public List<PmsBaseCatalog3> getCatalog3List(String catalog2Id) {
		PmsBaseCatalog3 catalog3 = new PmsBaseCatalog3();
		catalog3.setCatalog2Id(catalog2Id);
		QueryWrapper<PmsBaseCatalog3>  wrapper = new QueryWrapper<>(catalog3);
		return catalog3Mapper.selectList(wrapper);
	}
}
