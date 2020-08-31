package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Carousel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarouselDao extends BaseMapper<Carousel> {

    /**
     * 分页查询所有
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<Carousel> findAll(Page<Carousel> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Carousel> wrapper);
}
