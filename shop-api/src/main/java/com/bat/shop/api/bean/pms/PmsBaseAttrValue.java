package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;


/**
 * @param
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsBaseAttrValue extends Model<PmsBaseAttrValue> {
    private String id;
    private String valueName;
    private String attrId;
    private String isEnabled;

    @TableField(exist = false)
    private String urlParam;

}
