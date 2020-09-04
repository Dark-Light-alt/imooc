package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.imooc.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShoppingCartDao extends BaseMapper<ShoppingCart> {

    /**
     * 根据用户 id 查询购物车信息
     *
     * @param wrapper
     * @return
     */
    List<Map<String, Object>> findAllByCustomer(@Param(Constants.WRAPPER) LambdaQueryWrapper<ShoppingCart> wrapper);
}
