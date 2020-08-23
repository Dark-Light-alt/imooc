package com.imooc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 笔记
 */
@Data
@TableName("notes")
public class Notes implements Serializable {

    // 课程笔记 id
    @TableId(value = "notes_id")
    private String notesId;

    // 笔记路径
    @TableField("notes_url")
    private String notesUrl;

    // 作者
    @TableField
    private String author;

    // 点赞量
    @TableField
    private Integer likes;

    // 课程 id
    @TableField("course_id")
    private String courseId;

    // 课程视频 id
    @TableField("video_id")
    private String videoId;

    // 发布时间
    @TableField("create_time")
    private Date createTime;
}
