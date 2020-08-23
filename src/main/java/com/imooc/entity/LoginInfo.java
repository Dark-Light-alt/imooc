package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登陆信息
 */
@Data
@TableName("login_info")
public class LoginInfo implements Serializable {

    // 登录信息 id
    @TableId("login_info_id")
    private String loginInfoId;

    // 用户 id
    @TableField("customer_id")
    private String customerId;

    // 登录时间
    @TableField("login_time")
    private Date loginTime;

    // 登录城市
    @TableField
    private String city;

    // ip
    @TableField
    private String ip;
}
