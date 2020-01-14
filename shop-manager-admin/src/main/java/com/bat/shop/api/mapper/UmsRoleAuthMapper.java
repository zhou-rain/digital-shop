package com.bat.shop.api.mapper;

import com.bat.shop.api.bean.UmsRoleAuth;
import com.bat.shop.api.bean.UmsRoleAuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsRoleAuthMapper {
    long countByExample(UmsRoleAuthExample example);

    int deleteByExample(UmsRoleAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UmsRoleAuth record);

    int insertSelective(UmsRoleAuth record);

    List<UmsRoleAuth> selectByExample(UmsRoleAuthExample example);

    UmsRoleAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UmsRoleAuth record, @Param("example") UmsRoleAuthExample example);

    int updateByExample(@Param("record") UmsRoleAuth record, @Param("example") UmsRoleAuthExample example);

    int updateByPrimaryKeySelective(UmsRoleAuth record);

    int updateByPrimaryKey(UmsRoleAuth record);
}