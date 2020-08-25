package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Department;
import com.imooc.entity.EmployeeInfo;
import com.imooc.utils.common.Pages;

import java.util.List;

public interface DepartmentService extends IService<Department> {

    /**
     * 查询部门 分页
     */

    //List<Department> selectAll();

    Page<Department> findAll(Pages pages);


    boolean insert(Department department);


}