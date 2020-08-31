package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Course;
import com.imooc.utils.common.Pages;

/**
 * 课程服务
 */
public interface CourseService extends IService<Course> {

    /**
     * 添加课程
     *
     * @param course
     * @return
     */
    boolean append(Course course);

    /**
     * 根据课程 id 删除课程
     * 注意：目录和课程视频也随之删除
     *
     * @param courseId 课程 id
     * @return
     */
    boolean remove(String courseId);

    /**
     * 修改课程
     *
     * @param course
     * @return
     */
    boolean update(Course course);

    /**
     * 修改课程状态：0未完成 1未上架 2已上架 3已下架
     *
     * @param courseId 课程 id
     * @param status   状态值
     * @return
     */
    boolean changeStatus(String courseId, Integer status);

    /**
     * 定价
     *
     * @param course
     * @return
     */
    boolean pricing(Course course);

    /**
     * 根据 id 查询课程信息
     *
     * @param courseId
     * @return
     */
    Course findById(String courseId);

    /**
     * 根据作者查询课程信息
     *
     * @param pages
     * @param author 作者 id
     * @return
     */
    Page<Course> findByAuthor(Pages pages, String author);

    /**
     * 课程预览
     * 查看课程下的章节，章节下的视频课程
     *
     * @param courseId
     * @return
     */
    Course previewCourse(String courseId);

    /**
     * 实战课程管理
     * 用于课程的上下架
     *
     * @return
     */
    Page<Course> payForCourseManage(Pages pages);

    /**
     * 免费课程管理
     * 用于课程的上下架
     *
     * @param pages
     * @return
     */
    Page<Course> freeForCourseManage(Pages pages);
}
