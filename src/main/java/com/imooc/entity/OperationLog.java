package com.imooc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 */
@Data
@TableName("operation_log")
public class OperationLog {

    // 日志 id
    @TableId(value = "operation_log_id")
    private String operationLogId;

    // 操作信息
    @TableField("operation_info")
    private String operationInfo;

    // 操作资源
    @TableField("operation_resource")
    private String operationResource;

    // 员工 id
    @TableField("employee_id")
    private String employeeId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;
}
