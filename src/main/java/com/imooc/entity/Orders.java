package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 */
@Data
@TableName("orders")
public class Orders implements Serializable {

    @TableId("order_id")
    private String orderId;	    //主键	订单id

    @TableField("order_number")
    private String orderNumber;    //订单号

    @TableField("order_type")
    private Integer orderType;     //订单类型：0实战课程 1专刊

    private String commodity;       //商品id

    @TableField("customer_id")
    private String customerId;     //外键关联用户表	用户id

    @TableField("order_money")
    private BigDecimal orderMoney;     //订单金额

    @TableField("order_time")
    private Date orderTime;        //下单时间

    @TableField("order_status")
    private Integer orderStatus;   //订单状态：0未付款 1已付款 2已失效

    @TableField("payment_time")
    private Date paymentTime;      //支付时间

    @TableField("refund_time")
    private Date refundTime;       //退款时间

    @TableField("refund_money")
    private BigDecimal refundMoney;    //退款金额

    @TableField("order_isenable")
    private Integer orderIsenable; //是否禁用：0启用 1禁用

}
