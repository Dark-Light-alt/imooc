package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersDao extends BaseMapper<Orders> {

    /**
     * 查询订单，用于支付
     *
     * @param orderId
     * @return
     */
    Map<String, Object> findOrder(String orderId);

    /**
     * 根据用户 id 查询订单
     *
     * @param wrapper
     * @return
     */
    List<Map<String, Object>> findAllOrder(@Param(Constants.WRAPPER) LambdaQueryWrapper wrapper);

    /**
     * 分页查询所有订单
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<Orders> findAll(Page<Orders> page, @Param(Constants.WRAPPER) LambdaQueryWrapper wrapper);
}
