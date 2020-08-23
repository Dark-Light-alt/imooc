package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复
 */
@Data
@TableName("reply")
public class Reply implements Serializable {

    // 回复 id
    @TableId("reply_id")
    private String replyId;

    // 回答 id
    @TableField("answer_id")
    private String answerId;

    // 回复内容
    @TableField
    private String content;

    // 发送者
    @TableField
    private String sender;

    // 接收者
    @TableField
    private String receiver;

    // 回复时间
    @TableField("create_time")
    private Date createTime;
    
    // 是否禁用：0启用 1禁用
    @TableField("reply_isenable")
    private Integer replyIsenable;
}
