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
     * 课程管理
     * 用于课程的上下架
     *
     * @return
     */
    @Override
    public Page<Course> courseManage(Pages pages, Integer isfree) {

        Page<Course> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getIsfree, isfree);
        wrapper.ne(Course::getCourseStatus, 0);

        String courseName = pages.getSearchs().get("courseName");

        if (CommonUtils.isNotEmpty(courseName)) {
            wrapper.like(Course::getCourseName, courseName);
        }

        wrapper.orderByDesc(Course::getCreateTime);

        return page.setRecords(baseMapper.courseManage(page, wrapper));
    }

    /**
     * 多条件查询上架课程
     * <p>
     * isfree           是否付费
     * directionId      是否按照课程方向查找
     * num              查询条数
     * news             是否按照最新上架
     * numberOfStudents 是否查询学习人数最多
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findCourseByCondition(Map<String, Object> params) {

        Integer isfree = null;

        if (null != params.get("isfree")) {
            isfree = Integer.valueOf(params.get("isfree").toString());
        }

        String directionId = null;

        if (null != params.get("directionId")) {
            directionId = params.get("directionId").toString();
        }

        Integer num = null;

        if (null != params.get("num")) {
            num = Integer.valueOf(params.get("num").toString());
        }

        Boolean news = Boolean.valueOf(params.get("news").toString());

        Boolean numberOfStudents = Boolean.valueOf(params.get("numberOfStudents").toString());

        return baseMapper.findCourseByCondition(isfree, directionId, num, news, numberOfStudents);
    }

    /**
     * 查询指定的课程根据 是否付费、方向、类别、难度
     * <p>
     * isfree      是否付费 0 免费 1 付费
     * directionId 方向 id，null 全部
     * typeId      类别 id，null 全部
     * level       难度 0 入门 1初级 2 中级 3 高级，null 全部
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findAssignCourse(Map<String, Object> params) {

        Integer isfree = null;

        if (null != params.get("isfree")) {
            isfree = Integer.valueOf(params.get("isfree").toString());
        }

        String directionId = null;

        if (null != params.get("directionId") && !"null".equals(params.get("directionId"))) {
            directionId = params.get("directionId").toString();
        }

        String typeId = null;

        if (null != params.get("typeId")) {
            typeId = params.get("typeId").toString();
        }

        Integer level = null;

        if (null != params.get("level")) {
            level = Integer.valueOf(params.get("level").toString());
        }

        return baseMapper.findAssignCourse(isfree, directionId, typeId, level);
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
