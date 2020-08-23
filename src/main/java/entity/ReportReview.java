package entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * report_review举报审核表
 */
@Data
@TableName(value = "report_review")
public class ReportReview implements Serializable {

    @TableId(value = "report_review_id")
    private String reportReviewId; //举报审核id

    @TableField("report_reason_id")
    private String reportReasonId; //举报原因

    @TableField("report_describe")
    private String reportDescribe; //举报描述

    @TableField("report_person")
    private String reportPerson; //举报人

    @TableField("person_be_report")
    private String personBeReport; //被举报人

    @TableField("report_type")
    private Integer reportType; //0视频、1评论、2文章、3笔记

    @TableField("resource_be_report")
    private String resourceBeReport; //被举报源：如视频id、评论id、文章id、笔记id

    @TableField("task_id")
    private String taskId;

    @TableField("report_time")
    private Date reportTime;  //举报时间

    @TableField("review_status")
    private Integer reviewStatus; //审核状态：0未审核 1已审核

    @TableField("review_result")
    private String reviewResult; //审核处理结果

    @TableField("review_time")
    private Date reviewTime; //审核时间
}
