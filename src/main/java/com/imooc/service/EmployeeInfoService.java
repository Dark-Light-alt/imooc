package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.EmployeeInfo;
import com.imooc.utils.common.Pages;

public interface EmployeeInfoService extends IService<EmployeeInfo> {

    /**
     * 查询员工信息分页
     *
     * @param pages
     * @return
     */
    Page<EmployeeInfo> findAll(Pages pages);

}
