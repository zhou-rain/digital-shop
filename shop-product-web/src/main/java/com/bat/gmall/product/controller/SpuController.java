package com.bat.gmall.product.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bat.shop.api.bean.pms.PmsBaseCatalog1;
import com.bat.shop.api.service.pms.Catalog1Service;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 15:44
 */
@RestController
public class SpuController {


    @Reference(check = false)
    Catalog1Service catalog1Service;

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }


    @RequestMapping("/ok")
    @ResponseBody
    public String ok(){
        System.out.println("catalog1Service = " + catalog1Service);
        PmsBaseCatalog1 catalogo1ById = catalog1Service.selectById("1");
        System.out.println(catalogo1ById);

        return "123123";
    }

}
