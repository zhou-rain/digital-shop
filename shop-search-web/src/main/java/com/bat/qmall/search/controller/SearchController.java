package com.bat.qmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.service.pms.SearchService;
import com.bat.shop.common.webUtils.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 16:24
 */
@Controller
public class SearchController {

	@Reference(check = false)
	SearchService searchService;

	@RequestMapping("/ok")
	@ResponseBody
	public Msg ok(){
		System.out.println("searchService = " + searchService);

		return Msg.success();
	}


}
