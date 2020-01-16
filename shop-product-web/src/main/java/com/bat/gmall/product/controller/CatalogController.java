package com.bat.gmall.product.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.pms.PmsBaseCatalog1;
import com.bat.shop.api.bean.pms.PmsBaseCatalog2;
import com.bat.shop.api.bean.pms.PmsBaseCatalog3;
import com.bat.shop.api.service.pms.CatalogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 20:25
 * @function: 三级分类
 */

@RestController
@CrossOrigin
public class CatalogController {

	@Reference(check = false)
	CatalogService catalogService;


	/**
	 * 获取三级标题
	 * @return
	 */
	@RequestMapping("getCatalog3")
	public List<PmsBaseCatalog3> getCatalog3(String catalog2Id){
		return catalogService.getCatalog3List(catalog2Id);

	}

	/**
	 * 获取二级标题
	 * @return
	 */
	@RequestMapping("getCatalog2")
	public List<PmsBaseCatalog2> getCatalog2(String catalog1Id){
		return catalogService.getCatalog2List(catalog1Id);
	}


	/**
	 * 获取一级标题
	 * @return
	 */
	@RequestMapping("getCatalog1")
	public List<PmsBaseCatalog1> getCatalog1(){
		return catalogService.getCatalog1List();
	}
	


}

