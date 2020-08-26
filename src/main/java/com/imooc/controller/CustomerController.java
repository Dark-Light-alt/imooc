package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Customer;
import com.imooc.service.impl.CustomerServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("CustomerController")
public class CustomerController {

    @Resource
    private CustomerServiceImpl customerServiceImpl;

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {

        Result result = new Result();

        result.putData("customerList", customerServiceImpl.findAll());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "selectPosition", method = RequestMethod.POST)
    public Result selectPosition(@RequestBody Pages pages){
        Result result = new Result();

        Page<Customer> data = customerServiceImpl.selectPosition(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.setPages(pages);

        result.putData("customerList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

}
