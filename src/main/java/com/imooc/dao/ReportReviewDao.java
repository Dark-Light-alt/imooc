package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.ReportReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportReviewDao extends BaseMapper<ReportReview> {

    List<ReportReview> findAll(Page<ReportReview> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<ReportReview> wrapper);
}
