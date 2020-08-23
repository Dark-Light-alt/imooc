package com.imooc.entity;


/*
 *
 * 方向表
 * */


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 课程方向
 */
@Data
@TableName("direction")
public class Direction {

    // 课程方向 id
    @TableId("direction_id")
    private String directionId;

    // 方向名称
    @TableField("direction_name")
    private String directionName;

    // 是否禁用：0启用 1禁用
    @TableField("direction_isenable")
    private Integer directionIsenable;
}
