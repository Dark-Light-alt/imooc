package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Comments;

import java.util.List;
import java.util.Map;

/**
 * 评论服务
 */
public interface CommentsService extends IService<Comments> {

    /**
     * 添加评论
     *
     * @param comments
     * @return
     */
    boolean append(Comments comments);

    /**
     * 更改状态
     *
     * @param commentId 评论 id
     * @param status    状态 0：正常 1：禁用
     * @return
     */
    boolean changeStatus(String commentId, Integer status);

    /**
     * 点赞
     *
     * @param commentId 评论 id
     * @return
     */
    boolean like(String commentId);

    /**
     * 根据课程视频 id 查询评论
     *
     * @param videoId 视频 id
     * @return
     */
    List<Map<String, Object>> findAll(String videoId);
}
