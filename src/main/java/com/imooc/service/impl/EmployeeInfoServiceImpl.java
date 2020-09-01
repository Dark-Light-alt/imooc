package com.imooc.service.impl;

import cn.hutool.core.util.IdcardUtil;
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

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeInfoServiceImpl extends ServiceImpl<EmployeeInfoDao, EmployeeInfo> implements EmployeeInfoService {

    @Resource
    private EmployeeInfoDao employeeInfoDao;
    /**
     * 添加员工信息
     *
     * @param employeeInfo
     * @return
     */
    @Override
    public boolean append(EmployeeInfo employeeInfo) {

        // 验证参数是否合法
        valid(employeeInfo);

        // 验证参数是否唯一
        validExist(employeeInfo, true);

        employeeInfo.setEmployeeSex(employeeInfo.getEmployeeSex() == null ? 0 : employeeInfo.getEmployeeSex());

        return baseMapper.insert(employeeInfo) != 0;
    }

    /**
     * 办理离职
     *
     * @param employeeInfoId 员工 id
     * @return
     */
    @Override
    public boolean resignation(String employeeInfoId) {

        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setEmployeeId(employeeInfoId);
        employeeInfo.setIsleave(1);
        employeeInfo.setLeaveDate(new Date());

        return baseMapper.updateById(employeeInfo) != 0;
    }

    /**
     * 更改员工信息
     *
     * @param employeeInfo
     * @return
     */
    @Override
    public boolean update(EmployeeInfo employeeInfo) {

        // 验证参数是否合法
        valid(employeeInfo);

        // 验证参数是否唯一
        validExist(employeeInfo, false);

        return baseMapper.updateById(employeeInfo) != 0;
    }

    /**
     * 分配账号
     *
     * @param employeeId      员工 id
     * @param accountNumberId 账号 id
     * @return
     */
    @Override
    public boolean allocationAccountNumber(String employeeId, String accountNumberId) {

        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setEmployeeId(employeeId);
        employeeInfo.setAccountNumberId(accountNumberId);

        return baseMapper.updateById(employeeInfo) != 0;
    }

    @Override
    public EmployeeInfo findById(String employeeInfoId) {
        return baseMapper.selectById(employeeInfoId);
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
     * 不分页的查询所有员工   做下拉框
     * @return
     */
    @Override
    public List<EmployeeInfo> findAllEmployeeInfo() {
        return baseMapper.selectList(null);
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
     * 验证参数是否合法
     *
     * @param employeeInfo
     */
    private void valid(EmployeeInfo employeeInfo) {

        if (!CommonUtils.isNotEmpty(employeeInfo.getEmployeeName())) {
            throw new ApiException(500, "姓名不能为空");
        }
        if (!CommonUtils.isNotEmpty(employeeInfo.getEmployeeIdcard())) {
            throw new ApiException(500, "身份证号不能为空");
        }
        if (!CommonUtils.isNotEmpty(employeeInfo.getEmployeePhone())) {
            throw new ApiException(500, "手机号不能为空");
        }
        if (!CommonUtils.isNotEmpty(employeeInfo.getEmployeeEmail())) {
            throw new ApiException(500, "邮箱地址不能为空");
        }
        if (!CommonUtils.isNotEmpty(employeeInfo.getEmployeeAddress())) {
            throw new ApiException(500, "居住地址不能为空");
        }
        if (!CommonUtils.isNotEmpty(employeeInfo.getPhoto())) {
            throw new ApiException(500, "头像不能为空");
        }
        if (!IdcardUtil.isValidCard(employeeInfo.getEmployeeIdcard())) {
            throw new ApiException(500, "身份证号不合法");
        }
        if (!CommonUtils.isCorrectPhone(employeeInfo.getEmployeePhone())) {
            throw new ApiException(500, "手机号不合法");
        }
        if (!CommonUtils.isCorrectEmail(employeeInfo.getEmployeeEmail())) {
            throw new ApiException(500, "邮箱地址不合法");
        }
    }

    /**
     * 验证参数是否唯一s
     *
     * @param employeeInfo
     * @param flag         添加/修改
     */
    private void validExist(EmployeeInfo employeeInfo, Boolean flag) {

        EmployeeInfo idcard = findByParam("employee_idcard", employeeInfo.getEmployeeIdcard());
        EmployeeInfo phone = findByParam("employee_phone", employeeInfo.getEmployeePhone());
        EmployeeInfo emial = findByParam("employee_email", employeeInfo.getEmployeeEmail());

        if (flag) {
            if (null != idcard) {
                throw new ApiException(500, "身份证号已存在");
            }
            if (null != phone) {
                throw new ApiException(500, "手机号已存在");
            }
            if (null != emial) {
                throw new ApiException(500, "邮箱地址已存在");
            }
        }

        if (null != idcard && !idcard.getEmployeeId().equals(employeeInfo.getEmployeeId())) {
            throw new ApiException(500, "身份证号已存在");
        }
        if (null != phone && !phone.getEmployeeId().equals(employeeInfo.getEmployeeId())) {
            throw new ApiException(500, "手机号已存在");
        }
        if (null != emial && !emial.getEmployeeId().equals(employeeInfo.getEmployeeId())) {
            throw new ApiException(500, "邮箱地址已存在");
        }
    }

    /**
     * 根据参数获取 Employee
     * 用于判断是否重复
     *
     * @param column
     * @param value
     * @return
     */
    private EmployeeInfo findByParam(String column, Object value) {

        QueryWrapper<EmployeeInfo> wrapper = new QueryWrapper<>();

        wrapper.eq(column, value);

        return baseMapper.selectOne(wrapper);
    }

    /**
     * 获取某列的 count
     *
     * @param column 列名
     * @param value  值
     * @return
     */
    public int findByColumn(String column, Object value) {

        QueryWrapper<EmployeeInfo> wrapper = new QueryWrapper<>();

        wrapper.eq(column, value);

        return baseMapper.selectCount(wrapper);
    }
}
