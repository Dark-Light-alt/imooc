package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Position;
import com.imooc.utils.common.Pages;

import java.util.List;

public interface PositionService extends IService<Position> {

    /**
     * 添加职位信息
     *
     * @param position
     * @return
     */
    boolean append(Position position);

    /**
     * 删除职位信息
     *
     * @param positionId
     * @return
     */
    boolean remove(String positionId);

    /**
     * 修改职位信息
     *
     * @param position
     * @return
     */
    boolean update(Position position);

    /**
     * 根据 id 查询职位信息
     *
     * @param positionId
     * @return
     */
    Position findById(String positionId);

    /**
     * 分页查询职位信息
     *
     * @param pages
     * @return
     */
    Page<Position> pagingFindAll(Pages pages);

    /**
     * 查询所有职位信息
     *
     * @return
     */
    List<Position> findAll();
}
