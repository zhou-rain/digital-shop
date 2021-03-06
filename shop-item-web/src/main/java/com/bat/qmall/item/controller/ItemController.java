package com.bat.qmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.bat.shop.api.bean.pms.PmsProductSaleAttr;
import com.bat.shop.api.bean.pms.PmsSkuInfo;
import com.bat.shop.api.bean.pms.PmsSkuSaleAttrValue;
import com.bat.shop.api.service.pms.SkuService;
import com.bat.shop.api.service.pms.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhouR
 * @date: Create in 2020/1/16 - 15:13
 * @function:
 */
@Controller
public class ItemController {

	@Reference
	SkuService skuService;
	@Reference
	SpuService spuService;


	/*
		1、数据模型（根据销售属性切换一个sku的其他兄弟姐妹）
			pms_sku_info
			pms_sku_image

			Pms_sku_sale_attr_value
			Pms_spu_sale_attr
			pms_spu_sale_attr_value




		2、如何实现这些数据模型对应的功能
			A页面得到销售属性列表（当前sku对应spu的id）
				SELECT
				  *
				FROM
				  `pms_product_sale_attr` sa,
				  `pms_product_sale_attr_value` sav
				WHERE sa.`product_id` = sav.`product_id`
				  AND sa.`sale_attr_id` = sav.`sale_attr_id`
				  AND sa.`product_id` = ?
			B页面根据销售属性的选择的组合，定位到关联的sku
				通过页面被选择属性值id
				查询中间表pms_sku_sale_attr_value
				得到skuId
			C页面根据skuId查询到sku对象返回到页面


			现在优化上面的sql
			SELECT sa.*,sav.*,IF(ssav.id,1,0)
			FROM `pms_product_sale_attr` sa
			INNER JOIN `pms_product_sale_attr_value` sav
				ON sav.`product_id`=sa.`product_id`
				AND sav.`sale_attr_id` = sa.`sale_attr_id`
				AND sa.`product_id` = 24
			LEFT JOIN `pms_sku_sale_attr_value` ssav
				ON ssav.sale_attr_value_id = sav.`id`
				AND ssav.sku_id = 11


			if(判断的值,存在设为值,不存在设为值)
			IF(ssav.id,1,0)  如果ssav.id存在，设为1，否则设为0




		添加的时候就已经选择了红色和128G 这已经是一个组合，可以通过这个组合反推出sku
		点击商品详情的时候，就已经有skuIdl了，在url上，然后skuInfo表里有spuId



		D、还可以通过k-v的形式，节省性能

		在pms_sku_sale_attr_value表中，有skuid 和 sale_attr_value_id两个字段
		一个sku对应两个或三个属性 如
		skuId		sale_attr_value_id
		105				235
		105				236
		106				258
		106				259

		其实两个avid就可以确定一个具体的sku信息，得到他的id值

		可以拼接成 key：235,236	value：105
				  key：258,259	value：106
		用户在选择一个组合时，就已经确定了一个具体的sku值，无需再去数据库查询，可以节省一半的性能

	 */



	/**
	 * 根据skuid获取商品详情
	 * @param skuId
	 * @param model
	 * @return
	 */
	@RequestMapping("/{skuId}.html")
	public String item(@PathVariable String skuId,Model model){
		//sku对象
		PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId);

		//销售属性列表
		List<PmsProductSaleAttr> spuSaleAttrListCheckBySku = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),skuId);

		Map<String, String> skuSaleAttrHash = new HashMap<>();
		//查询当前sku的其他sku的集合的hash表
		List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(pmsSkuInfo.getProductId());
		for (PmsSkuInfo skuInfo : pmsSkuInfos) {
			StringBuilder key = new StringBuilder();	//hash表的key
			String skuInfoId = skuInfo.getId();			//hash表的value

			List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
			for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
				key.append(pmsSkuSaleAttrValue.getSaleAttrValueId()).append("|");
			}
			skuSaleAttrHash.put(key.toString(),skuInfoId);

		}
		//将map集合变成json字符串
		String skuSaleAttrHashJsonStr = JSON.toJSONString(skuSaleAttrHash);


		model.addAttribute("skuInfo",pmsSkuInfo);
		model.addAttribute("spuSaleAttrListCheckBySku",spuSaleAttrListCheckBySku);
		model.addAttribute("skuSaleAttrHashJsonStr",skuSaleAttrHashJsonStr);

		return "item";
	}


}
