package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 私信表
 */
@Data
@TableName("private_message")
public class PrivateMessage implements Serializable {

    private String sender;//外键关联用户表	发送者

    private String recipient;//外键关联用户表	接收者

    private String content;	//信息内容

    @TableField("create_time")
    private Date createTime;//创建时间

    private Integer isreading;	//是否已读：0未读 1已读



}
