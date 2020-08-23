package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 回答
 */
@Data
@TableName("answer")
public class Answer implements Serializable {

    // 回答 id
    @TableId(value = "answer_id")
    private String answerId;

    // 内容
    @TableField
    private String content;

    // 发布人
    @TableField
    private String publisher;

    // 问题id
    @TableField("question_id")
    private String questionId;

    // 回答时间
    @TableField("create_time")
    private Date createTime;

    // 点赞量
    @TableField
    private Integer likes;

    // 是否禁用：0启用 1禁用
    @TableField("answer_isenable")
    private Integer answerIsenable;

    // 是否是最佳回答：0否 1是
    @TableField
    private Integer isbest;
}
