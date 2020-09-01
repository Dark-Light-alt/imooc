package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChapterDao extends BaseMapper<Chapter> {

    /**
     * 根据数据源 id 查询到对应的章节 id
     * @param resource
     * @return 对应的章节 id
     */
    List<String> findChapterIdByResource(String resource);
}
