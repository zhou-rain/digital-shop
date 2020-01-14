package com.bat.shop.api.mapper;

import com.bat.shop.api.bean.UmsAuth;
import com.bat.shop.api.bean.UmsAuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsAuthMapper {
    long countByExample(UmsAuthExample example);

    int deleteByExample(UmsAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UmsAuth record);

    int insertSelective(UmsAuth record);

    List<UmsAuth> selectByExample(UmsAuthExample example);

    UmsAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UmsAuth record, @Param("example") UmsAuthExample example);

    int updateByExample(@Param("record") UmsAuth record, @Param("example") UmsAuthExample example);

    int updateByPrimaryKeySelective(UmsAuth record);

    int updateByPrimaryKey(UmsAuth record);
}