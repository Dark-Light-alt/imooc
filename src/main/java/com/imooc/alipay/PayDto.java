package com.imooc.alipay;

import lombok.Data;

/**
 * 支付宝支付信息
 */
@Data
public class PayDto {

    // 原订单号，必填
    private String out_trade_no;

    // 订单名称，必填
    private String subject;

    // 商品描述，可空
    private String body;

    // 付款金额
    private String total_amount;

    // 最晚付款时间
    // 参数限制：1m～15d
    // 描述：m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。该参数数值不接受小数点， 如 1.5h，可转换为 90m
    private String timeout_express = "30m";

    // 销售产品码，与支付宝签约的产品码名称
    private String product_code = "FAST_INSTANT_TRADE_PAY";
}
