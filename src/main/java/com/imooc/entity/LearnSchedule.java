package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 学习进度
 */
@Data
@TableName("learn_schedule")
public class LearnSchedule implements Serializable {

    // 学习进度 id
    @TableId("learn_schedule_id")
    private String learnScheduleId;

    //	用户 id
    @TableField("customer_id")
    private String customerId;

    // 课程 id
    @TableField("course_id")
    private String courseId;

    // 课程视频 id
    @TableField("video_id")
    private String videoId;

    // 进度点（秒）
    @TableField("schedule_point")
    private Integer schedulePoint;
}
