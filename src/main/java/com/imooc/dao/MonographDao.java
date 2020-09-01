package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Monograph;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MonographDao extends BaseMapper<Monograph> {

    /**
     * 分页查询章节和文章
     * @param page
     * @param wrapper
     * @return
     */
    List<Monograph> pageFindMonograph(Page<Monograph> page, @Param(Constants.WRAPPER) QueryWrapper<Monograph> wrapper);
}
