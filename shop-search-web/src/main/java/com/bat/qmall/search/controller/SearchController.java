package com.bat.qmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.pms.PmsSkuInfo;
import com.bat.shop.api.service.pms.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 16:24
 */
@Controller
public class SearchController {


    @Reference(check = false)
    SearchService searchService;

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }


    @RequestMapping("/ok")
    @ResponseBody
    public List<PmsSkuInfo> ok(){
        List<PmsSkuInfo> pmsSkuInfos = searchService.selectListByPrice(12000);


        return pmsSkuInfos;
    }
}
