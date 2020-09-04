package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Type;
import com.imooc.utils.common.Pages;

import java.util.List;

/**
 * 课程类别服务
 */
public interface TypeService extends IService<Type> {

    /**
     * 添加课程类别
     *
     * @param type
     * @return
     */
    boolean append(Type type);

    /**
     * 修改课程类别
     *
     * @param type
     * @return
     */
    boolean update(Type type);

    /**
     * 修改启用/禁用状态
     *
     * @param typeId
     * @param enable
     * @return
     */
    boolean changeEnable(String typeId, Integer enable);

    /**
     * 根据 id 查询课程类别
     *
     * @param typeId
     * @return
     */
    Type findById(String typeId);

    /**
     * 分页查询所有课程类别
     *
     * @param pages
     * @return
     */
    Page<Type> pagingFindAll(Pages pages);

    /**
     * 查询所有可用的课程类别
     *
     * @return
     */
    List<Type> findAll();

    /**
     * 根据方向 id 查询出类别
     *
     * @param directionId 方向 id
     * @return
     */
    List<Type> findByDirectionId(String directionId);
}
