package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.AccountNumberDao;
import com.imooc.entity.AccountNumber;
import com.imooc.exception.ApiException;
import com.imooc.service.AccountNumberService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AccountNumberServiceImpl extends ServiceImpl<AccountNumberDao, AccountNumber> implements AccountNumberService {

    @Resource
    private EmployeeInfoServiceImpl employeeInfoServiceImpl;

    @Override
    public boolean append(AccountNumber accountNumber) {

        // 验证参数是否合法
        valid(accountNumber);

        // 验证参数是否唯一
        validExist(accountNumber, true);

        return baseMapper.insert(accountNumber) != 0;
    }

    @Override
    public boolean update(AccountNumber accountNumber) {

        valid(accountNumber);

        return baseMapper.updateById(accountNumber) != 0;
    }

    @Override
    public boolean changeLocked(String accountNumberId, Integer islocked) {

        LambdaUpdateWrapper<AccountNumber> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(AccountNumber::getIslocked, islocked);
        wrapper.eq(AccountNumber::getAccountNumberId, accountNumberId);

        return baseMapper.update(null, wrapper) != 0;
    }

    @Override
    public boolean changeEndLoginTime(String username) {

        LambdaUpdateWrapper<AccountNumber> wrapper = new LambdaUpdateWrapper();
        wrapper.set(AccountNumber::getEndLoginTime, new Date());
        wrapper.eq(AccountNumber::getUsername, username);

        return baseMapper.update(null, wrapper) != 0;
    }

    @Override
    public AccountNumber findByUsername(String username) {

        LambdaQueryWrapper<AccountNumber> wrapper = new LambdaQueryWrapper<>();

        if (CommonUtils.isNotEmpty(username)) {
            wrapper.eq(AccountNumber::getUsername, username);
        }

        return this.getOne(wrapper);
    }

    @Override
    public AccountNumber findById(String accountNumberId) {
        return baseMapper.selectById(accountNumberId);
    }


    @Override
    public Page<AccountNumber> pagingFindAll(Pages pages) {

        Page page = new Page(pages.getCurrentPage(), pages.getPageSize());

        Page p = baseMapper.selectPage(page, this.lambdaQueryWrapper(pages));

        List<AccountNumber> records = p.getRecords();

        records.forEach(record -> {
            record.setEmployeeInfo(employeeInfoServiceImpl.findByAccountNumberId(record.getAccountNumberId()));
        });

        p.setRecords(records);

        return p;
    }

    @Override
    public AccountNumber findByEmployeeId(String employeeId) {

        return baseMapper.selectById(employeeId);
    }

    /**
     * 查询账号信息 wrapper
     *
     * @param pages
     * @return
     */
    private LambdaQueryWrapper<AccountNumber> lambdaQueryWrapper(Pages pages) {

        LambdaQueryWrapper<AccountNumber> wrapper = new LambdaQueryWrapper<>();

        if (CommonUtils.isNotEmpty(pages.getSearchs().get("username"))) {
            wrapper.like(AccountNumber::getUsername, pages.getSearchs().get("username"));
        }

        wrapper.orderByDesc(AccountNumber::getCreateTime);

        return wrapper;
    }

    /**
     * 验证参数是否合法
     *
     * @param accountNumber
     */
    private void valid(AccountNumber accountNumber) {

        if (!CommonUtils.isNotEmpty(accountNumber.getUsername())) {
            throw new ApiException(500, "用户名不能为空");
        }

        if (!CommonUtils.isNotEmpty(accountNumber.getPassword())) {
            throw new ApiException(500, "密码不能为空");
        }
    }

    /**
     * 验证参数是否唯一
     *
     * @param accountNumber
     */
    private void validExist(AccountNumber accountNumber, Boolean flag) {

        AccountNumber username = findByParam("username", accountNumber.getUsername());

        if (flag) {
            if (null != username) {
                throw new ApiException(500, "用户名已存在");
            }
        }

        if (null != username && !username.getAccountNumberId().equals(username.getAccountNumberId())) {
            throw new ApiException(500, "用户名已存在");
        }
    }

    /**
     * 根据参数获取 AccountNumber
     * 用于判断是否重复
     *
     * @param column
     * @param value
     * @return
     */
    private AccountNumber findByParam(String column, Object value) {

        QueryWrapper<AccountNumber> wrapper = new QueryWrapper<>();

        wrapper.eq(column, value);

        return baseMapper.selectOne(wrapper);
    }
}
