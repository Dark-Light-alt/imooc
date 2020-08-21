package com.imooc.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 *账号表
 */
@Data
@TableName(value = "account_number ")
public class AccountNumber {

   @TableId("account_number_id ")
   private String accountNumberId;
   @TableField("username ")
   private String  userName;
   @TableField("password ")
   private String  passWord;
   @TableField("create_time ")
   private Date createTime;
   @TableField("end_login_time ")
   private Date endLoginTime;
   @TableField("islocked ")
   private Integer  islocked;
   @TableField("employee_id ")
   private String employEeid;
}
