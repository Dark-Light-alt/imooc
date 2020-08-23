package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资料
 */
@Data
@TableName("data")
public class Datas implements Serializable {

    // 课程资料 id
    @TableId("data_id")
    private String dataId;

    // 课程资料名
    @TableField("data_name")
    private String dataName;

    // 课程资料路径
    @TableField("data_url")
    private String dataUrl;

    // 课程 id
    @TableField("course_id")
    private String courseId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;
}
