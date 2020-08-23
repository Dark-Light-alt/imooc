package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学习进度表
 */
@Data
@TableName("learn_schedule")
public class LearnSchedule implements Serializable {

    @TableId("learn_schedule_id")
    private String learnScheduleId;	//主键	学习进度id

    @TableField("customer_id")
    private String customerId;	//外键关联用户表	用户id

    @TableField("course_id")
    private String courseId;//外键关联课程表	课程id

    @TableField("video_id")
    private String videoId	;//外键关联视频表	课程视频id

    @TableField("schedule_point")
    private Integer schedulePoint;//进度点（秒）


}
