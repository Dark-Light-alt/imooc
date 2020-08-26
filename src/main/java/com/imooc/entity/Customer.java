package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
@Data
@TableName("customer")
public class Customer implements Serializable {

    // 用户 id
    @TableId("customer_id")
    private String customerId;

    // 用户昵称
    @TableField("customer_nickname")
    private String customerNickname;

    // 用户头像
    @TableField("customer_photo")
    private String customerPhoto;

    // 用户邮箱
    @TableField("customer_email")
    private String customerEmail;

    // 用户手机号
    @TableField("customer_phone")
    private String customerPhone;

    // 用户密码
    @TableField("customer_password")
    private String customerPassword;

    // 用户职位 id
    @TableField("position_id")
    private String positionId;

    // 用户所在城市：河南,郑州,二七
    @TableField("customer_address")
    private String customerAddress;

    //性别：0保密 1男 2女
    @TableField("custome_sex")
    private Integer customerSex;

    //个人简介
    @TableField("personal_about")
    private String personalAbout;

    //注册日期
    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private Position position;
}
