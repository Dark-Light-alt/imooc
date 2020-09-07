package com.imooc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.EvaluateDao;
import com.imooc.entity.Evaluate;
import com.imooc.exception.ApiException;
import com.imooc.service.EvaluateService;
import com.imooc.utils.common.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 评价服务实现
 */
@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateDao, Evaluate> implements EvaluateService {


    @Override
    public boolean append(Evaluate evaluate) {

        if (!CommonUtils.isNotEmpty(evaluate.getContent())) {
            throw new ApiException(500, "评价内容不能为空");
        }

        if (null == evaluate.getScore()) {
            throw new ApiException(500, "评分不能为空");
        }

        return baseMapper.insert(evaluate) != 0;
    }

    /**
     * 点赞
     *
     * @param evaluateId 评价 id
     * @return
     */
    @Override
    public boolean giveALike(String evaluateId) {
        return baseMapper.giveALike(evaluateId) != 0;
    }

    /**
     * 查询某个评价源的比率分
     *
     * @param resourceId 评价源 id
     * @return
     */
    @Override
    public Double rate(String resourceId) {
        return baseMapper.rate(resourceId);
    }

    @Override
    public List<Map<String, Object>> findAll(String resourceId) {
        return baseMapper.findAll(resourceId);
    }
}
