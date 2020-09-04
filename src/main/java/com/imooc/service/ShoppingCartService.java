package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.ShoppingCart;

import java.util.List;
import java.util.Map;

/**
 * 购物车服务
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * 根据用户查询
     *
     * @param customerId
     * @return
     */
    List<Map<String, Object>> findAllByCustomer(String customerId);
}
