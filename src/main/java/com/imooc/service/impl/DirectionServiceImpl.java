package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.DirectionDao;
import com.imooc.entity.Direction;
import com.imooc.service.DirectionService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

@Service
public class DirectionServiceImpl extends ServiceImpl<DirectionDao, Direction> implements DirectionService {


    @Override
    public Page<Direction> pagingFindAll(Pages pages) {

        Page<Direction> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        return page.setRecords(baseMapper.pagingFindAll(page, lambdaQueryWrapper(pages)));
    }

    private LambdaQueryWrapper<Direction> lambdaQueryWrapper(Pages pages) {

        LambdaQueryWrapper<Direction> wrapper = new LambdaQueryWrapper<>();

        if (CommonUtils.isNotEmpty(pages.getSearchs().get("directionName"))) {
            wrapper.like(Direction::getDirectionName, pages.getSearchs().get("directionName"));
        }

        return wrapper;
    }
}
