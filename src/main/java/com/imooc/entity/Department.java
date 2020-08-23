package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 部门
 */
@Data
@TableName("department")
public class Department {

    // 部门 id
    @TableId("department_id")
    private String departmentId;

    // 部门名称
    @TableField("departmentName")
    private String departmentName;
}
