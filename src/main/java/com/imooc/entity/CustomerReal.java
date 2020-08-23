package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实名表
 */
@Data
@TableName("customer_real")
public class CustomerReal implements Serializable {

    @TableId("customer_real_id")
    private String customerRealId;    //主键	实名id

    @TableField("actual_name")
    private String actualName;	//真实姓名

    private Integer sex;		//性别

    private String nation;//民族

    private String address;//住址

    @TableField("date_of_birth")
    private String dateOfBirth;	//出生日期

    private String idcard;//身份证号

    private String url;//存储路径《身份证图片大小不能超过3M》

    @TableField("customer_id")
    private String customerId;//外键关联用户表	用户id

}
