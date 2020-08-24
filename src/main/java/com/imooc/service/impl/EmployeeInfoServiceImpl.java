package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.EmployeeInfoDao;
import com.imooc.entity.EmployeeInfo;
import com.imooc.exception.ApiException;
import com.imooc.service.EmployeeInfoService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmployeeInfoServiceImpl extends ServiceImpl<EmployeeInfoDao, EmployeeInfo> implements EmployeeInfoService {

    /**
     * 添加员工信息
     *
     * @param employeeInfo
     * @return
     */
    public boolean append(EmployeeInfo employeeInfo) {

        if (findCount("employee_phone", employeeInfo.getEmployeePhone()) != 0) {
            throw new ApiException(500, "手机号已存在");
        }

        if (findCount("employee_email", employeeInfo.getEmployeeEmail()) != 0) {
            throw new ApiException(500, "邮箱地址已存在");
        }

        if (findCount("employee_idcard", employeeInfo.getEmployeeIdcard()) != 0) {
            throw new ApiException(500, "身份证号已存在");
        }

        return baseMapper.insert(employeeInfo) != 0;
    }

    /**
     * 办理离职
     *
     * @param employeeInfoId 员工 id
     * @return
     */
    public boolean resignation(String employeeInfoId) {

        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setEmployeeId(employeeInfoId);
        employeeInfo.setIsleave(1);
        employeeInfo.setLeaveDate(new Date());

        return baseMapper.updateById(employeeInfo) != 0;
    }

    /**
     * 查询员工信息
     *
     * @param pages
     * @return
     */
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

    /**
     * 查询 count 值
     *
     * @param column
     * @param value
     * @return
     */
    private int findCount(String column, Object value) {

        QueryWrapper<EmployeeInfo> wrapper = new QueryWrapper<>();

        wrapper.eq(column, value);

        return baseMapper.selectCount(wrapper);
    }
}
