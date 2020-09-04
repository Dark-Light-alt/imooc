package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Customer;

/**
 * 用户服务
 */
public interface CustomerSerivce extends IService<Customer> {


    /**
     * 添加用户
     *
     * @param customer
     * @return
     */
    boolean append(Customer customer);

    /**
     * 查询手机号数量
     *
     * @param phone 手机号
     * @return
     */
    Integer findPhoneCount(String phone);

    Customer findById(String customerId);

    /**
     * 根据手机号或邮箱进行登录
     *
     * @param username 邮箱或者手机号
     * @param password 密码
     * @return
     */
    Customer login(String username, String password);

    /**
     * 根据id查询用户信息
     * @param customerId
     * @return
     */
    Customer findByCustomerId(String customerId);

    /**
     * 修改
     * @param customer
     * @return
     */
    boolean update(Customer customer);

}
