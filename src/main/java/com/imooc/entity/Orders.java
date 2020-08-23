package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 */
@Data
@TableName("orders")
public class Orders implements Serializable {

    // 订单 id
    @TableId("order_id")
    private String orderId;

    // 订单号
    @TableField("order_number")
    private String orderNumber;

    // 订单类型：0实战课程 1专刊
    @TableField("order_type")
    private Integer orderType;

    // 商品 id
    private String commodity;

    // 用户 id
    @TableField("customer_id")
    private String customerId;

    // 订单金额
    @TableField("order_money")
    private BigDecimal orderMoney;

    // 下单时间
    @TableField("order_time")
    private Date orderTime;

    // 订单状态：0未付款 1已付款 2已失效
    @TableField("order_status")
    private Integer orderStatus;

    // 支付时间
    @TableField("payment_time")
    private Date paymentTime;

    // 退款时间
    @TableField("refund_time")
    private Date refundTime;

    // 退款金额
    @TableField("refund_money")
    private BigDecimal refundMoney;

    // 是否禁用：0启用 1禁用
    @TableField("order_isenable")
    private Integer orderIsenable;
}
