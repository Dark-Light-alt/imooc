package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Department;
import com.imooc.service.impl.DepartmentServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("DepartmentController")
public class DepartmentController {

    @Resource
    DepartmentServiceImpl departmentServiceImpl;

    /*@RequestMapping("selectAll")
    @ResponseBody
    public List<Department> selectAll() {
        return departmentServiceImpl.selectAll();
    }
    */
    @RequestMapping("findAll")
    @ResponseBody
    public Result findAll(@RequestBody Pages pages){
        Result result = new Result();

        Page<Department> data = departmentServiceImpl.findAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());
        result.setPages(pages);
        result.putData("department",data.getRecords());
        return result;
    }


    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(@RequestBody Department department) {

        Result result = new Result();

        departmentServiceImpl.insert(department);

        result.success(200, "部门添加成功");

        return result;
    }
}
