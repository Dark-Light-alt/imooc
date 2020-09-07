package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.CommentsDao;
import com.imooc.entity.Comments;
import com.imooc.exception.ApiException;
import com.imooc.service.CommentsService;
import com.imooc.utils.common.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 评论服务实现
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsDao, Comments> implements CommentsService {

    /**
     * 添加评论
     *
     * @param comments
     * @return
     */
    @Override
    public boolean append(Comments comments) {

        if (!CommonUtils.isNotEmpty(comments.getContent())) {
            throw new ApiException(500, "评论内容不能为空");
        }

        return baseMapper.insert(comments) != 0;
    }

    /**
     * 更改状态
     *
     * @param commentId 评论 id
     * @param status    状态 0：正常 1：禁用
     * @return
     */
    @Override
    public boolean changeStatus(String commentId, Integer status) {

        LambdaUpdateWrapper<Comments> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Comments::getCommentStatus, status);
        wrapper.eq(Comments::getCommentId, commentId);

        return baseMapper.update(null, wrapper) != 0;
    }

    /**
     * 点赞
     *
     * @param commentId 评论 id
     * @return
     */
    @Override
    public boolean like(String commentId) {
        return baseMapper.like(commentId) != 0;
    }

    /**
     * 根据课程视频 id 查询评论
     *
     * @param videoId 视频 id
     * @return
     */
    @Override
    public List<Map<String, Object>> findAll(String videoId) {
        return baseMapper.findAll(videoId);
    }
}
