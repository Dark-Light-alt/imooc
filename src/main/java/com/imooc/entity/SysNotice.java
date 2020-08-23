package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统通知表
 */
@Data
@TableName("sys_notice")
public class SysNotice implements Serializable {

    @TableId("sys_notice_id")
    private String sysNoticeId;	//主键	通知id

    private String title;//标题

    private String content;//通知内容

    @TableField("customer_id")
    private String customerId;//外键关联用户表	被通知人

    @TableField("create_time")
    private Date createTime;	//创建时间

    private Integer isreading;	//是否已读：0未读 1已读

}
