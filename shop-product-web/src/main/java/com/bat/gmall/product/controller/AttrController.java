package com.bat.gmall.product.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.pms.PmsBaseAttrInfo;
import com.bat.shop.api.bean.pms.PmsBaseAttrValue;
import com.bat.shop.api.bean.pms.PmsBaseSaleAttr;
import com.bat.shop.api.service.pms.AttrService;
import com.bat.shop.common.exception.MsgException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 22:22
 * @function: 平台属性
 */
@RestController
@CrossOrigin
public class AttrController {

	@Reference(check = false)
	AttrService attrService;

	/**
	 * 删除平台属性值
	 *
	 * @return
	 */
	@RequestMapping("/deleteAttrValueById")
	public String deleteAttrValueById(String id){
		try {
			attrService.delBatch(id);
		} catch (MsgException e) {
			return e.getMessage();
		}
		return "ok";
	}


	/**
	 * 获取平台属性
	 *
	 * @return
	 */
	@RequestMapping("/baseSaleAttrList")
	public List<PmsBaseSaleAttr> baseSaleAttrList(){

		return attrService.getSaleAttrList();
	}



	/**
	 * 保存新增的属性值
	 * @param attrInfo
	 * @return
	 */
	@RequestMapping("saveAttrInfo")
	public String saveAttrInfo(@RequestBody PmsBaseAttrInfo attrInfo){

		attrService.saveAttrInfo(attrInfo);
		return "success";
	}

	/**
	 * 根据属性id 获取属性值
	 * @param attrId
	 * @return
	 */
	@RequestMapping("getAttrValueList")
	public List<PmsBaseAttrValue> getAttrValueList(String attrId){
		return attrService.getValueByattrId(attrId);
	}


	/**
	 * 根据三级分类获取属性
	 * @param catalog3Id
	 * @return
	 */
	@RequestMapping("attrInfoList")
	public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
		return attrService.getAttrInfoList(catalog3Id);
	}


}
