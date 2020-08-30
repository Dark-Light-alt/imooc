package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 员工
 */
@Data
@TableName("employee_info")
public class EmployeeInfo {

    // 员工 id
    @TableId("employee_id")
    private String employeeId;

    // 员工姓名
    @TableField("employee_name")
    private String employeeName;

    // 性别：0男 1女
    @TableField("employee_sex")
    private Integer employeeSex;

    // 联系方式
    @TableField("employee_phone")
    private String employeePhone;

    // 邮箱地址
    @TableField("employee_email")
    private String employeeEmail;

    // 身份证号
    @TableField("employee_idcard")
    private String employeeIdcard;

    // 现居住地
    @TableField("employee_address")
    private String employeeAddress;

    // 头像
    @TableField
    private String photo;

    // 入职日期
    @TableField
    private Date hiredate;

    // 是否离职：0未离职 1已离职
    @TableField
    private Integer isleave;

    // 离职日期
    @TableField("leave_date")
    private Date leaveDate;

    // 部门 id
    @TableField("department_id")
    private String departmentId;

    // 职位 id
    @TableField("position_id")
    private String positionId;

    //用户账号编号
    @TableField("account_number_id")
    private String accountNumberId;

    @TableField(exist = false)
    private Position position;
}
