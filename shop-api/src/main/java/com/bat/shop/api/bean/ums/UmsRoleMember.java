package com.bat.shop.api.bean.ums;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UmsRoleMember extends Model<UmsRoleMember> {

    private Integer id;
    private Integer memberId;
    private Integer roleId;

}