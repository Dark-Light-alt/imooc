package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Answer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnswerDao extends BaseMapper<Answer> {

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
}
