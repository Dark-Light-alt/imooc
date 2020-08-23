package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统通知
 */
@Data
@TableName("sys_notice")
public class SysNotice implements Serializable {

    // 通知 id
    @TableId("sys_notice_id")
    private String sysNoticeId;

    // 标题
    @TableField
    private String title;

    // 通知内容
    @TableField
    private String content;

    // 被通知人
    @TableField("customer_id")
    private String customerId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;

    // 是否已读：0未读 1已读
    @TableField
    private Integer isreading;
}
