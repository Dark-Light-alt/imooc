package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.EmployeeInfo;
import com.imooc.exception.ApiException;
import com.imooc.service.impl.EmployeeInfoServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("EmployeeInfoController")
public class EmployeeInfoController {

    @Resource
    private EmployeeInfoServiceImpl employeeInfoServiceImpl;

    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody Pages pages) {

        Result result = new Result();

        try {
            Page<EmployeeInfo> data = employeeInfoServiceImpl.findAll(pages);

            pages.setLastPage(data.getPages());
            pages.setTotal(data.getTotal());

            result.setPages(pages);
            //添加数据
            result.putData("employeeInfoList", data.getRecords());

            result.success(200, "SUCCESS");

        } catch (ApiException e) {
            result.error(500, e.msg());
        } catch (Exception e) {
            result.error(500, "服务器出小差");
        }

        return result;
    }
}
