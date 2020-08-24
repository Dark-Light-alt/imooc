package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.AccountNumberDao;
import com.imooc.entity.AccountNumber;
import com.imooc.service.AccountNumberService;
import org.springframework.stereotype.Service;

@Service
public class AccountNumberServiceImpl extends ServiceImpl<AccountNumberDao, AccountNumber> implements AccountNumberService {

    @Override
    public AccountNumber findByUsername(String username) {

        LambdaQueryWrapper<AccountNumber> wrapper = new LambdaQueryWrapper<>();

        if (null != username && !"".equals(username)) {
            wrapper.eq(AccountNumber::getUsername, username);
        }

        return this.getOne(wrapper);
    }
}
