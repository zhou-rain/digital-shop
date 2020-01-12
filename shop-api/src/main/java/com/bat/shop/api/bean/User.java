package com.bat.shop.api.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author: zhouR
 * @date: Create in 2020/1/10 - 19:50
 * @function:
 */
@Data
//@TableName("tb_user")	//如果表名与数据库不满足驼峰，需要加此注解， 可在全局配置文件中配置
public class User extends Model<User> {

	private Long id;
	private String userName;
	@TableField(select = false)		//查询时不返回该字段的值
	private String password;
	private String name;
	private Integer age;
	@TableField(value = "email")	//指定数据表中的字段名
	private String email;

	//乐观锁  先去配置 OptimisticLockerInterceptor
	@Version
	private Integer version;

	@TableLogic  //逻辑删除
	private Integer deleted;  //1可用  0删除

	@TableField(exist = false)	//表示该字段在数据库中不存在
	private String address;

	/*
	 * 如果字段与数据库不匹配，非驼峰，需要加
	 * @TableField(value = "email")
	 * 来指定与数据库的映射
	 *
	 *
	 * 如果有多余的字段，数据库没有的，需要加
	 * @TableField(exist = false)
	 * 代表数据库中不存在，无需映射
	 *
	 * 如果不想查出某个字段，比如查询时，不希望查出password，需要加
	 * @TableField(select = false)
	 *
	 */



}