package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实名
 */
@Data
@TableName("customer_real")
public class CustomerReal implements Serializable {

    // 实名id
    @TableId("customer_real_id")
    private String customerRealId;

    // 真实姓名
    @TableField("actual_name")
    private String actualName;

    // 性别
    @TableField
    private Integer sex;

    // 民族
    @TableField
    private String nation;

    // 住址
    @TableField
    private String address;

    // 出生日期
    @TableField("date_of_birth")
    private String dateOfBirth;

    // 身份证号
    @TableField
    private String idcard;

    // 存储路径《身份证图片大小不能超过3M》
    @TableField
    private String url;

    // 用户id
    @TableField("customer_id")
    private String customerId;
}
