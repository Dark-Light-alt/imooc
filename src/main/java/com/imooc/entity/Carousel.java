package com.imooc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 轮播表
 */
@Data
@TableName("carousel")
public class Carousel implements Serializable {

    @TableId(value = "carousel_id")
    private String carouselId;  //轮播id

    @TableField(value = "course_id")
    private String courseId;    //课程id

    private String cover;       //封面

    private Integer sort;       //排序
}
