package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 *职位
 */
@Data
@TableName(value = "position ")
public class Position {
    @TableId("position_id")
    private String positionId;
    @TableField("position_name")
    private String positionName;
}
