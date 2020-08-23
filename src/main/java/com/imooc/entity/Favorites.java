package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏表
 */
@Data
@TableName("favorites")
public class Favorites implements Serializable {

    @TableId("favorites_id")
    private String favoritesId	;//主键	收藏id

    @TableField("customer_id")
    private String customerId;//外键连接用户表	用户id

    @TableField("course_id")
    private String courseId;//外键连接课程表	收藏课程id

    @TableField("create_time")
    private Date createTime; //创建时间


}
