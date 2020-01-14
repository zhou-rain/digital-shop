package com.bat.qmall.product.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bat.shop.api.bean.pms.PmsBaseCatalog1;
import com.bat.shop.api.mapper.pms.PmsBaseCatalog1Mapper;
import com.bat.shop.api.service.pms.Catalog1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 15:34
 */
@Service
@Component
public class SpuServiceImpl implements Catalog1Service {

    @Autowired
    PmsBaseCatalog1Mapper catalog1Mapper;


    @Override
    public PmsBaseCatalog1 selectById(String id) {
        return catalog1Mapper.selectById(id);
    }
}
