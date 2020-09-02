package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据课程方向查询已上架的课程
     *
     * @param directionId 课程方向 id
     * @param num         前 ？ 条
     * @return
     */
    List<Course> findCourseByDirection(String directionId, Integer num);

    /**
     * 根据学习人数查看上架的热门课程
     *
     * @param isfree 0 免费 1 实战课程
     * @param num    前 ？ 条
     * @return
     */
    List<Map<String, Object>> findHotCourse(Integer isfree, Integer num);

    /**
     * 根据学习人数和最新时间查询新上好课
     *
     * @param num 前 ？ 条
     * @return
     */
    List<Map<String, Object>> findNewCourse(Integer num);

    /**
     * 查询指定的免费课程根据 方向、类别、难度
     *
     * @param directionId 方向 id，null 全部
     * @param typeId      类别 id，null 全部
     * @param level       难度 0 入门 1初级 2 中级 3 高级，null 全部
     * @return
     */
    List<Map<String, Object>> findAssignFreeCourse(@Param("directionId") String directionId, @Param("typeId") String typeId, @Param("level") Integer level);
}
