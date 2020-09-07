package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.MyCourse;

import java.util.List;
import java.util.Map;

/**
 * 我的课程服务
 */
public interface MyCourseService extends IService<MyCourse> {

    /**
     * 添加我的课程信息
     *
     * @param myCourse
     * @return
     */
    int append(MyCourse myCourse);

    /**
     * 根据用户 id 和 课程 id 查询条数
     *
     * @param customerId 用户 id
     * @param courseId   课程 id
     * @return
     */
    int findCourseCountByCustomer(String customerId, String courseId);


    /**
     * 查询用户的课程
     *
     * @param customerId 用户 id
     * @param isfree     0 免费 1 付费 null 全部
     * @return
     */
    List<Map<String, Object>> findByCustomer(String customerId, Integer isfree);
}
