package com.bat.shop.api.bean.oms;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmsCompanyAddress extends Model<OmsCompanyAddress> {

    private String id;
    private String  addressName;
    private Integer sendStatus;
    private Integer receiveStatus;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String region;
    private String detailAddress;
}
