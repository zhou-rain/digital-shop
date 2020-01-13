package com.bat.shop.api.bean.pms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @param
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PmsBaseCatalog2 extends Model<PmsBaseCatalog2> {
    private String id;
    private String name;
    private String catalog1Id;

    @TableField(exist = false)
    private List<PmsBaseCatalog3> catalog3List;

}
