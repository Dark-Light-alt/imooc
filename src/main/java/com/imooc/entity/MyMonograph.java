package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的专刊表
 */
@Data
@TableName("my_monograph")
public class MyMonograph implements Serializable {

    @TableId("my_monograph_id")
    private String myMonographId;//主键	我的专刊id

    @TableField("customer_id")
    private String customerId;//外键关联用户表	用户id

    @TableField("monograph_id")
    private String monographId	;//外键关联专刊表	专刊id

    @TableField("create_time")
    private Date createTime;//创建时间

}
