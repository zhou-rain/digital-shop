package com.bat.qmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.qmall.utils.StringUtil;
import com.bat.qmall.utils.Validator;
import com.bat.shop.api.bean.pms.PmsBaseAttrInfo;
import com.bat.shop.api.bean.pms.PmsBaseAttrValue;
import com.bat.shop.api.bean.pms.PmsSkuAttrValue;
import com.bat.shop.api.bean.search.PmsSearchCrumb;
import com.bat.shop.api.bean.search.PmsSearchParam;
import com.bat.shop.api.bean.search.PmsSearchSkuInfo;
import com.bat.shop.api.service.pms.AttrService;
import com.bat.shop.api.service.pms.SearchService;
import com.bat.qmall.webUtils.Msg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 16:24
 */
@Controller
public class SearchController {

	@Reference
	SearchService searchService;
	@Reference
	AttrService attrService;


	/**
	 * 搜索条件
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping("/list.html")
	public String list(PmsSearchParam param, Model model) {

		List<PmsSearchSkuInfo> pmsSearchSkuInfoList = searchService.list(param);

		//抽取检索结果中的平台属性id集合
		Set<String> valueIdSet = new HashSet<>();

		for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfoList) {
			List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
			for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
				valueIdSet.add(pmsSkuAttrValue.getValueId());
			}
		}
		model.addAttribute("skuLsInfoList", pmsSearchSkuInfoList);

		//根据valueId 将属性列表查询出来
		List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getAttrValueListByValueId(valueIdSet);
		//对平台属性进一步处理，将选择过的平台属性隐藏
		String[] valueIds = param.getValueId();

		if (Validator.isNotEmpty(valueIds)) {
			//面包屑
			List<PmsSearchCrumb> pmsSearchCrumbs = new ArrayList<>();
			//当前请求中包含属性的参数，每一个属性参数，就会生成一个面包屑

			for (String valueId : valueIds) {
				//面包屑的实例
				PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
				//生成参数
				pmsSearchCrumb.setValueId(valueId);        //面包屑的id
				//pmsSearchCrumb.setValueName(valueId);    //面包屑名称   pmsBaseAttrValue 这个里面有
				pmsSearchCrumb.setUrlParam(getUrlParamForCrumbs(param, valueId));            //面包屑参数地址

				//删除
				Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();	//平台属性集合
				while (iterator.hasNext()) {
					PmsBaseAttrInfo next = iterator.next();
					List<PmsBaseAttrValue> attrValueList = next.getAttrValueList();
					for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
						String id = pmsBaseAttrValue.getId();
						if (valueId.equals(id)) {
							//在这里添加面包屑的名称
							pmsSearchCrumb.setValueName(pmsBaseAttrValue.getValueName());    //面包屑名称   pmsBaseAttrValue 这个里面有
							//删除该属性值所在的属性组
							iterator.remove();
						}
					}
				}
				pmsSearchCrumbs.add(pmsSearchCrumb);
			}
			//面包屑写在这里
			model.addAttribute("attrValueSelectedList", pmsSearchCrumbs);
		}

		model.addAttribute("attrList", pmsBaseAttrInfos);


		//将当前的参数列表算出来
		//urlParam的参数列表就是 PmsSearchParam 中提交的参数
		String urlParam = getUrlParamForCrumbs(param,"no");
		model.addAttribute("urlParam", urlParam);

		//关键字回显
		String keyword = param.getKeyword();
		if (Validator.isNotEmpty(keyword)) {
			model.addAttribute("keyword", keyword);
		}

		return "list";
	}


	/**
	 * 首页
	 *
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {

		return "index";
	}


	/**
	 * 拼接url参数
	 * @param param
	 * @param delValueIds   设置为 no 则是将当前的参数列表算出来，urlParam的参数列表就是 PmsSearchParam 中提交的参数
	 *                      正常传id值的话就是拼接面包屑
	 * @return
	 */
	private String getUrlParamForCrumbs(PmsSearchParam param, String delValueIds) {
		StringBuilder urlParam = new StringBuilder();

		String keyword = param.getKeyword();
		String catalog3Id = param.getCatalog3Id();
		String[] valueIds = param.getValueId();

		if (Validator.isNotEmpty(keyword)) {
			if (Validator.isNotEmpty(urlParam)) {
				urlParam.append("&");
			}
			urlParam.append("keyword=").append(keyword);
		}
		if (Validator.isNotEmpty(catalog3Id)) {
			if (Validator.isNotEmpty(urlParam)) {
				urlParam.append("&");
			}
			urlParam.append("catalog3Id=").append(catalog3Id);

		}
		if (Validator.isNotEmpty(valueIds)) {
			for (String valueId : valueIds) {
				//如果是面包屑功能传入点击的属性id，这里就不拼接了
				if(delValueIds.equals("no")){
					urlParam.append("&valueId=").append(valueId);
				}else if (!valueId.equals(delValueIds)) {
					urlParam.append("&valueId=").append(valueId);
				}
			}
		}

		return urlParam.toString();
	}

}
