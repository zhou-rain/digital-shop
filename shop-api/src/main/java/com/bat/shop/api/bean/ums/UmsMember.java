package com.bat.shop.api.bean.ums;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class UmsMember extends Model<UmsMember> {

	private Integer id;
	private String memberLevelId;            //用户等级
	private String username;                 //登录名
	private String password;                 //登录密码
	private String nickname;                 //昵称
	private String phone;                    //手机
	private String email;                    //邮箱
	private Integer status;                  //帐号启用状态:0->禁用；1->启用
	private Date createTime;                 //创建时间
	private String icon;                     //头像
	private Integer gender;                  //性别
	private Date birthday;                   //生日
	private String city;                     //所在城市
	private String job;                      //职业
	private String personalizedSignature;    //个性签名
	private Integer integration;             //积分
	private Integer growth;                  //成长值
	private Integer luckeyCount;             //剩余抽奖次数
	private Integer historyIntegration;      //历史积分数量
	private Integer sourceType;              //用户来源
	private String accessToken;              //其它网站给用户颁发的授权码


}
