package com.imooc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 课程方向
 */
@Data
@TableName(value = "course_direction")
public class CourseDirection {

    @TableId("course_direction_id")
    private String courseDirectionId;

    @TableField("course_direction_name")
    private String courseDirectionName;

    @TableField("direction_isenable")
    private String directionIsenable;
}
