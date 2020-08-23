package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * report_reason举报原因表
 */
@Data
@TableName(value = "report_reason")
public class ReportReason implements Serializable {

    @TableId(value = "report_reason_id")
    private String reportReasonId; //举报原因id

    @TableField("report_reason_name")
    private String reportReasonName; //举报原因名：色情、涉政或违法、暴恐

    private String dimension; //检测维度：
}
