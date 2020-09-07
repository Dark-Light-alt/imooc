package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Evaluate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EvaluateDao extends BaseMapper<Evaluate> {

    /**
     * 点赞
     *
     * @param evaluateId
     * @return
     */
    int giveALike(String evaluateId);

    /**
     * 查询某个评价源的比率分
     *
     * @param resourceId
     * @return
     */
    Double rate(String resourceId);

    /**
     * 查看评价
     *
     * @param resourceId 评价源 id
     * @return
     */
    List<Map<String, Object>> findAll(String resourceId);
}
