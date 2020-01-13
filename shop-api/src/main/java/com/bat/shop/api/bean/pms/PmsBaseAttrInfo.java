package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @param
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsBaseAttrInfo extends Model<PmsBaseAttrInfo> {

    @Id
    private String id;
    private String attrName;
    private String catalog3Id;
    private String isEnabled;

    @TableField(exist = false)
    List<PmsBaseAttrValue> attrValueList;

}
