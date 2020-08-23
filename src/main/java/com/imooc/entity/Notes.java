package com.imooc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 笔记表
 */
@Data
@TableName("notes")
public class Notes implements Serializable {

    @TableId(value = "notes_id")
    private String notesId; //主键	课程笔记id

    @TableField("notes_url")
    private String notesUrl;//笔记路径

    private String author;//外键关联用户表	作者

    private Integer likes;//点赞量

    @TableField("course_id")
    private String courseId;//外键关联课程表	课程id

    @TableField("video_id")
    private String videoId	;//外键关联课程视频表	课程视频id

    @TableField("create_time")
    private Date createTime;	//发布时间

}
