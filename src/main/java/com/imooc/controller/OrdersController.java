package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.alipay.Alipay;
import com.imooc.alipay.PayDto;
import com.imooc.entity.MyCourse;
import com.imooc.entity.Orders;
import com.imooc.service.impl.MyCourseServiceImpl;
import com.imooc.service.impl.OrdersServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("OrdersController")
public class OrdersController {

    @Resource
    private OrdersServiceImpl ordersServiceImpl;

    @Resource
    private MyCourseServiceImpl myCourseServiceImpl;

    @Resource
    private Alipay alipay;

    /**
     * 生成订单
     *
     * @param orders
     * @return
     */
    @RequestMapping(value = "addCourseOrder", method = RequestMethod.PUT)
    public Result addCourseOrder(@RequestBody Orders orders) {

        Result result = new Result();

        ordersServiceImpl.addCourseOrder(orders);

        result.putData("orderId", orders.getOrderId());

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 查询订单，用于支付
     *
     * @param orderId 订单 id
     * @return
     */
    @RequestMapping(value = "findOrder/{orderId}", method = RequestMethod.GET)
    public Result findOrder(@PathVariable String orderId) {

        Result result = new Result();

        result.putData("order", ordersServiceImpl.findOrder(orderId));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 根据用户 id 查询各种状态的订单
     * <p>
     * customerId  用户 id
     * orderStatus 订单状态  0 未支付 1 已支付 2 已失效 -1 全部
     *
     * @return
     */
    @RequestMapping(value = "findAllOrder", method = RequestMethod.POST)
    public Result findAllOrder(@RequestBody Map<String, Object> params) {

        Result result = new Result();

        String customerId = params.get("customerId").toString();

        Integer orderStatus = Integer.valueOf(params.get("orderStatus").toString());

        result.putData("orderList", ordersServiceImpl.findAllOrder(customerId, orderStatus));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 更改订单状态
     *
     * @param orderNumber 订单编号
     * @param orderStatus 订单状态 0 未支付 1 已支付 2 已失效
     * @return
     */
    @RequestMapping(value = "changeStatus/{orderNumber}/{orderStatus}", method = RequestMethod.GET)
    public Result changeStatus(@PathVariable String orderNumber, @PathVariable Integer orderStatus) {

        Result result = new Result();

        ordersServiceImpl.changeStatus(orderNumber, orderStatus);

        if (orderStatus == 2) {
            result.success(200, "订单已取消");
        } else {
            result.success(200, "SUCCESS");
        }

        return result;
    }

    /**
     * 订单禁用
     *
     * @param orderId 订单 id
     * @return
     */
    @RequestMapping(value = "changeIsenable/{orderId}", method = RequestMethod.GET)
    public Result changeIsenable(@PathVariable String orderId) {

        Result result = new Result();

        ordersServiceImpl.changeIsenable(orderId);

        result.success(200, "删除成功");

        return result;
    }

    /**
     * 调取阿里支付宝沙箱进行验签，验签成功后，生成一个支付表单，前端进行提交，再次验签
     *
     * @param payDto 支付数据传输对象
     * @return
     */
    @RequestMapping(value = "pay", method = RequestMethod.POST)
    public Result pay(@RequestBody PayDto payDto) throws AlipayApiException {

        Result result = new Result();

        result.putData("payform", alipay.pay(payDto));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 同步的成功回调函数，进行订单状态的修改
     *
     * @return
     */
    @RequestMapping(value = "returns", method = RequestMethod.POST)
    public Result returns(@RequestBody Map<String, String> params) {

        Result result = new Result();

        String orderNumber = params.get("orderNumber");

        ordersServiceImpl.changeStatus(orderNumber, 1);

        Orders order = ordersServiceImpl.findByOrderNumber(orderNumber);

        if (order.getOrderType() == 0) {

            MyCourse myCourse = new MyCourse();
            myCourse.setCustomerId(order.getCustomerId());
            myCourse.setCourseId(order.getCommodity());
            myCourseServiceImpl.append(myCourse);
        }

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String, String> params) {

        Result result = new Result();

        Integer status = null;

        if (null != params.get("status") && !"null".equals(params.get("status"))) {
            status = Integer.valueOf(params.get("status"));
        }

        Pages pages = JSONObject.parseObject(params.get("pages"), Pages.class);

        Page<Orders> data = ordersServiceImpl.findAll(pages, status);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.putData("orderList", data.getRecords());
        result.setPages(pages);

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 查询已经支付的订单数量
     *
     * @return
     */
    @RequestMapping(value = "findOrderCount", method = RequestMethod.GET)
    public Result findOrderCount() {

        Result result = new Result();

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getOrderStatus, 1);

        result.putData("count", ordersServiceImpl.count(wrapper));

        result.success(200, "SUCCESS");

        return result;
    }
}
