package com.bat.gmall.product.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.pms.PmsBaseSaleAttr;
import com.bat.shop.api.bean.pms.PmsProductImage;
import com.bat.shop.api.bean.pms.PmsProductInfo;
import com.bat.shop.api.bean.pms.PmsProductSaleAttr;
import com.bat.shop.api.service.pms.SpuService;
import com.bat.shop.common.commons.Msg;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/15 - 12:49
 * @function:
 */
@RestController
@CrossOrigin
public class SpuController {

	@Reference
	SpuService spuService;


	//spu销售属性列表
	@RequestMapping("/spuSaleAttrList")
	public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){
		return spuService.spuSaleAttrListBySpuId(spuId);
	}


	//spu的图片列表
	@RequestMapping("/spuImageList")
	public List<PmsProductImage> spuImageList(String spuId){
		return spuService.spuImageListBySpuId(spuId);
	}






	/**
	 * 图片上传
	 *
	 * 将图片或者音视频上传到分布式的文件存储系统
	 *
	 * @param multipartFile
	 * @return
	 */
	@RequestMapping("/fileUpload")
	public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
		String imgUrl = "http://112.124.25.228/images/aa.png";

		System.out.println("imgUrl = " + imgUrl);
		//将图片的存储路径返回给页面

		//将图片的元数据保存到数据库  （名称 路径）


		//功能后期再做，这里模拟一张数据库中的图片
		//图片存储服务器 - fastdfs
		//https://www.bilibili.com/video/av55643074?p=51
		
		return imgUrl;
	}

	/**
	 * 保存SPU信息
	 * @param pmsProductInfo
	 * @return
	 */
	@RequestMapping("/saveSpuInfo")
	public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){

		return spuService.saveSpuInfo(pmsProductInfo);
	}


	/**
	 * 根据三级分类，查询Spu  商品分类集合  电脑下面有很多品牌的电脑
	 * @param catalog3Id
	 * @return
	 */
	@RequestMapping("/spuList")
	public List<PmsProductInfo> spuList(String catalog3Id){
		return spuService.getSpuListByCatalog3Id(catalog3Id);
	}
	
}
