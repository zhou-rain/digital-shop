package com.bat.shop.api.bean.ums;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UmsRoleAdmin extends Model<UmsRoleAdmin> {

    private Integer id;
    private Integer adminId;
    private Integer roleId;

}