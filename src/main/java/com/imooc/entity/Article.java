package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * article文章表
 */
@Data
@TableName(value = "article")
public class Article implements Serializable {

    @TableId(value = "article_id")
    private String articleId; //专刊文章id

    @TableField("article_name")
    private String articleName; //专刊文章名

    @TableField("article_url")
    private String articleUrl; //专刊文章路径

    @TableField("try_reading")
    private Integer tryReading; //是否试读 0否 1是

    @TableField("chapter_id")
    private String chapterId; //章节

    @TableField("create_time")
    private Date createTime; //创建时间
}
