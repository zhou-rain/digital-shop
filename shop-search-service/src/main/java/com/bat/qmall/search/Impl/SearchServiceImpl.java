package com.bat.qmall.search.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.shop.api.bean.pms.PmsSkuInfo;
import com.bat.shop.api.mapper.pms.PmsSearchMapper;
import com.bat.shop.api.service.pms.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/13 16:19
 */
@Service
@Component
public class SearchServiceImpl implements SearchService {

    @Autowired
    PmsSearchMapper pmsSearchMapper;


    @Override
    public List<PmsSkuInfo> selectListByPrice(float price) {

        QueryWrapper<PmsSkuInfo> wrapper = new QueryWrapper<PmsSkuInfo>();
        wrapper.gt("price",price);
        return pmsSearchMapper.selectList(wrapper);
    }
}
