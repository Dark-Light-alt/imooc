package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登陆信息表
 */
@Data
@TableName("login_info")
public class LoginInfo implements Serializable {

    @TableId("login_info_id")
    private String loginInfoId;//主键	登录信息id

    @TableField("customer_id")
    private String customerId;//外键连接用户表	用户id

    @TableField("login_time")
    private Date loginTime;//登录时间

    private String city	;//登录城市

    private String ip;//ip

}
