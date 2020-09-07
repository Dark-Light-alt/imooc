package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Question;

import java.util.List;
import java.util.Map;

/**
 * 课程问题服务
 */
public interface QuestionService extends IService<Question> {

    /**
     * 添加课程问题
     *
     * @param question
     * @return
     */
    boolean append(Question question);

    /**
     * 启用/禁用
     *
     * @param questionId 问题 id
     * @param isenable   0 启用 1 禁用
     * @return
     */
    int changeIsenable(String questionId, Integer isenable);

    /**
     * 增加浏览量
     *
     * @param questionId 问题 id
     * @return
     */
    boolean browse(String questionId);

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
}
