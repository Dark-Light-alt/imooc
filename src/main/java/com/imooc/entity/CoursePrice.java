package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 课程价格
 */
@Data
@TableName("course_price")
public class CoursePrice implements Serializable {

    // 课程价格表 id
    @TableId("course_price_id")
    private String coursePriceId;

    // 课程定价
    private BigDecimal price;

    // 课程优惠价格
    private BigDecimal discounts;

    // 优惠截止日期
    @TableField("offer_end_time")
    private Date offerEndTime;

    // 课程 id
    @TableField("course_id")
    private String courseId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;
}
