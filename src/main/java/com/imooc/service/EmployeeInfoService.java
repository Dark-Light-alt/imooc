package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.EmployeeInfo;
import com.imooc.utils.common.Pages;

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
}
