package com.bat.shop.api.mapper.pms;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bat.shop.api.bean.pms.PmsSkuInfo;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/16 - 11:47
 * @function:
 */
public interface PmsSkuInfoMapper extends BaseMapper<PmsSkuInfo> {

	List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(String productId);

}
