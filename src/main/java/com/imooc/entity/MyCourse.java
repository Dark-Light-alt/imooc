package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的课程表
 */
@Data
@TableName("my_course")
public class MyCourse implements Serializable {

    @TableId("my_course_id")
    private String myCourseId;//主键	我的课程id

    @TableField("course_id")
    private String courseId;//外键连接课程表	课程id

    @TableField("create_time")
    private Date createTime;//创建时间

}
