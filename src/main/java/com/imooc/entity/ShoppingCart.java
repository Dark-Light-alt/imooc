package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车
 */
@Data
@TableName("shopping_cart")
public class ShoppingCart implements Serializable {

    // 购物车 id
    @TableId("shopping_cart_id")
    private String shoppingCartId;

    // 用户 id
    @TableField("customer_id")
    private String customerId;

    // 课程 id
    @TableField("course_id")
    private String courseId;

    // 创建时间
    @TableField("create_time")
    private Date createTime;
}
