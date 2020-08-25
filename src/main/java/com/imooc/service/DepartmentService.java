package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Department;
import com.imooc.utils.common.Pages;

import java.util.List;

public interface DepartmentService extends IService<Department> {


    /**
     * 添加部门信息
     *
     * @param department
     * @return
     */
    boolean append(Department department);

    /**
     * 删除部门信息
     *
     * @param departmentId
     * @return
     */
    boolean remove(String departmentId);

    /**
     * 修改部门信息
     *
     * @param department
     * @return
     */
    boolean update(Department department);

    /**
     * 根据 departmentId 查询部门信息
     *
     * @param departmentId
     * @return
     */
    Department findById(String departmentId);

    /**
     * 分页查询部门信息
     *
     * @param pages
     * @return
     */
    Page<Department> pagingFindAll(Pages pages);

    /**
     * 查询所有部门信息
     *
     * @return
     */
    List<Department> findAll();
}
