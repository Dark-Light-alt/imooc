package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 讲师申请表
 */
@Data
@TableName(value = "lecturer_app")
public class LecturerApp implements Serializable {

    @TableId(value = "lecturer_app_id")
    private String lecturerAppId;     //id

    @TableField("customer_id")
    private String customerId;         //外键关联用户表	用户id

    private String name;               //姓名

    private String phone;               //联系方式

    private String about;               //简介

    @TableField("trial_video")
    private String trialVideo;	        //试讲视频

    @TableField("apply_date")
    private Date applyDate	;	        //申请日期

    private String reviewer;	        //外键关联员工表	审核人

    @TableField("review_status")
    private Integer reviewStatus;      //审核状态：0未审核 1已审核

    @TableField("review_result")
    private String reviewResult;		//审核结果

    @TableField("review_date")
    private Date reviewDate;	        //审核日期

}
