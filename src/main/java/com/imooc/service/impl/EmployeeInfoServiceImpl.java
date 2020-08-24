package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.EmployeeInfoDao;
import com.imooc.entity.EmployeeInfo;
import com.imooc.service.EmployeeInfoService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

@Service
public class EmployeeInfoServiceImpl extends ServiceImpl<EmployeeInfoDao, EmployeeInfo> implements EmployeeInfoService {

    @Override
    public Page<EmployeeInfo> findAll(Pages pages) {

        Page page = new Page(pages.getCurrentPage(), pages.getPageSize());

        return baseMapper.selectPage(page, this.lambdaQueryWrapper(pages));
    }

    /**
     * 查询员工信息 wrapper
     *
     * @param pages
     * @return
     */
    private LambdaQueryWrapper<EmployeeInfo> lambdaQueryWrapper(Pages pages) {

        LambdaQueryWrapper<EmployeeInfo> wrapper = new LambdaQueryWrapper<>();

        //获取属性名                封装模糊查询
        if (CommonUtils.isNotEmpty(pages.getSearchs().get("name"))) {
            wrapper.like(EmployeeInfo::getEmployeeName, pages.getSearchs().get("name"));
        }
        if (CommonUtils.isNotEmpty(pages.getSearchs().get("phone"))) {
            wrapper.like(EmployeeInfo::getEmployeePhone, pages.getSearchs().get("phone"));
        }

        wrapper.orderByDesc(EmployeeInfo::getHiredate);

        return wrapper;
    }
}
