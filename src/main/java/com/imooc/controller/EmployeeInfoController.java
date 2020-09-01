package com.imooc.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.AccountNumber;
import com.imooc.entity.EmployeeInfo;
import com.imooc.service.impl.AccountNumberServiceImpl;
import com.imooc.service.impl.EmployeeInfoServiceImpl;
import com.imooc.utils.aliyun.oss.FileStorageService;
import com.imooc.utils.aliyun.oss.FileStorageServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("EmployeeInfoController")
public class EmployeeInfoController{

    @Resource
    private FileStorageServiceImpl fileStorageServiceImpl;

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
        result.success(300,"个人信息修改成功");
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
     * 不分页查询所有员工  做下拉框列表
     *
     */
    @RequestMapping(value = "findAllEmployeeInfo", method = RequestMethod.POST)
    public Result findAllEmployeeInfo() {
        Result result = new Result();

        result.putData("employeeInfoList", employeeInfoServiceImpl.findAllEmployeeInfo());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file)throws IOException {
        Result result = new Result();

        InputStream in = file.getInputStream();

        String fileName = file.getOriginalFilename();

        String url = fileStorageServiceImpl.upload(in, fileName, FileStorageService.IMG);

        result.putData("url", url);

        result.success(200, "SUCCESS");

        return result;
    }
}
