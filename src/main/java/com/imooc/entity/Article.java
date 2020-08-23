package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章表
 */
@Data
@TableName("article")
public class Article implements Serializable {

    // 专刊文章 id
    @TableId("article_id")
    private String articleId;

    // 专刊文章名
    @TableField("article_name")
    private String articleName;

    // 专刊文章路径
    @TableField("article_url")
    private String articleUrl;

    // 是否试读 0否 1是
    @TableField("try_reading")
    private Integer tryReading;

    // 章节 id
    @TableField("chapter_id")
    private String chapterId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;
}
