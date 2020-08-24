package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.EmployeeInfo;
import com.imooc.exception.ApiException;
import com.imooc.service.impl.EmployeeInfoServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

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
            result.putData("employeeInfoList", data.getRecords());

            result.success(200, "SUCCESS");

        } catch (ApiException e) {
            result.error(500, e.msg());
        } catch (Exception e) {
            result.error(500, "服务器出小差");
        }

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody EmployeeInfo employeeInfo) {

        Result result = new Result();

        try {
            employeeInfoServiceImpl.append(employeeInfo);

            result.success(200, "员工添加成功");
        } catch (ApiException e) {
            result.error(500, e.msg());
        } catch (Exception e) {
            e.printStackTrace();
            result.error(500, "服务器开小差");
        }

        return result;
    }

    @RequestMapping(value = "resignation/{employeeId}", method = RequestMethod.GET)
    public Result resignation(@PathVariable String employeeId) {

        Result result = new Result();

        try {
            employeeInfoServiceImpl.resignation(employeeId);

            result.success(200, "离职办理成功");
        } catch (ApiException e) {
            result.error(500, e.msg());
        } catch (Exception e) {
            e.printStackTrace();
            result.error(500, "服务器开小差");
        }

        return result;
    }
}
