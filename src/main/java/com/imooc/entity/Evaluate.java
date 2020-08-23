package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价
 */
@Data
@TableName("evaluate")
public class Evaluate implements Serializable {

    // 课程评价 id
    @TableId("evaluate_id")
    private String evaluateId;

    // 评价内容
    @TableField
    private String content;

    // 点赞量
    @TableField
    private Integer likes;

    // 发布人
    @TableField
    private String author;

    // 评价类型：0课程 1专刊
    @TableField("evaluate_type")
    private Integer evaluateType;

    // 被评价源：课程、专刊
    @TableField("evaluate_be_resource")
    private String evaluateBeResource;

    // 课程评分 1 - 10
    @TableField
    private Integer score;

    // 发布时间
    @TableField("create_time")
    private Date createTime;
}
