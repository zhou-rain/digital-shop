package com.bat.shop.api.bean.ums;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UmsRole extends Model<UmsRole> {

    private Integer id;
    private String name;
    private String decs;
    private Integer status;

    @TableField(exist = false)
	Set<UmsAuth> auths = new HashSet<>();

}