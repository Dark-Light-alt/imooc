package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.RightsDao;
import com.imooc.entity.Rights;
import com.imooc.exception.ApiException;
import com.imooc.service.RightsService;
import com.imooc.utils.common.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RightsServiceImpl extends ServiceImpl<RightsDao, Rights> implements RightsService {

    @Override
    public boolean append(Rights rights) {

        // 验证
        Rights valid = valid(rights);

        validExist(valid, true);

        return baseMapper.insert(valid) != 0;
    }

    @Override
    public boolean update(Rights rights) {

        // 验证
        Rights valid = valid(rights);

        validExist(valid, false);

        return baseMapper.updateById(valid) != 0;
    }


    @Override
    public Rights findById(String rightsId) {
        return getById(rightsId);
    }

    /**
     * tree 结构查询所有权限
     *
     * @return
     */
    @Override
    public List<Rights> treeFindAll() {

        List<Rights> rightsList = findByParentId("0");

        recursionFindChildren(rightsList);

        return rightsList;
    }

    /**
     * 查询出权限等级为 0 1 的权限
     * 保证不出现 4 级权限
     *
     * @return
     */
    @Override
    public List<Rights> findParent() {

        LambdaQueryWrapper<Rights> wrapper = new LambdaQueryWrapper();

        wrapper.lt(Rights::getRightsLevel, 2);

        return baseMapper.selectList(wrapper);
    }

    /**
     * 根据职位 id 查询所拥有权限 id
     *
     * @return
     */
    @Override
    public List<String> findHaveThreeRights(String positionId) {
        return baseMapper.findHaveThreeRights(positionId);
    }

    /**
     * 根据职位 id 查询出所属权限
     *
     * @param positionId
     * @return
     */
    public List<Rights> findRightsByPositionId(String positionId) {

        List<Rights> rightsList = baseMapper.findRightsByPositionId(positionId, "0");
        rightsList.forEach(rights -> {
            String rightsId = rights.getRightsId();
            List<Rights> chiu = baseMapper.findRightsByPositionId(positionId, rightsId);
            rights.setChildren(chiu);
        });

        return rightsList;
    }

    /**
     * 利用递归查询出父级的所有子级
     *
     * @param rights
     */
    private void recursionFindChildren(List<Rights> rights) {
        for (Rights r : rights) {
            if (r.getChildrenCount() == 0) {
                continue;
            }

            List<Rights> childrens = findByParentId(r.getRightsId());
            r.setChildren(childrens);
            recursionFindChildren(childrens);
        }
    }

    /**
     * 验证
     *
     * @param rights
     */
    private Rights valid(Rights rights) {

        // 判断权限名是否为空
        if (!CommonUtils.isNotEmpty(rights.getRightsName())) {
            throw new ApiException(500, "权限名不能为空");
        }

        // 当父级权限为空时代表是 1 级权限
        if (!CommonUtils.isNotEmpty(rights.getParentId()) || "0".equals(rights.getParentId())) {
            rights.setParentId("0");
            rights.setRightsLevel(0);
            // 当为 1 级权限是，路径必须为空
            if (CommonUtils.isNotEmpty(rights.getRightsPath())) {
                throw new ApiException(500, "一级权限路径不能包含路径");
            }
            // 当为 1 级权限时，图标不能为空
            if (!CommonUtils.isNotEmpty(rights.getIcon())) {
                throw new ApiException(500, "一级权限必须包含图标");
            }
        }
        // 父级权限不为空时代表是 2 3 级权限
        else {

            if (rights.getParentId().equals(rights.getRightsId())) {
                throw new ApiException(500, "父级权限不能为当前权限");
            }

            // 根据父级 id 查询出父级等级
            rights.setRightsLevel(findById(rights.getParentId()).getRightsLevel() + 1);

            if (!CommonUtils.isNotEmpty(rights.getRightsPath())) {
                throw new ApiException(500, "子级权限路径不能为空");
            }

            if (CommonUtils.isNotEmpty(rights.getIcon())) {
                throw new ApiException(500, "子级权限不能包含图标");
            }
        }

        return rights;
    }

    /**
     * 验证参数是否唯一
     *
     * @param rights
     * @param flag   添加/修改
     */
    private void validExist(Rights rights, Boolean flag) {

        Rights rightsPath = findByParam("rights_path", rights.getRightsPath());

        if (flag) {
            if (null != rightsPath) {
                throw new ApiException(500, "权限路径已存在");
            }
        }

        if (null != rightsPath && !rightsPath.getRightsId().equals(rights.getRightsId())) {
            throw new ApiException(500, "权限路径已存在");
        }

    }

    /**
     * 根据参数获取 Rights
     * 用于判断是否重复
     *
     * @param column
     * @param value
     * @return
     */
    private Rights findByParam(String column, Object value) {

        QueryWrapper<Rights> wrapper = new QueryWrapper<>();

        wrapper.eq(column, value);

        return baseMapper.selectOne(wrapper);
    }
}
