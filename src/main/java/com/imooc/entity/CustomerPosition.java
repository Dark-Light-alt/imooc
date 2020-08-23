package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户职位
 */
@Data
@TableName("customer_position")
public class CustomerPosition implements Serializable {

    // 职位 id
    @TableId("position_id")
    private String positionId;

    // 职位名称
    @TableField("position_name")
    private String positionName;
}
