package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Customer;

import java.util.List;

public interface CustomerService extends IService<Customer> {


    /**
     * 查询所有用户信息
     *
     * @return
     */
    List<Customer> findAll();
}
