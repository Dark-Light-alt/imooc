package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Direction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DirectionDao extends BaseMapper<Direction> {

    /**
     * 分页查询 tree 型结构
     * @param page
     * @param wrapper
     * @return
     */
    List<Direction> pagingFindAll(Page<Direction> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Direction> wrapper);
}
