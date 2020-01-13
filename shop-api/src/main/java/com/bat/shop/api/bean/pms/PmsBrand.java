package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PmsBrand  extends Model<PmsBrand> {

    private String id;
    private String        name;
    private String firstLetter;
    private int         sort;
    private int factoryStatus;
    private int         showStatus;
    private int productCount;
    private String         productCommentCount;
    private String logo;
    private String         bigPic;
    private String brandStory;


}
