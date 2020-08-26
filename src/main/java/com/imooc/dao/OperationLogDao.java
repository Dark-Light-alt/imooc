package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志
 */
@Mapper
public interface OperationLogDao extends BaseMapper<OperationLog> {

    /**
     * 分页查询所有操作信息
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<OperationLog> findAll(Page<OperationLog> page, @Param(Constants.WRAPPER) LambdaQueryWrapper wrapper);
}
