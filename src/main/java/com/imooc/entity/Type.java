package com.imooc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 课程类型
 */
@Data
@TableName("type")
public class Type {

    // 课程类型 id
    @TableId("type_id")
    private String typeId;

    // 类型名
    @TableField("type_name")
    private String typeName;

    // 是否禁用：0启用 1禁用
    @TableField("type_isenable")
    private Integer typeIsenable;

    // 课程方向 id
    @TableField("direction_id")
    private String directionId;
}
