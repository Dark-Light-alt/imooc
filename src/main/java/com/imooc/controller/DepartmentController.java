package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Department;
import com.imooc.service.impl.DepartmentServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("DepartmentController")
public class DepartmentController {

    @Resource
    private DepartmentServiceImpl departmentServiceImpl;

    @RequestMapping(value = "pagingFindAll", method = RequestMethod.POST)
    public Result pagingFindAll(@RequestBody Pages pages) {

        Result result = new Result();

        Page<Department> data = departmentServiceImpl.pagingFindAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.setPages(pages);

        result.putData("departmentList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {

        Result result = new Result();

        result.putData("departmentList", departmentServiceImpl.findAll());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Department department) {

        Result result = new Result();

        departmentServiceImpl.append(department);

        result.success(200, "部门信息添加成功");

        return result;
    }

    @RequestMapping(value = "findById/{departmentId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String departmentId) {

        Result result = new Result();

        result.putData("department", departmentServiceImpl.findById(departmentId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Department department) {

        Result result = new Result();

        departmentServiceImpl.update(department);

        result.success(200, "部门信息修改成功");

        return result;
    }

    @RequestMapping(value = "remove/{departmentId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String departmentId) {

        Result result = new Result();

        departmentServiceImpl.remove(departmentId);

        result.success(200, "部门信息删除成功");

        return result;
    }
}
