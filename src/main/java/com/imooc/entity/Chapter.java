package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 章节
 */
@Data
@TableName("chapter")
public class Chapter implements Serializable {

    // 章节 id
    @TableId("chapter_id")
    private String chapterId;

    // 章节名
    @TableField("chapter_name")
    private String chapterName;

    // 章节简介
    @TableField("chapter_about")
    private String chapterAbout;

    // 章节类型 0:课程 1:专刊
    @TableField("chapter_type")
    private Integer chapterType;

    // 章节源 课程id 专刊id
    @TableField("chapter_resource")
    private String chapterResource;

    // 创建时间
    @TableField("create_time")
    private Date createTime;

    //课程
    @TableField(exist = false)
    private Course course;

    //专刊
    @TableField(exist = false)
    private Monograph monograph;

    //文章
    @TableField(exist = false)
    private List<Article> articleList;

    //视频列表
    @TableField(exist = false)
    private List<Video> videoList;

}
