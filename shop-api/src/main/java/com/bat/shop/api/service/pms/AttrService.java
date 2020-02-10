package com.bat.shop.api.service.pms;

import com.bat.shop.api.bean.pms.PmsBaseAttrInfo;
import com.bat.shop.api.bean.pms.PmsBaseAttrValue;
import com.bat.shop.api.bean.pms.PmsBaseSaleAttr;
import com.bat.qmall.exception.EmptyException;
import com.bat.qmall.exception.ErrException;

import java.util.List;
import java.util.Set;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 22:28
 * @function:  平台属性
 */
public interface AttrService  {
	/**
	 * 获取所有的平台属性
	 * @param catalog3Id
	 * @return
	 */
	List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id);

	/**
	 * 根据平台属性id获取平台属性值
	 * @param attrId
	 * @return
	 */
	List<PmsBaseAttrValue> getValueByattrId(String attrId);

	/**
	 * 保存更新平台属性
	 * @param attrInfo
	 * @return
	 */
	String saveAttrInfo(PmsBaseAttrInfo attrInfo);

	/**
	 * 获取销售属性
	 * @return
	 */
	List<PmsBaseSaleAttr> getSaleAttrList();

	/**
	 * 根据属性值id删除（支持批量，多个id值用英文逗号隔开1,2,3,4,5,）
	 * @param attrId
	 * @return
	 */
	void delBatch(String attrId) throws ErrException, EmptyException;

	/**
	 * 根据平台属性id集合 查询出 平台属性值
	 * @param valueIdSet
	 * @return
	 */
	List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet);
}
