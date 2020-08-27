package com.imooc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 课程
 */
@Data
@TableName("course")
public class Course {

    @TableId("course_id")
    private String courseId;

    @TableField("cover")
    private String cover;

    @TableField("background")
    private String background;

    @TableField("course_name")
    private String courseName;

    @TableField("course_about")
    private String courseAbout;

    @TableField("author")
    private String author;

    @TableField("duration")
    private Integer duration;

    @TableField("number_of_students")
    private Integer numberOfStudents;

    @TableField("type_id")
    private String typeId;

    @TableField("course_level")
    private Integer courseLevel;

    @TableField("isfree")
    private Integer isfree;

    @TableField("create_time")
    private Date createTime;

    @TableField("course_status")
    private Integer courseStatus;
}
