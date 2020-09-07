package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChapterDao extends BaseMapper<Chapter> {

    /**
     * 根据数据源 id 查询到对应的章节 id
     *
     * @param resource
     * @return 对应的章节 id
     */
    List<String> findChapterIdByResource(String resource);

    /**
     * 课程学习：根据课程 id 查询对应的所有章节和所有正常状态的视频
     *
     * @param courseId 课程 id
     * @return
     */
    List<Chapter> courseLearn(String courseId);
}
