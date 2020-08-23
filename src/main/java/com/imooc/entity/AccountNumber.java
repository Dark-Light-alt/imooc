package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * 账号
 */
@Data
@TableName("account_number")
public class AccountNumber {

    // 账号 id
    @TableId("account_number_id")
    private String accountNumberId;

    // 用户名
    @TableField
    private String username;

    // 密码
    @TableField
    private String password;

    // 创建时间
    @TableField("create_time")
    private Date createTime;

    // 最后登录时间
    @TableField("end_login_time")
    private Date endLoginTime;

    // 是否锁定：0未锁定 1锁定
    @TableField
    private Integer islocked;

    // 员工 id
    @TableField("employee_id")
    private String employeeId;
}
