package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.OrdersDao;
import com.imooc.entity.Orders;
import com.imooc.exception.ApiException;
import com.imooc.service.OrdersService;
import com.imooc.utils.OrderNumberBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单服务实现
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

    @Resource
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @Resource
    private MyCourseServiceImpl myCourseServiceImpl;

    @Resource
    private OrderNumberBuilder orderNumberBuilder;

    /**
     * 添加课程订单
     *
     * @param orders
     * @return
     */
    @Transactional
    @Override
    public boolean addCourseOrder(Orders orders) {

        // 判断订单列表中是否存在此课程未支付的订单
        int count = findOrderCount(orders.getCustomerId(), orders.getCommodity());

        if (count != 0) {
            throw new ApiException(500, "此课程已存在未支付订单，请移步订单中心支付或取消未支付订单");
        }

        // 判断用户是否已经购买过此课程
        count = myCourseServiceImpl.findCourseCountByCustomer(orders.getCustomerId(), orders.getCommodity());

        if (count != 0) {
            throw new ApiException(500, "此课程已被购买，请移步我的课程中进行查看");
        }

        // 判断用户的购物车内是否有此课程
        count = shoppingCartServiceImpl.verifyExist(orders.getCustomerId(), orders.getCommodity());

        if (count != 0) {
            // 将购物车内的课程进行删除
            shoppingCartServiceImpl.removeByCustomerAndCourse(orders.getCustomerId(), orders.getCommodity());
        }

        // 设置订单类型：0 课程 1 专刊
        orders.setOrderType(0);

        // 生成订单号
        orders.setOrderNumber(orderNumberBuilder.builder());

        return baseMapper.insert(orders) != 0;
    }

    /**
     * 查询订单用于支付
     *
     * @param orderId
     * @return
     */
    @Override
    public Map<String, Object> findOrder(String orderId) {
        return baseMapper.findOrder(orderId);
    }


    /**
     * 根据用户 id 查询各种状态的订单
     *
     * @param customerId  用户 id
     * @param orderStatus 订单状态 0 未支付 1 已支付 2 已失效 -1 全部
     * @return
     */
    @Override
    public List<Map<String, Object>> findAllOrder(String customerId, Integer orderStatus) {

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Orders::getCustomerId, customerId);

        if (orderStatus != -1) {
            wrapper.eq(Orders::getOrderStatus, orderStatus);
        }

        wrapper.eq(Orders::getOrderIsenable, 0);
        wrapper.orderByAsc(Orders::getOrderStatus);

        return baseMapper.findAllOrder(wrapper);
    }

    /**
     * 根据订单号查询订单信息
     *
     * @param orderNumber 订单号
     * @return
     */
    @Override
    public Orders findByOrderNumber(String orderNumber) {

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Orders::getOrderNumber, orderNumber);

        return baseMapper.selectOne(wrapper);
    }

    /**
     * 更改订单状态
     *
     * @param orderNumber 订单编号
     * @param orderStatus 订单状态 0 未支付 1 已支付 2 已失效
     * @return
     */
    @Override
    public boolean changeStatus(String orderNumber, Integer orderStatus) {

        LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Orders::getOrderStatus, orderStatus);
        wrapper.eq(Orders::getOrderNumber, orderNumber);

        return baseMapper.update(null, wrapper) != 0;
    }

    /**
     * 禁用订单
     *
     * @param orderId
     * @return
     */
    @Override
    public boolean changeIsenable(String orderId) {

        LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Orders::getOrderIsenable, 1);
        wrapper.eq(Orders::getOrderId, orderId);

        return baseMapper.update(null, wrapper) != 0;
    }

    /**
     * 查询商品未支付的条数
     *
     * @param customerId 用户 id
     * @param commodity  商品 id
     * @return
     */
    public int findOrderCount(String customerId, String commodity) {

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Orders::getCustomerId, customerId);
        wrapper.eq(Orders::getCommodity, commodity);
        wrapper.eq(Orders::getOrderStatus, 0);

        return baseMapper.selectCount(wrapper);
    }

    /**
     * 关闭 30 分钟之前的订单
     *
     * @return
     */
    public boolean closeTheOrder() {

        // 获取日历实例
        Calendar calendar = Calendar.getInstance();

        // 设置当前时间
        calendar.setTime(new Date());

        // 设置为 30 分钟之前的时间
        calendar.add(Calendar.MINUTE, -30);

        // 获取 30 分钟之前的时间
        Date time = calendar.getTime();

        LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();

        // 设置为失效
        wrapper.set(Orders::getOrderStatus, 2);
        // 小于或等于 30 分钟之前的时间
        wrapper.le(Orders::getOrderTime, time);
        // 未支付的订单
        wrapper.eq(Orders::getOrderStatus, 0);

        return baseMapper.update(null, wrapper) != 0;
    }
}
