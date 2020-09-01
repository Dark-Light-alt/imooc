package com.imooc.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.EmployeeInfo;
import com.imooc.utils.common.Pages;

import java.util.List;

public interface EmployeeInfoService extends IService<EmployeeInfo> {

    /**
     * 添加员工信息
     *
     * @param employeeInfo
     * @return
     */
    boolean append(EmployeeInfo employeeInfo);

    /**
     * 办理离职
     *
     * @param employeeId
     * @return
     */
    boolean resignation(String employeeId);

    /**
     * 更改员工信息
     *
     * @param employeeInfo
     * @return
     */
    boolean update(EmployeeInfo employeeInfo);

    /**
     * 分配账号
     *
     * @param employeeId      员工 id
     * @param accountNumberId 账号 id
     * @return
     */
    boolean allocationAccountNumber(String employeeId, String accountNumberId);

    /**
     * 根据员工 id 查询员工信息
     *
     * @param employeeInfoId
     * @return
     */
    EmployeeInfo findById(String employeeInfoId);

    /**
     * 查询员工信息分页
     *
     * @param pages
     * @return
     */
    Page<EmployeeInfo> findAll(Pages pages);

    /**
     * 根据 账号 id 查询员工信息
     *
     * @param accountNumberId
     * @return
     */
    default EmployeeInfo findByAccountNumberId(String accountNumberId) {

        LambdaQueryWrapper<EmployeeInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeeInfo::getAccountNumberId, accountNumberId);
        return getOne(wrapper);
    }

<<<<<<< HEAD
=======

    /**
     * 不分页的查询所有员工   做下拉框
     * @return
     */
    List<EmployeeInfo> findAllEmployeeInfo();
>>>>>>> 8863759132b5954f24d17d0824ae9498791a7367
}
