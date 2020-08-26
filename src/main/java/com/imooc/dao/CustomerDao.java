package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerDao extends BaseMapper<Customer> {
    /**
     * 查询员工职位
     *
     * @return@Param(Constants.WRAPPER)
     */
    List<Customer> selectPosition(Page<Customer> page,  @Param("ew") LambdaQueryWrapper<Customer> wrapper);

}
