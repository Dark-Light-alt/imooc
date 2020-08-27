package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.DirectionDao;
import com.imooc.entity.Direction;
import com.imooc.exception.ApiException;
import com.imooc.service.DirectionService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程方向服务实现
 */
@Service
public class DirectionServiceImpl extends ServiceImpl<DirectionDao, Direction> implements DirectionService {


    @Override
    public boolean append(Direction direction) {

        valid(direction);
        validExist(direction, true);

        return baseMapper.insert(direction) != 0;
    }

    @Override
    public boolean update(Direction direction) {

        valid(direction);
        validExist(direction, false);

        return baseMapper.updateById(direction) != 0;
    }

    @Override
    public boolean changeEnable(String directionId, Integer enable) {

        Direction direction = new Direction();
        direction.setDirectionIsenable(enable);

        LambdaUpdateWrapper<Direction> wrapper = new LambdaUpdateWrapper();
        wrapper.eq(Direction::getDirectionId, directionId);

        return baseMapper.update(direction, wrapper) != 0;
    }

    @Override
    public Direction findById(String directionId) {
        return baseMapper.selectById(directionId);
    }

    @Override
    public Page<Direction> pagingFindAll(Pages pages) {

        Page<Direction> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        return page.setRecords(baseMapper.pagingFindAll(page, lambdaQueryWrapper(pages)));
    }

    /**
     * 线型查询启用的课程方向
     *
     * @return
     */
    @Override
    public List<Direction> findAll() {

        LambdaQueryWrapper<Direction> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Direction::getDirectionIsenable, 0);

        return baseMapper.selectList(wrapper);
    }

    private LambdaQueryWrapper<Direction> lambdaQueryWrapper(Pages pages) {

        LambdaQueryWrapper<Direction> wrapper = new LambdaQueryWrapper<>();

        if (CommonUtils.isNotEmpty(pages.getSearchs().get("directionName"))) {
            wrapper.like(Direction::getDirectionName, pages.getSearchs().get("directionName"));
        }

        return wrapper;
    }

    /**
     * 验证参数是否合法
     *
     * @param direction
     */
    private void valid(Direction direction) {
        if (!CommonUtils.isNotEmpty(direction.getDirectionName())) {
            throw new ApiException(500, "方向名不能为空");
        }
    }

    /**
     * 验证参数是否唯一
     *
     * @param direction
     * @param flag
     */
    private void validExist(Direction direction, Boolean flag) {

        LambdaQueryWrapper<Direction> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Direction::getDirectionName, direction.getDirectionName());

        Direction directionName = baseMapper.selectOne(wrapper);

        if (flag) {
            if (null != directionName) {
                throw new ApiException(500, "方向名不能重复");
            }
        }

        if (null != directionName && !directionName.getDirectionId().equals(direction.getDirectionId())) {
            throw new ApiException(500, "方向名不能重复");
        }
    }
}
