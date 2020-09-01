package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.CarouselDao;
import com.imooc.entity.Carousel;
import com.imooc.exception.ApiException;
import com.imooc.service.CarouselService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播控制服务实现
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselDao, Carousel> implements CarouselService {

    /**
     * 添加轮播控制
     *
     * @param carousel
     * @return
     */
    public boolean append(Carousel carousel) {

        valid(carousel);

        return baseMapper.insert(carousel) != 0;
    }

    public boolean remove(String carouselId) {
        return baseMapper.deleteById(carouselId) != 0;
    }

    private void valid(Carousel carousel) {

        if (!CommonUtils.isNotEmpty(carousel.getCourseId())) {
            throw new ApiException(500, "课程选项不能为空");
        }

        if (!CommonUtils.isNotEmpty(carousel.getCover())) {
            throw new ApiException(500, "课程封面不能为空");
        }

        if (null == carousel.getSort()) {
            throw new ApiException(500, "排序不能为空");
        }
    }

    /**
     * 分页查询所有
     *
     * @param pages
     * @return
     */
    @Override
    public Page<Carousel> findAll(Pages pages) {

        Page<Carousel> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        LambdaQueryWrapper<Carousel> wrapper = new LambdaQueryWrapper<>();

        wrapper.orderByAsc(Carousel::getSort);

        return page.setRecords(baseMapper.findAll(page, wrapper));
    }

    public List<Carousel> findAllList() {

        LambdaQueryWrapper<Carousel> wrapper = new LambdaQueryWrapper<>();

        wrapper.orderByAsc(Carousel::getSort);

        return baseMapper.selectList(wrapper);
    }
}
