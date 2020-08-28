package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.Rights;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RightsDao extends BaseMapper<Rights> {

    /**
     * 根据权限 id 删除该权限及子孙级
     *
     * @param rightsId
     * @return
     */
    int remove(String rightsId);

    /**
     * 根据职位 id 查询所拥有的三级权限 id
     *
     * @return
     */
    List<String> findHaveThreeRights(String positionId);


    /**
     * 根据职位 id 查询所属权限
     *
     * @param positionId 职位 id
     * @param parentId   父级 id
     * @return
     */
    List<Rights> findRightsByPositionId(String positionId, String parentId);
}
