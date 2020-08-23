package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import oracle.sql.DATE;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注表
 */
@Data
@TableName("focus")
public class Focus implements Serializable {

    @TableId("focus_id")
    private String focusId;//主键	关注id

    private String fans	;//外键关联用户表	粉丝

    private String following;//关键关联用户表	关注者

    @TableField("create_time")
    private Date createTime;//创建时间


}
