package com.imooc.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Rights;

import java.util.List;

public interface RightsService extends IService<Rights> {

    /**
     * 添加权限
     *
     * @param rights
     * @return
     */
    boolean append(Rights rights);

    /**
     * 修改权限
     *
     * @param rights
     * @return
     */
    boolean update(Rights rights);

    /**
     * 根据权限 id 查询
     *
     * @param rightsId
     * @return
     */
    Rights findById(String rightsId);

    /**
     * tree 结构查询所有权限
     *
     * @return
     */
    List<Rights> treeFindAll();

    /**
     * 查询权限等级为 1 2 的父级权限
     * 保证不出现 4 级权限
     *
     * @return
     */
    List<Rights> findParent();

    /**
     * 根据职位 id 查询所拥有的三级权限 id
     *
     * @return
     */
    List<String> findHaveThreeRights(String positionId);

    /**
     * 根据 parentId 查询
     *
     * @param parentId
     * @return
     */
    default List<Rights> findByParentId(String parentId) {

        LambdaQueryWrapper<Rights> wrapper = new LambdaQueryWrapper();

        wrapper.eq(Rights::getParentId, parentId);

        List<Rights> list = list(wrapper);

        list.forEach(rights -> {
            rights.setChildrenCount(count(wrapper));
        });

        return list;
    }
}
