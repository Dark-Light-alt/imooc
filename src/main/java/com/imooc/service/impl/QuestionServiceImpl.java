package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.QuestionDao;
import com.imooc.entity.Question;
import com.imooc.exception.ApiException;
import com.imooc.service.QuestionService;
import com.imooc.utils.common.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 课程问题服务实现
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, Question> implements QuestionService {

    /**
     * 添加课程问题
     *
     * @param question
     * @return
     */
    @Override
    public boolean append(Question question) {

        if (!CommonUtils.isNotEmpty(question.getTitle())) {
            throw new ApiException(500, "问题标题不能为空");
        }

        if (!CommonUtils.isNotEmpty(question.getContent())) {
            throw new ApiException(500, "问题内容不能为空");
        }

        return baseMapper.insert(question) != 0;
    }

    /**
     * 启用/禁用
     *
     * @param questionId 问题 id
     * @param isenable   0 启用 1 禁用
     * @return
     */
    @Override
    public int changeIsenable(String questionId, Integer isenable) {

        LambdaUpdateWrapper<Question> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Question::getQuestionIsenable, isenable);
        wrapper.eq(Question::getQuestionId, questionId);

        return baseMapper.update(null, wrapper);
    }

    /**
     * 增加浏览量
     *
     * @param questionId 问题 id
     * @return
     */
    @Override
    public boolean browse(String questionId) {
        return baseMapper.browse(questionId) != 0;
    }

    /**
     * 根据课程 id 查询问题
     *
     * @param courseId 课程 id
     * @return
     */
    @Override
    public List<Map<String, Object>> findByCourse(String courseId) {
        return baseMapper.findByCourse(courseId);
    }

    /**
     * 根据视频 id 查询问题
     *
     * @param videoId
     * @return
     */
    @Override
    public List<Map<String, Object>> findByVideo(String videoId) {
        return baseMapper.findByVideo(videoId);
    }

    /**
     * 根据问题 id 查询信息
     *
     * @param questionId 问题 id
     * @return
     */
    @Override
    public Map<String, Object> findById(String questionId) {
        return baseMapper.findById(questionId);
    }
}
