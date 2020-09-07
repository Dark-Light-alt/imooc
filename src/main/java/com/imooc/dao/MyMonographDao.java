package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.MyMonograph;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 我的专刊
 */
@Mapper
public interface MyMonographDao extends BaseMapper<MyMonograph> {

    /**
     * 查询用户的专刊
     * @param customerId
     * @return
     */
    List<MyMonograph> findMonographByCustomerId(String customerId);
}
