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
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 课程服务实现
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {

    @Resource
    private ChapterServiceImpl chapterServiceImpl;

    @Resource
    private VideoServiceImpl videoServiceImpl;

    @Resource
    private DatasServiceImpl datasServiceImpl;

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

        // 删除课程对应的资料
        datasServiceImpl.removeByCourseId(courseId);

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

    /**
     * 定价
     *
     * @param course
     * @return
     */
    @Override
    public boolean pricing(Course course) {

        if (null == course.getPrice()) {
            throw new ApiException(500, "课程价格不能为空");
        }

        if (course.getPrice().compareTo(new BigDecimal("0")) == 0 || course.getPrice().compareTo(new BigDecimal("0")) == -1) {
            throw new ApiException(500, "课程价格不能小于0或等于0");
        }

        LambdaUpdateWrapper<Course> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Course::getPrice, course.getPrice());
        wrapper.eq(Course::getCourseId, course.getCourseId());

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

    /**
     * 课程预览
     * 查看课程下的章节，章节下的视频课程
     *
     * @param courseId
     * @return
     */
    @Override
    public Course previewCourse(String courseId) {
        return baseMapper.previewCourse(courseId);
    }


    /**
     * 实战课程管理
     * 用于课程的上下架
     *
     * @return
     */
    @Override
    public Page<Course> payForCourseManage(Pages pages) {

        Page<Course> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getIsfree, 1);
        wrapper.ne(Course::getCourseStatus, 0);

        String courseName = pages.getSearchs().get("payForCourseName");

        if (CommonUtils.isNotEmpty(courseName)) {
            wrapper.like(Course::getCourseName, courseName);
        }

        wrapper.orderByDesc(Course::getCreateTime);

        return page.setRecords(baseMapper.payForCourseManage(page, wrapper));
    }

    /**
     * 免费课程管理
     *
     * @param pages
     * @return
     */
    @Override
    public Page<Course> freeForCourseManage(Pages pages) {

        Page<Course> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getIsfree, 0);
        wrapper.ne(Course::getCourseStatus, 0);

        String courseName = pages.getSearchs().get("freeForCourseName");

        if (CommonUtils.isNotEmpty(courseName)) {
            wrapper.like(Course::getCourseName, courseName);
        }

        wrapper.orderByDesc(Course::getCreateTime);

        return page.setRecords(baseMapper.freeForCourseManage(page, wrapper));
    }

    /**
     * 根据课程方向查询已上架的课程
     *
     * @param directionId 课程方向 id
     * @param num         前 ？ 条
     * @return
     */
    @Override
    public List<Course> findCourseByDirection(String directionId, Integer num) {
        return baseMapper.findCourseByDirection(directionId, num);
    }

    /**
     * 根据学习人数查看上架的热门课程
     *
     * @param isfree 0 免费 1 实战课程
     * @param num    前 ？ 条
     * @return
     */
    @Override
    public List<Map<String, Object>> findHotCourse(Integer isfree, Integer num) {
        return baseMapper.findHotCourse(isfree, num);
    }

    /**
     * 根据学习人数和最新时间查询新上好课
     *
     * @param num 前 ？ 条
     * @return
     */
    @Override
    public List<Map<String, Object>> findNewCourse(Integer num) {
        return baseMapper.findNewCourse(num);
    }

    /**
     * 查询指定的免费课程根据 方向、类别、难度
     *
     * @param directionId 方向 id，null 全部
     * @param typeId      类别 id，null 全部
     * @param level       难度 0 入门 1初级 2 中级 3 高级，null 全部
     * @return
     */
    @Override
    public List<Map<String, Object>> findAssignFreeCourse(String directionId, String typeId, Integer level) {
        return baseMapper.findAssignFreeCourse(directionId, typeId, level);
    }

    /**
     * 查询已经上架的课程
     *
     * @return
     */
    public List<Course> list() {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Course::getCourseStatus, 2);
        wrapper.orderByDesc(Course::getCreateTime);
        return baseMapper.selectList(wrapper);
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
