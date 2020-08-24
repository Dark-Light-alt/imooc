package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.AccountNumber;

public interface AccountNumberService extends IService<AccountNumber> {

    /**
     * 根据 username 查询账号信息
     *
     * @param username 用户名
     * @return
     */
    AccountNumber findByUsername(String username);
}
