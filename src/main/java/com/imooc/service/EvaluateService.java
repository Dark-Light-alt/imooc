package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Evaluate;

import java.util.List;
import java.util.Map;

/**
 * 评价服务
 */
public interface EvaluateService extends IService<Evaluate> {

    /**
     * 添加评价
     *
     * @param evaluate
     * @return
     */
    boolean append(Evaluate evaluate);

    /**
     * 点赞
     *
     * @param evaluateId
     * @return
     */
    boolean giveALike(String evaluateId);

    /**
     * 查询某个评价源的比率分
     *
     * @param resourceId
     * @return
     */
    Double rate(String resourceId);

    /**
     * 根据评价源 id 查询出评价
     *
     * @param resourceId 评价源：课程 专栏
     * @return
     */
    List<Map<String, Object>> findAll(String resourceId);
}
