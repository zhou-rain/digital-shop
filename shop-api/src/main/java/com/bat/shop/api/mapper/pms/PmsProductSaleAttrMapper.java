package com.bat.shop.api.mapper.pms;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bat.shop.api.bean.pms.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/15 - 19:04
 * @function:
 */
public interface PmsProductSaleAttrMapper extends BaseMapper<PmsProductSaleAttr> {

	List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("spuId") String spuId,@Param("skuId") String skuId);

}
