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
@EqualsAndHashCode(callSuper = true)
@Data
public class PmsBaseCatalog1 extends Model<PmsBaseCatalog1> {

    private String id;
    private String name;

    @TableField(exist = false)
    private List<PmsBaseCatalog2> catalog2s;

}

