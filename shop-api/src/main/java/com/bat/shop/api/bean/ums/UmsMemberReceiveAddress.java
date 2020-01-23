package com.bat.shop.api.bean.ums;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UmsMemberReceiveAddress extends Model<UmsMemberReceiveAddress> {

	private String id;
	private String memberId;
	private String name;
	private String phoneNumber;
	private Integer defaultStatus;
	private String postCode;
	private String province;
	private String city;
	private String region;
	private String detailAddress;


}
