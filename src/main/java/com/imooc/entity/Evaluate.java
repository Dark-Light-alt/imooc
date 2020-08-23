package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价表
 */
@Data
@TableName(value = "evaluate")
public class Evaluate implements Serializable {

    @TableId(value = "evaluate_id")
    private String evaluateId; //课程评价id

    private String content; //评价内容

    private Integer likes; //点赞量

    private String author; //发布人

    @TableField("evaluate_type")
    private Integer evaluateType; //评价类型：0课程 1专刊

    @TableField("evaluate_be_resource")
    private String evaluateBeResource; //被评价源：课程、专刊

    private Integer score; //课程评分 1 - 10

    @TableField("create_time")
    private Date createTime; //发布时间
}
