package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.MyMonographDao;
import com.imooc.entity.MyMonograph;
import com.imooc.service.MyMonographService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyMonographServiceImpl extends ServiceImpl<MyMonographDao, MyMonograph> implements MyMonographService {

    /**
     * 查询用户是否购买过指定专刊
     * @param customerId
     * @param monographId
     * @return
     */
    @Override
    public MyMonograph isBuyMonograph(String customerId, String monographId) {
        LambdaQueryWrapper<MyMonograph> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(MyMonograph::getCustomerId,customerId);
        wrapper.eq(MyMonograph::getMonographId,monographId);

        return baseMapper.selectOne(wrapper);
    }

    /**
     * 查询用户的专刊
     * @param customerId
     * @return
     */
    @Override
    public List<MyMonograph> findMonographByCustomerId(String customerId) {
        return baseMapper.findMonographByCustomerId(customerId);
    }

    /**
     * 添加我的专刊
     * @param myMonograph
     * @return
     */
    @Override
    public int append(MyMonograph myMonograph) {
        return baseMapper.insert(myMonograph);
    }

    /**
     * 根据员工和专刊查询我的专刊
     * @param customerId
     * @param monographId
     * @return
     */
    @Override
    public MyMonograph findByCustomerIdAndMonographId(String customerId, String monographId) {

        LambdaQueryWrapper<MyMonograph> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(MyMonograph::getCustomerId,customerId);
        wrapper.eq(MyMonograph::getMonographId,monographId);

        return baseMapper.selectOne(wrapper);
    }


}
