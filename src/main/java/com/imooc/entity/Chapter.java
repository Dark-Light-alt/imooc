package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 章节表
 */
@Data
@TableName(value = "chapter")
public class Chapter implements Serializable {

    @TableId(value = "chapter_id")
    private String chapterId; //章节ID

    @TableField("chapter_name")
    private String chapterName; //章节名

    @TableField("chapter_about")
    private String chapterAbout; //章节简介

    @TableField("chapter_type")
    private Integer chapterType; //章节类型 0:课程 1:专刊

    @TableField("chapter_resource")
    private String chapterResource; //章节源 课程id 专刊id

    @TableField("create_time")
    private Date createTime; //创建时间
}
