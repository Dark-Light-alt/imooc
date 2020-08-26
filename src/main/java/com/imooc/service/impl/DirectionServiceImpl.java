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
public class DirectionServiceImpl extends ServiceImpl<DirectionDao,Direction> implements DirectionService {


    @Override
    public Page<Direction> findAll(Pages pages) {
        Page<Direction> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        //条件构造器
        LambdaQueryWrapper<Direction> wrapper = new LambdaQueryWrapper<>();

        //如果课程方向不为空
        if(CommonUtils.isNotEmpty(pages.getSearchs().get("directionName"))){
            //添加模糊查询的条件
            wrapper.like(Direction::getDirectionName,pages.getSearchs().get("directionName"));
        }

        return baseMapper.selectPage(page,wrapper);
    }
}
