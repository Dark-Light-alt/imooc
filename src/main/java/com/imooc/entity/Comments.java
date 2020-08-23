package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论
 */
@Data
@TableName("comments")
public class Comments implements Serializable {

    // 评论 id
    @TableId("comment_id")
    private String commentId;

    // 评论内容
    private String content;

    // 点赞量
    private Integer likes;

    // 发布人
    private String author;

    // 评论源
    @TableField("comment_be_resource")
    private String commentBeResource;

    // 发布时间
    @TableField("create_time")
    private Date createTime;

    // 状态：0启用 1禁用
    @TableField("comment_status")
    private Integer commentStatus;
}
