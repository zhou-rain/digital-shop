package com.bat.shop.api.bean.ums;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UmsAuth extends Model<UmsAuth> {

    private Integer id;
    private Integer pid;
    private String url;
    private String perms;
    private String name;
    private String desc;
    private Integer type;
    private String icon;
    private Integer order;
    private Integer status;

}