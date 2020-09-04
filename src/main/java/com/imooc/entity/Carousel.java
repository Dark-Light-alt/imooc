package com.imooc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 轮播
 */
@Data
@TableName("carousel")
public class Carousel implements Serializable {

    // 轮播 id
    @TableId(value = "carousel_id")
    private String carouselId;

    // 课程 id
    @TableField(value = "course_id")
    private String courseId;

    // 封面
    @TableField
    private String cover;

    // 排序
    @TableField
    private Integer sort;

    @TableField(exist = false)
    private Course course;
}
