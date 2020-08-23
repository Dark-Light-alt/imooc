package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 私信
 */
@Data
@TableName("private_message")
public class PrivateMessage implements Serializable {

    // 发送者
    @TableField
    private String sender;

    // 接收者
    @TableField
    private String recipient;

    // 信息内容
    @TableField
    private String content;

    // 创建时间
    @TableField("create_time")
    private Date createTime;

    // 是否已读：0未读 1已读
    @TableField
    private Integer isreading;
}
