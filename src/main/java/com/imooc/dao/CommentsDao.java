package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Comments;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentsDao extends BaseMapper<Comments> {

    /**
     * 点赞
     *
     * @param commentId 评论 id
     * @return
     */
    int like(String commentId);

    /**
     * 根据课程视频 id 查询评论
     *
     * @return
     */
    List<Map<String, Object>> findAll(String resourceId);
}
