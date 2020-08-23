package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的课程
 */
@Data
@TableName("my_course")
public class MyCourse implements Serializable {

    // 我的课程 id
    @TableId("my_course_id")
    private String myCourseId;

    // 课程 id
    @TableField("course_id")
    private String courseId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;
}
