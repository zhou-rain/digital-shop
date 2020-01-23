package com.bat.shop.api.bean.ums;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class UmsMember extends Model<UmsMember> {

    private String id;
    private String memberLevelId;
    private String username;
    private String password;
    private String nickname;
    private String phone;
	private String email;					//邮箱
    private Integer status;						//
    private Date createTime;
    private String icon;
    private Integer gender;
    private Date birthday;
    private String city;
    private String job;
    private String personalizedSignature;
    private Integer sourceType;
    private Integer integration;
    private Integer growth;
    private Integer luckeyCount;
    private Integer historyIntegration;			//历史积分数量

	@Version
	private int deleted;					//逻辑删除 0-删除 1没删
	@TableLogic
	private int version;					//乐观锁，版本号


    }
