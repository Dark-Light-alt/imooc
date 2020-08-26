package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChapterDao extends BaseMapper<Chapter> {

    /**
     * 根据monographId查询章节
     * @param monographId
     * @return
     */
    List<Chapter> findByMonograph(String monographId);
}
