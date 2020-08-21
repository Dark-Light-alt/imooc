package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 *部门
 */
@Data
@TableName(value = "department ")
public class DeparTment {
    @TableId("department_id ")
    private String  departmentId;
    @TableField("department_name ")
    private String  positionName;
}
