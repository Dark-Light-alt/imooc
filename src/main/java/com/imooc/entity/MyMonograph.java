package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的专刊
 */
@Data
@TableName("my_monograph")
public class MyMonograph implements Serializable {

    // 我的专刊 id
    @TableId("my_monograph_id")
    private String myMonographId;

    // 用户 id
    @TableField("customer_id")
    private String customerId;

    // 专刊 id
    @TableField("monograph_id")
    private String monographId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;
}
