package com.bat.shop.api.bean.ums;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UmsRoleAuth extends Model<UmsRoleAuth> {

	private Integer id;
	private Integer roleId;
	private Integer authId;

}