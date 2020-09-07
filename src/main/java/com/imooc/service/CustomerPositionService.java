package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.CustomerPosition;

import java.util.List;

public interface CustomerPositionService extends IService<CustomerPosition> {
    /**
     * 根据查询用户职位信息
     * @param
     * @return
     */
    List<CustomerPosition> findAll();
}
