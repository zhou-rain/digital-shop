package com.bat.qmall.product.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bat.shop.api.bean.pms.PmsSkuAttrValue;
import com.bat.shop.api.bean.pms.PmsSkuImage;
import com.bat.shop.api.bean.pms.PmsSkuInfo;
import com.bat.shop.api.bean.pms.PmsSkuSaleAttrValue;
import com.bat.shop.api.mapper.pms.PmsSkuAttrValueMapper;
import com.bat.shop.api.mapper.pms.PmsSkuImageMapper;
import com.bat.shop.api.mapper.pms.PmsSkuInfoMapper;
import com.bat.shop.api.mapper.pms.PmsSkuSaleAttrValueMapper;
import com.bat.shop.api.service.pms.SkuService;
import com.bat.shop.common.Const.RedisConst;
import com.bat.shop.common.exception.ErrException;
import com.bat.shop.common.utils.RedisUtil;
import com.bat.shop.common.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2020/1/16 - 11:16
 * @function: 处理sku的信息  具体的商品单元
 */
@Component
@Service
public class SkuServiceImpl implements SkuService {

	@Autowired
	PmsSkuInfoMapper pmsSkuInfoMapper;
	@Autowired
	PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
	@Autowired
	PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
	@Autowired
	PmsSkuImageMapper pmsSkuImageMapper;
	@Autowired
	RedisUtil redisUtil;

	/**
	 * 保存商品sku信息
	 * @param pmsSkuInfo
	 * @return
	 */
	@Override
	public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) throws ErrException {

		try {
			//保存skuInfo基本信息
			pmsSkuInfoMapper.insert(pmsSkuInfo);
			//主键回填
			String skuId = pmsSkuInfo.getId();

			//保存平台属性关联
			List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
			for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
				pmsSkuAttrValue.setSkuId(skuId);
				pmsSkuAttrValueMapper.insert(pmsSkuAttrValue);
			}

			//保存销售属性关联
			List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
			for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
				pmsSkuSaleAttrValue.setSkuId(skuId);
				pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
			}
			System.out.println("skuSaleAttrValueList = " + skuSaleAttrValueList);


			//保存sku图片信息
			List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
			for (PmsSkuImage pmsSkuImage : skuImageList) {
				pmsSkuImage.setSkuId(skuId);
				pmsSkuImageMapper.insert(pmsSkuImage);
			}
		} catch (Exception e) {
			System.out.println("e ======================= " + e);
			throw new ErrException();
		}

	}

	/**
	 * 根据skuid获取信息
	 * @param skuId
	 * @return
	 */
	@Override
	public PmsSkuInfo getSkuById(String skuId) {

		PmsSkuInfo pmsSkuInfo;

		//连接缓存
		Jedis jedis = redisUtil.getJedis();

		//查询缓存
		String skuKey = RedisConst.getSkuKey(skuId);
		String skuJson = jedis.get(skuKey);

		if(Validator.isNotEmpty(skuJson)){
			//如果缓存中有,直接取出
			pmsSkuInfo = JSON.parseObject(skuJson,PmsSkuInfo.class); //将json字符串转成java对象
		}else {

			//如果缓存中没有，查询数据库，返回数据，并把数据存入缓存
			pmsSkuInfo = getSkuByIdFromDb(skuId);

			if(pmsSkuInfo!=null){
				//如果在数据库中查询的结果不为空
				//将数据存入redis
				jedis.set(skuKey,JSON.toJSONString(pmsSkuInfo));
			}

		}

		//关闭流
		jedis.close();

		return pmsSkuInfo;
	}


	public PmsSkuInfo getSkuByIdFromDb(String skuId) {
		PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectById(skuId);

		//查图片
		PmsSkuImage pmsSkuImage = new PmsSkuImage();
		pmsSkuImage.setSkuId(skuId);
		QueryWrapper<PmsSkuImage> siqw = new QueryWrapper<>(pmsSkuImage);
		List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.selectList(siqw);


		PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
		pmsSkuAttrValue.setSkuId(skuId);
		QueryWrapper<PmsSkuAttrValue> avqw = new QueryWrapper<>(pmsSkuAttrValue);
		List<PmsSkuAttrValue> skuAttrValueList = pmsSkuAttrValueMapper.selectList(avqw);


		PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
		pmsSkuSaleAttrValue.setSkuId(skuId);
		QueryWrapper<PmsSkuSaleAttrValue> savqw =  new QueryWrapper<>(pmsSkuSaleAttrValue);
		List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuSaleAttrValueMapper.selectList(savqw);

		//赋值给商品sku
		pmsSkuInfo.setSkuImageList(pmsSkuImages);
		pmsSkuInfo.setSkuAttrValueList(skuAttrValueList);
		pmsSkuInfo.setSkuSaleAttrValueList(skuSaleAttrValueList);


		return pmsSkuInfo;
	}



	/**
	 * 获取当前sku的所有兄弟属性，并将属性值id进行拼接成hash表
	 * @param productId
	 * @return
	 */
	@Override
	public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {

		return pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
	}


}
