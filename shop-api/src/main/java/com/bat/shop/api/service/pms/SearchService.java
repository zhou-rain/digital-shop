package com.bat.shop.api.service.pms;

import com.bat.shop.api.bean.pms.PmsSkuInfo;

import java.util.List;

/**
 * @author panxs
 * @version 1.0
 * @date 2020/1/14 9:16
 */
public interface SearchService {

    //暴露接口
    List<PmsSkuInfo> selectListByPrice(float price);

}
