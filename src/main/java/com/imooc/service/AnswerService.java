package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Answer;

import java.util.List;
import java.util.Map;

/**
 * 问题回答服务
 */
public interface AnswerService extends IService<Answer> {

    /**
     * 添加回答
     *
     * @param answer
     * @return
     */
    boolean append(Answer answer);

    /**
     * 点赞
     *
     * @param answerId 回答 id
     * @return
     */
    int like(String answerId);

    /**
     * 根据 问题 id 查询回答信息
     *
     * @param questionId 问题 id
     * @return
     */
    List<Map<String, Object>> findByQuestion(String questionId);

    /**
     * 启用/禁用
     *
     * @param answerId 回答 id
     * @param iseabel  0 启用 1 禁用
     * @return
     */
    public int changeIsenable(String answerId, Integer iseabel);

    /**
     * 查询用户的回答
     * @param customerId
     * @return
     */
    List<Map<String,Object>> findByCustomer(String customerId);
}
