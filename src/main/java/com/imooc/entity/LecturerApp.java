package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 讲师申请
 */
@Data
@TableName("lecturer_app")
public class LecturerApp implements Serializable {

    // 申请 id
    @TableId("lecturer_app_id")
    private String lecturerAppId;

    // 用户id
    @TableField("customer_id")
    private String customerId;

    // 姓名
    @TableField
    private String name;

    // 联系方式
    @TableField
    private String phone;

    // 简介
    @TableField
    private String about;

    // 试讲视频
    @TableField("trial_video")
    private String trialVideo;

    // 申请日期
    @TableField("apply_date")
    private Date applyDate;

    // 审核人
    @TableField
    private String reviewer;

    // 审核状态：0未审核 1已审核
    @TableField("review_status")
    private Integer reviewStatus;

    // 审核结果
    @TableField("review_result")
    private String reviewResult;

    // 审核日期
    @TableField("review_date")
    private Date reviewDate;
}
