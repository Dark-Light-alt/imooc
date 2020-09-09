package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 举报审核
 */
@Data
@TableName("report_review")
public class ReportReview implements Serializable {

    // 举报审核 id
    @TableId("report_review_id")
    private String reportReviewId;

    // 举报原因
    @TableField("report_reason_id")
    private String reportReasonId;

    // 举报描述
    @TableField("report_describe")
    private String reportDescribe;

    // 举报人
    @TableField("report_person")
    private String reportPerson;

    // 被举报人
    @TableField("person_be_report")
    private String personBeReport;

    // 举报类型：0视频、1评论、2文章、3笔记
    @TableField("report_type")
    private Integer reportType;

    // 被举报源：如视频id、评论id、文章id、笔记id
    @TableField("resource_be_report")
    private String resourceBeReport;

    // 任务 id
    @TableField("task_id")
    private String taskId;

    // 举报时间
    @TableField("report_time")
    private Date reportTime;

    // 审核状态：0未审核 1已审核
    @TableField("review_status")
    private Integer reviewStatus;

    // 审核处理结果
    @TableField("review_result")
    private String reviewResult;

    // 审核时间
    @TableField("review_time")
    private Date reviewTime;

    @TableField(exist = false)
    private ReportReason reportReason;

    @TableField(exist = false)
    private Video video;

    @TableField(exist = false)
    private Comments comments;

    @TableField(exist = false)
    private Question question;

    @TableField(exist = false)
    private Answer answer;
}
