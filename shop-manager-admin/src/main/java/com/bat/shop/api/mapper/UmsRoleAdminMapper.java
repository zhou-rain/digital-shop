package com.bat.shop.api.mapper;

import com.bat.shop.api.bean.UmsRoleAdmin;
import com.bat.shop.api.bean.UmsRoleAdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsRoleAdminMapper {
    long countByExample(UmsRoleAdminExample example);

    int deleteByExample(UmsRoleAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UmsRoleAdmin record);

    int insertSelective(UmsRoleAdmin record);

    List<UmsRoleAdmin> selectByExample(UmsRoleAdminExample example);

    UmsRoleAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UmsRoleAdmin record, @Param("example") UmsRoleAdminExample example);

    int updateByExample(@Param("record") UmsRoleAdmin record, @Param("example") UmsRoleAdminExample example);

    int updateByPrimaryKeySelective(UmsRoleAdmin record);

    int updateByPrimaryKey(UmsRoleAdmin record);
}