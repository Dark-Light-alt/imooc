package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Chapter;
import com.imooc.entity.Monograph;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChapterDao extends BaseMapper<Chapter> {

    /**
     * 根据数据源 id 查询到对应的章节 id
     * @param resource
     * @return 对应的章节 id
     */
    List<String> findChapterIdByResource(String resource);

    /**
     * 根据专刊分页查询文章和章节
     * @return
     */
    List<Chapter> findACByMid(String monographId);
}
