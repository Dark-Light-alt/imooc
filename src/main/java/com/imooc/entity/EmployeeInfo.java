package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("employee_info")
public class EmployeeInfo {

    @TableId("employee_id")
    private String employeeId;

    @TableField("employee_name")
    private String employeeName;

    @TableField("employee_sex")
    private Integer employeeSex;

    @TableField("employee_phone")
    private String employeePhone;

    @TableField("employee_email")
    private String employeeEmail;

    @TableField("employee_idcard")
    private String employeeIdcard;

    @TableField("employee_address")
    private String employeeAddress;

    @TableField("photo")
    private String photo;

    @TableField("hiredate")
    private Date hiredate;

    @TableField("isleave")
    private Integer isleave;

    @TableField("leave_date")
    private Date leaveDate;

    @TableField("department_id")
    private String departmentId;

    @TableField("position_id")
    private String positionId;
}
