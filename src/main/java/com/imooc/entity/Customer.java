package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户表
 */
@Data
@TableName("customer")
public class Customer implements Serializable {

    @TableId("customer_id")
    private String customerId;//主键	用户id

    @TableField("customer_nickname")
    private String customerNickname;//用户昵称

    @TableField("customer_photo")
    private String customerPhoto;//用户头像

    @TableField("customer_email")
    private String customerEmail;//用户邮箱，当用户绑定邮箱时，判断是否被使用了

    @TableField("customer_phone")
    private String customerPhone;//用户手机号

    @TableField("customer_password")
    private String customerPassword;//用户密码

    @TableField("position_id")
    private String positionId;//外键关联用户职位表	用户职位

    @TableField("customer_address")
    private String customerAddress	;//用户所在城市：河南,郑州,二七

    @TableField("custome_sex")
    private Integer customerSex	;//性别：0保密 1男 2女

    @TableField("personal_about")
    private String personalAbout;//个人简介

    @TableField("create_time")
    private Date createTime;//注册日期


}
