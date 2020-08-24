package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.EmployeeInfo;
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

        Page<EmployeeInfo> data = employeeInfoServiceImpl.findAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());
        System.out.println(pages);
        result.setPages(pages);
        result.putData("employeeInfoList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody EmployeeInfo employeeInfo) {

        Result result = new Result();

        employeeInfoServiceImpl.append(employeeInfo);

        result.success(200, "员工添加成功");

        return result;
    }

    @RequestMapping(value = "resignation/{employeeId}", method = RequestMethod.GET)
    public Result resignation(@PathVariable String employeeId) {

        Result result = new Result();

        employeeInfoServiceImpl.resignation(employeeId);

        result.success(200, "离职办理成功");

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody EmployeeInfo employeeInfo) {

        Result result = new Result();

        employeeInfoServiceImpl.update(employeeInfo);

        result.success(200, "员工修改成功");

        return result;
    }

    @RequestMapping(value = "findById/{employeeId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String employeeId) {

        Result result = new Result();

        result.putData("employeeInfo", employeeInfoServiceImpl.findById(employeeId));

        result.success(200, "SUCCESS");

        return result;
    }
}
