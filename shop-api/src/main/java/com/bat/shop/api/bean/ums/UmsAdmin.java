package com.bat.shop.api.bean.ums;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author: zhouR
 * @date: Create in 2020/1/14 - 11:42
 * @function: 管理人员表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UmsAdmin extends Model<UmsAdmin> {

	private Integer id;
	private String loginName;
	private String loginPass;
	private String realName;
	private String phone;
	private String email;
	private int sex;
	private Integer age;
	private Date hireDate;
	private Date creatDate;
	private Date updateDate;
	@Version
	private Integer version;
	@TableLogic
	private Integer deleted;


}
