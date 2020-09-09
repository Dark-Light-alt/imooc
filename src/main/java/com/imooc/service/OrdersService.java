package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Orders;
import com.imooc.utils.common.Pages;

import java.util.List;
import java.util.Map;

/**
 * 订单服务
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 添加课程订单
     *
     * @param orders
     * @return
     */
    boolean addCourseOrder(Orders orders);

    /**
     * 查询订单，用于支付
     *
     * @param orderId
     * @return
     */
    Map<String, Object> findOrder(String orderId);

    /**
     * 根据用户 id 查询各种状态的订单
     *
     * @param customerId  用户 id
     * @param orderStatus 订单状态
     * @return
     */
    List<Map<String, Object>> findAllOrder(String customerId, Integer orderStatus);

    /**
     * 分页查询所有订单
     *
     * @param pages
     * @param status 订单状态：0 未支付 1 已完成 2 已失效 null 全部
     * @return
     */
    Page<Orders> findAll(Pages pages, Integer status);

    /**
     * 根据订单号查询订单信息
     *
     * @param orderNumber 订单号
     * @return
     */
    Orders findByOrderNumber(String orderNumber);

    /**
     * 禁用订单
     *
     * @param orderId
     * @return
     */
    boolean changeIsenable(String orderId);

    /**
     * 更改订单状态
     *
     * @param orderNumber 订单编号
     * @param orderStatus 订单状态 0 未支付 1 已支付 2 已失效
     * @return
     */
    boolean changeStatus(String orderNumber, Integer orderStatus);
}
