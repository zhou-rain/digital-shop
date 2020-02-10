package com.bat.shop.api.service.pms;

import com.bat.shop.api.bean.search.PmsSearchParam;
import com.bat.shop.api.bean.search.PmsSearchSkuInfo;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/15 - 9:22
 * @function:
 */
public interface SearchService {
	/**
	 * 首页搜索功能
	 * @param param
	 * @return
	 */
	List<PmsSearchSkuInfo> list(PmsSearchParam param);
}
