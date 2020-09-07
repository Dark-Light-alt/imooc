package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Favorites;

import java.util.List;
import java.util.Map;

/**
 * 我的收藏服务
 */
public interface FavoritesService extends IService<Favorites> {

    /**
     * 添加收藏
     *
     * @param favorites
     * @return
     */
    int append(Favorites favorites);

    /**
     * 根据用户 id 查询收藏的课程信息
     *
     * @param customerId 用户 id
     * @param isfree     是否付费：0 免费课程 1 实战课程 null 全部
     * @return
     */
    List<Map<String, Object>> findByCustomer(String customerId, Integer isfree);
}
