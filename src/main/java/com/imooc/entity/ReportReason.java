package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 举报原因
 */
@Data
@TableName("report_reason")
public class ReportReason implements Serializable {

    // 举报原因 id
    @TableId("report_reason_id")
    private String reportReasonId;

    // 举报原因名：色情、涉政或违法、暴恐
    @TableField("report_reason_name")
    private String reportReasonName;

    // 检测维度
    @TableField
    private String dimension;
}
