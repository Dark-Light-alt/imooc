package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;

/**
 * 购物车表
 */
@Data
@TableName("shopping_cart")
public class ShoppingCart implements Serializable {

    @TableId("shopping_cart_id")
    private String shoppingCartId	;//主键	购物车id

    @TableField("customer_id")
    private String customerId;//外键关联用户表	用户id

    @TableField("course_id")
    private String courseId;//默认关联课程表	课程id

    @TableField("create_time")
    private Date createTime;//创建时间

}
