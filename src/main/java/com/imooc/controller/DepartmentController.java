package com.imooc.controller;

import com.imooc.entity.Department;
import com.imooc.service.impl.DepartmentServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("DepartmentController")
public class DepartmentController {

    @Resource
    DepartmentServiceImpl departmentServiceImpl;

    @RequestMapping("selectAll")
    @ResponseBody
    public List<Department> selectAll() {
        return departmentServiceImpl.selectAll();
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public int insert(@RequestBody Department department) {
        return departmentServiceImpl.insert(department);
    }
}
