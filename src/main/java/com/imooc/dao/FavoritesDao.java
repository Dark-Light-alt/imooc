package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Favorites;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FavoritesDao extends BaseMapper<Favorites> {

    /**
     * 根据用户 id 查询收藏的课程信息
     *
     * @param customerId 用户 id
     * @param isfree     是否付费：0 免费课程 1 实战课程 null 全部
     * @return
     */
    List<Map<String, Object>> findByCustomer(@Param("customerId") String customerId, @Param("isfree") Integer isfree);
}
