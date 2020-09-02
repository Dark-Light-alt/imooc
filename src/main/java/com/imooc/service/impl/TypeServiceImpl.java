package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.TypeDao;
import com.imooc.entity.Type;
import com.imooc.exception.ApiException;
import com.imooc.service.TypeService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程类型服务实现
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeDao, Type> implements TypeService {

    @Override
    public boolean append(Type type) {

        valid(type);

        validExist(type, true);

        return baseMapper.insert(type) != 0;
    }

    @Override
    public boolean update(Type type) {

        valid(type);

        validExist(type, false);

        return baseMapper.updateById(type) != 0;
    }

    @Override
    public boolean changeEnable(String typeId, Integer enable) {

        Type type = new Type();
        type.setTypeId(typeId);
        type.setTypeIsenable(enable);

        return baseMapper.updateById(type) != 0;
    }

    @Override
    public Type findById(String typeId) {
        return baseMapper.selectById(typeId);
    }

    @Override
    public Page<Type> pagingFindAll(Pages pages) {

        Page<Type> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        return baseMapper.selectPage(page, lambdaQueryWrapper(pages));
    }

    /**
     * 查询所有启用的课程类别
     *
     * @return
     */
    @Override
    public List<Type> findAll() {

        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Type::getTypeIsenable, 0);

        return baseMapper.selectList(wrapper);
    }

    /**
     * 根据方向 id 查询出类别
     *
     * @param directionId 方向 id
     * @return
     */
    @Override
    public List<Type> findByDirectionId(String directionId) {

        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Type::getDirectionId, directionId);

        return baseMapper.selectList(wrapper);
    }

    /**
     * 条件构造器
     *
     * @param pages
     * @return
     */
    private LambdaQueryWrapper<Type> lambdaQueryWrapper(Pages pages) {

        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper<>();

        if (CommonUtils.isNotEmpty(pages.getSearchs().get("typeName"))) {
            wrapper.like(Type::getTypeName, pages.getSearchs().get("typeName"));
        }

        return wrapper;
    }

    /**
     * 验证参数是否合法
     *
     * @param type
     */
    private void valid(Type type) {
        if (!CommonUtils.isNotEmpty(type.getTypeName())) {
            throw new ApiException(500, "方向名不能为空");
        }

        if (!CommonUtils.isNotEmpty(type.getDirectionId())) {
            throw new ApiException(500, "课程方向不能为空");
        }
    }

    /**
     * 验证参数是否唯一
     *
     * @param type
     * @param flag
     */
    private void validExist(Type type, Boolean flag) {

        LambdaQueryWrapper<Type> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Type::getTypeName, type.getTypeName());

        Type typeName = baseMapper.selectOne(wrapper);

        if (flag) {
            if (null != typeName) {
                throw new ApiException(500, "课程类别不能重复");
            }
        }

        if (null != typeName && !typeName.getTypeId().equals(type.getTypeId())) {
            throw new ApiException(500, "课程类别不能重复");
        }
    }
}
