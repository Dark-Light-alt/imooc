package com.imooc.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.AccountNumber;
import com.imooc.entity.EmployeeInfo;
import com.imooc.service.impl.AccountNumberServiceImpl;
import com.imooc.service.impl.EmployeeInfoServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("EmployeeInfoController")
public class EmployeeInfoController {

    @Resource
    private EmployeeInfoServiceImpl employeeInfoServiceImpl;

    @Resource
    private AccountNumberServiceImpl accountNumberServiceImpl;

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

    @RequestMapping(value = "allocationAccountNumber", method = RequestMethod.PUT)
    public Result allocationAccountNumber(@RequestBody Map<String, String> params) {

        Result result = new Result();

        // 获取到 employeeId
        String employeeId = params.get("employeeId");

        AccountNumber accountNumber = JSON.parseObject(params.get("accountNumber"), AccountNumber.class);

        accountNumberServiceImpl.append(accountNumber);

        employeeInfoServiceImpl.allocationAccountNumber(employeeId, accountNumber.getAccountNumberId());

        result.success(200, "账号分配成功");

        return result;
    }

    /**
     * fxy 根据id 查询账号表密码
     * @param employeeId
     * @return
     */
    @RequestMapping(value = "findEmployeeId/{employeeId}", method = RequestMethod.GET)
    public Result findEmployeeId(@PathVariable("employeeId") String employeeId) {

        Result result = new Result();

        result.putData("accountNumber", employeeInfoServiceImpl.findEmployeeId(employeeId));

        System.out.println(employeeInfoServiceImpl.findEmployeeId(employeeId));

        result.success(200, "SUCCESS");

        return result;
    }


}
