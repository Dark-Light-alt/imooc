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
     * 课程管理
     * 用于课程的上下架
     *
     * @param page
     * @param wrapper
     * @return
     */
    List<Course> courseManage(Page<Course> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Course> wrapper);

    /**
     * 多条件查询上架课程
     *
     * @param isfree           是否付费
     * @param directionId      是否按照课程方向查找
     * @param num              查询条数
     * @param news             是否按照最新上架
     * @param numberOfStudents 是否查询学习人数最多
     * @return
     */
    List<Map<String, Object>> findCourseByCondition(@Param("isfree") Integer isfree, @Param("directionId") String directionId, @Param("num") Integer num, @Param("news") Boolean news, @Param("numberOfStudents") Boolean numberOfStudents);

    /**
     * 查询指定的课程根据 是否付费、方向、类别、难度
     *
     * @param isfree      是否付费 0 免费 1 付费
     * @param directionId 方向 id，null 全部
     * @param typeId      类别 id，null 全部
     * @param level       难度 0 入门 1初级 2 中级 3 高级，null 全部
     * @return
     */
    List<Map<String, Object>> findAssignCourse(@Param("isfree") Integer isfree, @Param("directionId") String directionId, @Param("typeId") String typeId, @Param("level") Integer level);
}
