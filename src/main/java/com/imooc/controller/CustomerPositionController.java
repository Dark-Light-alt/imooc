package com.imooc.controller;

import com.imooc.service.impl.CustomerPositionServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("CustomerPositionController")
public class CustomerPositionController {

    @Resource
    CustomerPositionServiceImpl customerPositionServiceImpl;
    /**
     * 查询所有用户职位信息
     * @return
     */
    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {

        Result result = new Result();

        result.putData("positionList", customerPositionServiceImpl.findAll());

        result.success(200, "SUCCESS");

        return result;
    }

}
