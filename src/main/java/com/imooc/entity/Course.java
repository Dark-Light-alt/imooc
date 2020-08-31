package com.imooc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @TableField("course_name")
    private String courseName;

    @TableField("course_about")
    private String courseAbout;

    @TableField("author")
    private String author;

    @TableField("number_of_students")
    private Integer numberOfStudents;

    @TableField("type_id")
    private String typeId;

    @TableField("course_level")
    private Integer courseLevel;

    @TableField("isfree")
    private Integer isfree;

    @TableField("price")
    private BigDecimal price;

    @TableField("create_time")
    private Date createTime;

    @TableField("course_status")
    private Integer courseStatus;

    @TableField(exist = false)
    private EmployeeInfo employeeInfo;

    @TableField(exist = false)
    private Customer customer;

    @TableField(exist = false)
    private Direction direction;

    @TableField(exist = false)
    private Type type;

    @TableField(exist = false)
    private List<Chapter> chapterList;

    @TableField(exist = false)
    private List<Datas> datasList;
}
