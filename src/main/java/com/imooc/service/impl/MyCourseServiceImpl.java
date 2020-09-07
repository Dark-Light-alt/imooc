package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.MyCourseDao;
import com.imooc.entity.MyCourse;
import com.imooc.service.MyCourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 我的课程服务实现
 */
@Service
public class MyCourseServiceImpl extends ServiceImpl<MyCourseDao, MyCourse> implements MyCourseService {

    /**
     * 课程添加有两种情况：
     * 1.用户观看免费课程时，将添加
     * 2.当用户购买过实战课程之后
     *
     * @param myCourse
     * @return
     */
    @Override
    public int append(MyCourse myCourse) {

        if (!isExist(myCourse.getCustomerId(), myCourse.getCourseId())) {
            System.out.println("进来了");
            return 0;
        }

        return baseMapper.insert(myCourse);
    }

    /**
     * 根据用户 id 和 课程 id 查询条数
     *
     * @param customerId 用户 id
     * @param courseId   课程 id
     * @return
     */
    @Override
    public int findCourseCountByCustomer(String customerId, String courseId) {

        LambdaQueryWrapper<MyCourse> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(MyCourse::getCustomerId, customerId);
        wrapper.eq(MyCourse::getCourseId, courseId);

        return baseMapper.selectCount(wrapper);
    }

    /**
     * 查询用户的课程
     *
     * @param customerId 用户 id
     * @param isfree     0 免费 1 付费 null 全部
     * @return
     */
    @Override
    public List<Map<String, Object>> findByCustomer(String customerId, Integer isfree) {
        return baseMapper.findByCustomer(customerId, isfree);
    }

    /**
     * 查询用户是否已经具有此课程
     *
     * @param customerId 用户 id
     * @param courseId   课程 id
     * @return
     */
    private boolean isExist(String customerId, String courseId) {

        LambdaQueryWrapper<MyCourse> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(MyCourse::getCustomerId, customerId);
        wrapper.eq(MyCourse::getCourseId, courseId);

        return baseMapper.selectCount(wrapper) == 0;
    }
}
