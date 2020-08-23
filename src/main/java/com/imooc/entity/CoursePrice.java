package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 课程价格表
 */
@Data
@TableName(value = "course_price")
public class CoursePrice implements Serializable {

    @TableId(value = "course_price_id")
    private String coursePriceId; //课程价格表id

    private BigDecimal price; //课程定价

    private BigDecimal discounts; //课程优惠价格

    @TableField("offer_end_time")
    private Date offerEndTime; //优惠截止日期

    @TableField("course_id")
    private String courseId; //课程id

    @TableField("create_time")
    private Date createTime; //创建时间
}
