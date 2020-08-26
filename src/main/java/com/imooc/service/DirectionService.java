package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Direction;
import com.imooc.utils.common.Pages;

public interface DirectionService extends IService<Direction> {


    /**
     * 分页查询 tree 型结构
     *
     * @param pages
     * @return
     */
    Page<Direction> pagingFindAll(Pages pages);
}
