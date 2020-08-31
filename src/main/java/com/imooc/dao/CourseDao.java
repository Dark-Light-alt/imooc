package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseDao extends BaseMapper<Course> {

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
     * @param page
     * @param wrapper
     * @return
     */
    List<Course> payForCourseManage(Page<Course> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Course> wrapper);

    /**
     * 免费课程管理
     * 用于课程的上下架
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<Course> freeForCourseManage(Page<Course> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Course> wrapper);
}
