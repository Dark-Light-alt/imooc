package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Carousel;
import com.imooc.utils.common.Pages;

/**
 * 轮播控制服务
 */
public interface CarouselService extends IService<Carousel> {

    /**
     * 分页查询所有
     *
     * @param pages
     * @return
     */
    Page<Carousel> findAll(Pages pages);
}
