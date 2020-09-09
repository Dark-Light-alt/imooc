package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.AnswerDao;
import com.imooc.entity.Answer;
import com.imooc.exception.ApiException;
import com.imooc.service.AnswerService;
import com.imooc.utils.common.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 回答服务实现
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerDao, Answer> implements AnswerService {

    /**
     * 添加回答
     *
     * @param answer
     * @return
     */
    @Override
    public boolean append(Answer answer) {

        if (!CommonUtils.isNotEmpty(answer.getContent())) {
            throw new ApiException(200, "回答内容不能为空");
        }

        return baseMapper.insert(answer) != 0;
    }

    /**
     * 点赞
     *
     * @param answerId 回答 id
     * @return
     */
    @Override
    public int like(String answerId) {
        return baseMapper.like(answerId);
    }

    /**
     * 根据 问题 id 查询回答信息
     *
     * @param questionId 问题 id
     * @return
     */
    @Override
    public List<Map<String, Object>> findByQuestion(String questionId) {
        return baseMapper.findByQuestion(questionId);
    }

    /**
     * 启用/禁用
     *
     * @param answerId 回答 id
     * @param iseabel  0 启用 1 禁用
     * @return
     */
    @Override
    public int changeIsenable(String answerId, Integer iseabel) {

        LambdaUpdateWrapper<Answer> wrapper = new LambdaUpdateWrapper<>();

        wrapper.set(Answer::getAnswerIsenable, iseabel);
        wrapper.eq(Answer::getAnswerId, answerId);

        return baseMapper.update(null, wrapper);
    }

    /**
     * 查询用户的回答
     * @param customerId
     * @return
     */
    @Override
    public List<Map<String, Object>> findByCustomer(String customerId) {
        return baseMapper.findByCustomer(customerId);
    }
}
