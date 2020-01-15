package com.bat.shop.api.service.pms;

import com.bat.shop.api.bean.pms.PmsBaseCatalog1;
import com.bat.shop.api.bean.pms.PmsBaseCatalog2;
import com.bat.shop.api.bean.pms.PmsBaseCatalog3;

import java.util.List;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 23:01
 */
public interface CatalogService {
	/**
	 * 获取一级分类
	 */
	List<PmsBaseCatalog1> getCatalog1List();

	/**
	 * 获取二级分类
	 */
	List<PmsBaseCatalog2> getCatalog2List(String catalog2Id);
	/**
	 * 获取三级分类
	 */
	List<PmsBaseCatalog3> getCatalog3List(String catalog2Id);
}
