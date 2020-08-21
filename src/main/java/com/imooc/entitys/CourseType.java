package com.imooc.entitys;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 *课程类型
 */
@Data
@TableName(value = "course_type ")
public class CourseType {
    @TableId("course_type_id ")
    private String  courseTypeId;
    @TableField("course_type_name ")
    private String  courseTypeName;
    @TableField("type_isenable ")
    private Integer typeIsenable;
    @TableField("course_direction_id ")
    private String  courseDirectionId;
}
