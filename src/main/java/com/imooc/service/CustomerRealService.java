package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.CustomerReal;

/**
 * 用户实名服务
 */
public interface CustomerRealService extends IService<CustomerReal> {

    /**
     * 根据用户 id 获取到实名信息
     *
     * @param customerId 用户 id
     * @return
     */
    CustomerReal findByCustomer(String customerId);

    /**
     * 认证用户的身份证信息
     *
     * @param customerId 用户id
     * @param idcardUrl  身份证存储路径
     * @return
     */
    int authentication(String customerId, String idcardUrl);
}
