package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程问题
 */
@Data
@TableName("question")
public class Question implements Serializable {

    // 问题 id
    @TableId("question_id")
    private String questionId;

    // 标题
    @TableField
    private String title;

    // 内容
    @TableField
    private String content;

    // 发布人
    @TableField
    private String publisher;

    // 课程 id
    @TableField("course_id")
    private String courseId;

    // 课程视频 id
    @TableField("video_id")
    private String videoId;

    // 浏览量
    @TableField
    private Integer pageviews;

    // 点赞量
    @TableField
    private Integer likes;

    // 发布时间
    @TableField("create_time")
    private Date createTime;

    // 是否禁用：0启用 1禁用
    @TableField("question_isenable")
    private Integer questionIsenable;

    // 是否解决：0未解决 1已解决
    @TableField
    private Integer issolve;
}
