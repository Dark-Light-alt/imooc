package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Direction;
import com.imooc.utils.common.Pages;

import java.util.List;

/**
 * 课程方向服务
 */
public interface DirectionService extends IService<Direction> {

    /**
     * 添加课程方向
     *
     * @param direction
     * @return
     */
    boolean append(Direction direction);

    /**
     * 修改课程方向名称
     *
     * @param direction
     * @return
     */
    boolean update(Direction direction);

    /**
     * 修改启用/禁用
     *
     * @param directionId
     * @param enable
     * @return
     */
    boolean changeEnable(String directionId, Integer enable);

    /**
     * 根据 id 查询
     *
     * @param directionId
     * @return
     */
    Direction findById(String directionId);

    /**
     * 分页查询 tree 型结构
     *
     * @param pages
     * @return
     */
    Page<Direction> pagingFindAll(Pages pages);

    /**
     * 线型查询
     *
     * @return
     */
    List<Direction> findAll();
}
