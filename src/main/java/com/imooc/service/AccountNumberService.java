package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.AccountNumber;
import com.imooc.utils.common.Pages;

public interface AccountNumberService extends IService<AccountNumber> {

    /**
     * 添加账号信息
     *
     * @param accountNumber
     * @return
     */
    boolean append(AccountNumber accountNumber);

    /**
     * 修改账号信息
     *
     * @param accountNumber
     * @return
     */
    boolean update(AccountNumber accountNumber);

    /**
     * 解锁/锁定 账号
     *
     * @param accountNumberId
     * @param islocked
     * @return
     */
    boolean changeLocked(String accountNumberId, Integer islocked);

    /**
     * 根据 username 查询账号信息
     *
     * @param username 用户名
     * @return
     */
    AccountNumber findByUsername(String username);

    /**
     * 根据 accountNumberId 查询账号信息
     * @param accountNumberId
     * @return
     */
    AccountNumber findById(String accountNumberId);

    /**
     * 分页查询所有账号信息
     * @param pages
     * @return
     */
    Page<AccountNumber> pagingFindAll(Pages pages);
}
