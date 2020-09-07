package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.MyMonograph;

import java.util.List;

public interface MyMonographService extends IService<MyMonograph>{

    /**
     * 查询用户是否购买过指定专刊
     * @param customerId
     * @param monographId
     * @return
     */
    MyMonograph isBuyMonograph(String customerId,String monographId);


    /**
     * 查询用户的专刊
     * @param customerId
     * @return
     */
    List<MyMonograph> findMonographByCustomerId(String customerId);
}
