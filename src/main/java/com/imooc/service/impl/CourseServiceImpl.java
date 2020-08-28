package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.CourseDao;
import com.imooc.entity.Course;
import com.imooc.exception.ApiException;
import com.imooc.service.CourseService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程服务实现
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {

    @Resource
    private ChapterServiceImpl chapterServiceImpl;

    @Resource
    private VideoServiceImpl videoServiceImpl;

    /**
     * 添加课程信息
     *
     * @param course
     * @return
     */
    @Override
    public boolean append(Course course) {

        // 验证
        valid(course);

        return baseMapper.insert(course) != 0;
    }

    /**
     * 根据课程 id 删除课程
     * 注意：目录和课程视频也随之删除
     *
     * @param courseId 课程 id
     * @return
     */
    @Override
    public boolean remove(String courseId) {

        // 查询到课程对应的所有章节
        List<String> chapterIds = chapterServiceImpl.findChapterIdByResource(courseId);

        if (null != chapterIds && chapterIds.size() != 0) {
            // 删除章节下的所有视频
            videoServiceImpl.removeByChapterId(chapterIds);

            // 删除章节
            chapterServiceImpl.removeChapterByResource(courseId);
        }
        // 删除课程
        return baseMapper.deleteById(courseId) != 0;
    }

    @Override
    public boolean update(Course course) {
        // 验证
        valid(course);

        return baseMapper.updateById(course) != 0;
    }

    @Override
    public boolean changeStatus(String courseId, Integer status) {

        LambdaUpdateWrapper<Course> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Course::getCourseId, courseId)
                .set(Course::getCourseStatus, status);

        return baseMapper.update(null, wrapper) != 0;
    }

    @Override
    public Course findById(String courseId) {
        return baseMapper.selectById(courseId);
    }

    @Override
    public Page<Course> findByAuthor(Pages pages, String author) {

        Page<Course> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getAuthor, author);

        String courseName = pages.getSearchs().get("courseName");

        if (CommonUtils.isNotEmpty(courseName)) {
            wrapper.like(Course::getCourseName, courseName);
        }

        wrapper.orderByDesc(Course::getCreateTime);

        return baseMapper.selectPage(page, wrapper);
    }


    private void valid(Course course) {

        if (!CommonUtils.isNotEmpty(course.getCourseName())) {
            throw new ApiException(500, "课程标题不能为空");
        }

        if (!CommonUtils.isNotEmpty(course.getCourseAbout())) {
            throw new ApiException(500, "课程简介不能为空");
        }

        if (!CommonUtils.isNotEmpty(course.getCover())) {
            throw new ApiException(500, "课程封面不能为空");
        }

        if (null == course.getCourseLevel()) {
            throw new ApiException(500, "课程级别不能为空");
        }
    }
}
