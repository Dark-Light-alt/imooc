package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Customer;
import com.imooc.utils.common.Pages;

import java.util.List;

public interface CustomerService extends IService<Customer> {


    /**
     * 查询所有用户信息
     *
     * @return
     */
    Page<Customer> pagingFindAll(Pages pages);

}
