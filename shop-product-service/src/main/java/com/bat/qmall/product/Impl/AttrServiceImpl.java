package com.bat.qmall.product.Impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.shop.api.bean.pms.PmsBaseAttrInfo;
import com.bat.shop.api.bean.pms.PmsBaseAttrValue;
import com.bat.shop.api.bean.pms.PmsBaseSaleAttr;
import com.bat.shop.api.mapper.pms.PmsBaseAttrInfoMapper;
import com.bat.shop.api.mapper.pms.PmsBaseAttrValueMapper;
import com.bat.shop.api.mapper.pms.PmsBaseSaleAttrMapper;
import com.bat.shop.api.service.pms.AttrService;
import com.bat.shop.common.exception.EmptyException;
import com.bat.shop.common.exception.ErrException;
import com.bat.shop.common.utils.ArrayUtil;
import com.bat.shop.common.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 22:29
 * @function: 平台属性
 */
@Service
@Component
public class AttrServiceImpl implements AttrService {

	@Autowired
	PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
	@Autowired
	PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
	@Autowired
	PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;


	/**
	 * 获取销售属性
	 * @return
	 */
	@Override
	public List<PmsBaseSaleAttr> getSaleAttrList() {
		return pmsBaseSaleAttrMapper.selectList(null);
	}

	/**
	 * 根据属性值id删除（支持批量，多个id值用英文逗号隔开1,2,3,4,5,）
	 * @param attrId
	 * @return
	 */
	@Override
	public void delBatch(String attrId) throws ErrException, EmptyException {

		List<Integer> idlist = ArrayUtil.StringToIntegerList(attrId);
		if(Validator.isEmpty(idlist))
			throw new EmptyException("id为空，无法删除");

		try {
			pmsBaseAttrValueMapper.deleteBatchIds(idlist);
		} catch (Exception e) {
			throw new ErrException();
		}

	}


	/**
	 * 新增 - 更新
	 *
	 * @param attrInfo
	 */
	@Override
	public String saveAttrInfo(PmsBaseAttrInfo attrInfo) {

		//获取属性的id
		String attrInfoId = attrInfo.getId();

		if(Validator.isEmpty(attrInfoId)){
			//保存
			try {
				//保存属性
				pmsBaseAttrInfoMapper.insert(attrInfo);

				//保存属性值
				List<PmsBaseAttrValue> attrValueList = attrInfo.getAttrValueList();
				for (PmsBaseAttrValue attrValue : attrValueList) {
					attrValue.setAttrId(attrInfo.getId());
					pmsBaseAttrValueMapper.insert(attrValue);
				}
			} catch (Exception e) {
				return "err";
			}

		}else{
			//更新

			try {
				//属性
				pmsBaseAttrInfoMapper.updateById(attrInfo);

				//将属性值全部删掉
				PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
				pmsBaseAttrValue.setAttrId(attrInfo.getId());
				QueryWrapper<PmsBaseAttrValue> wapper = new QueryWrapper<>(pmsBaseAttrValue);
				pmsBaseAttrValueMapper.delete(wapper);

				//重新赋属性值
				List<PmsBaseAttrValue> attrValueList = attrInfo.getAttrValueList();
				for (PmsBaseAttrValue attrValue : attrValueList) {
					attrValue.setAttrId(attrInfo.getId());
					pmsBaseAttrValueMapper.insert(attrValue);
				}

			} catch (Exception e) {
				return "err";
			}
		}
		return "success";
	}

	/**
	 * 根据属性id获取属性值
	 *
	 * @param attrId
	 * @return
	 */
	@Override
	public List<PmsBaseAttrValue> getValueByattrId(String attrId) {
		PmsBaseAttrValue attrValue = new PmsBaseAttrValue();
		attrValue.setAttrId(attrId);
		QueryWrapper<PmsBaseAttrValue> wapper = new QueryWrapper<>(attrValue);
		return attrValue.selectList(wapper);
	}

	/**
	 * 根据三级分类获取属性
	 *
	 * @param catalog3Id
	 * @return
	 */
	@Override
	public List<PmsBaseAttrInfo> getAttrInfoList(String catalog3Id) {
		PmsBaseAttrInfo attrInfo = new PmsBaseAttrInfo();
		attrInfo.setCatalog3Id(catalog3Id);
		QueryWrapper<PmsBaseAttrInfo> wq = new QueryWrapper<>(attrInfo);

		//查出平台属性
		List<PmsBaseAttrInfo> baseAttrInfos = attrInfo.selectList(wq);

		//根据属性id 查出属性值集合，赋值给平台属性
		for (PmsBaseAttrInfo baseAttrInfo : baseAttrInfos) {
			PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
			//设置属性id
			pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());
			QueryWrapper<PmsBaseAttrValue> wrapper = new QueryWrapper<>(pmsBaseAttrValue);
			List<PmsBaseAttrValue> pmsBaseAttrValueList = pmsBaseAttrValueMapper.selectList(wrapper);
			baseAttrInfo.setAttrValueList(pmsBaseAttrValueList);
		}

		return baseAttrInfos;
	}


}
