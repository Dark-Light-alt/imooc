package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 专刊
 */
@Data
@TableName("monograph")
public class Monograph implements Serializable {

    // 专刊 id
    @TableId("monograph_id")
    private String monographId;

    // 封面
    @TableField
    private String cover;

    // 专刊名
    @TableField("monograph_name")
    private String monographName;

    // 亮点
    @TableField
    private String highlights;

    // 简介
    @TableField("monograph_about")
    private String monographAbout;

    // 作者
    @TableField
    private String author;

    // 价格
    @TableField
    private BigDecimal price;

    // 优惠
    @TableField
    private BigDecimal discounts;

    // 优惠截止日期
    @TableField("offer_end_time")
    private Date offerEndTime;

    // 购买人数
    @TableField("number_of_purchasers")
    private Integer numberOfPurchasers;

    // 是否下架 0未下架 1已下架
    @TableField("off_shelf")
    private Integer offShelf;

    // 创建时间
    @TableField("create_time")
    private Date createTime;
}
