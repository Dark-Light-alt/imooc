package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionDao extends BaseMapper<Question> {

    /**
     * 浏览量增加
     *
     * @param questionId 问题 id
     * @return
     */
    int browse(String questionId);

    /**
     * 根据课程 id 查询问题
     *
     * @param courseId 课程 id
     * @return
     */
    List<Map<String, Object>> findByCourse(String courseId);

    /**
     * 根据视频 id 查询问题
     *
     * @param videoId
     * @return
     */
    List<Map<String, Object>> findByVideo(String videoId);

    /**
     * 根据问题 id 查询信息
     *
     * @param questionId 问题 id
     * @return
     */
    Map<String, Object> findById(String questionId);

    /**
     * 查找用户提出的问题
     * @param customerId
     * @return
     */
    List<Map<String,Object>> findByCustomer(String customerId);


}
