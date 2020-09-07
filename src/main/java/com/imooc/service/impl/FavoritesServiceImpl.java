package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.FavoritesDao;
import com.imooc.entity.Favorites;
import com.imooc.exception.ApiException;
import com.imooc.service.FavoritesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 我的收藏服务实现
 */
@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesDao, Favorites> implements FavoritesService {

    @Override
    public int append(Favorites favorites) {

        if (isExist(favorites.getCustomerId(), favorites.getCourseId())) {
            throw new ApiException(500, "课程已被收藏，不能重复收藏哦！");
        }

        return baseMapper.insert(favorites);
    }

    /**
     * 根据用户 id 查询收藏的课程信息
     *
     * @param customerId 用户 id
     * @param isfree     是否付费：0 免费课程 1 实战课程 null 全部
     * @return
     */
    @Override
    public List<Map<String, Object>> findByCustomer(String customerId, Integer isfree) {
        return baseMapper.findByCustomer(customerId, isfree);
    }

    /**
     * 此用户是否收藏过了
     *
     * @param customerId 用户 id
     * @param courseId   课程 id
     * @return
     */
    private boolean isExist(String customerId, String courseId) {

        LambdaQueryWrapper<Favorites> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Favorites::getCustomerId, customerId);
        wrapper.eq(Favorites::getCourseId, courseId);

        return baseMapper.selectCount(wrapper) != 0;
    }
}
