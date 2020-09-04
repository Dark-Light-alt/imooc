package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.CustomerPositionDao;
import com.imooc.entity.CustomerPosition;
import com.imooc.service.CustomerPositionService;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class CustomerPositionServiceImpl extends ServiceImpl<CustomerPositionDao, CustomerPosition> implements CustomerPositionService {

    /**
     * 查询所有用户职位信息
     * @return
     */
    @Override
    public List<CustomerPosition> findAll() {
        return baseMapper.selectList(new LambdaQueryWrapper<>());
    }
}
