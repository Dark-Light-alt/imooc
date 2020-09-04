package com.imooc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Course;
import com.imooc.service.impl.CourseServiceImpl;
import com.imooc.utils.aliyun.oss.FileStorageService;
import com.imooc.utils.aliyun.oss.FileStorageServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("CourseController")
public class CourseController {

    @Resource
    private CourseServiceImpl courseServiceImpl;

    @Resource
    private FileStorageServiceImpl fileStorageServiceImpl;

    @RequestMapping(value = "findByAuthor", method = RequestMethod.POST)
    public Result findByAuthor(@RequestBody Map<String, String> params) {

        Result result = new Result();

        String author = params.get("author");

        Pages pages = JSONObject.parseObject(params.get("pages"), Pages.class);

        Page<Course> data = courseServiceImpl.findByAuthor(pages, author);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.putData("courseList", data.getRecords());
        result.setPages(pages);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Course course) {

        Result result = new Result();

        courseServiceImpl.append(course);

        result.success(200, "课程新建成功");

        return result;
    }

    @RequestMapping(value = "findById/{courseId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String courseId) {

        Result result = new Result();

        result.putData("course", courseServiceImpl.findById(courseId));

        result.success(200, "SECCUSS");

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Course course) {

        Result result = new Result();

        courseServiceImpl.update(course);

        result.success(200, "课程信息修改成功");

        return result;
    }

    @RequestMapping(value = "remove/{courseId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String courseId) {

        Result result = new Result();

        courseServiceImpl.remove(courseId);

        result.success(200, "课程删除成功");

        return result;
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {

        Result result = new Result();

        InputStream in = file.getInputStream();

        String fileName = file.getOriginalFilename();

        String url = fileStorageServiceImpl.upload(in, fileName, FileStorageService.IMG);

        result.putData("url", url);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "previewCourse/{courseId}", method = RequestMethod.GET)
    public Result previewCourse(@PathVariable String courseId) {

        Result result = new Result();

        result.putData("course", courseServiceImpl.previewCourse(courseId));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 课程管理
     * 用于实战课程的上下架
     *
     * @return
     */
    @RequestMapping(value = "courseManage", method = RequestMethod.POST)
    public Result payForCourseManage(@RequestBody Map<String, String> params) {

        Result result = new Result();

        Pages pages = JSON.parseObject(params.get("pages"), Pages.class);

        Integer isfree = Integer.valueOf(params.get("isfree").toString());

        Page<Course> data = courseServiceImpl.courseManage(pages, isfree);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.putData("courseList", data.getRecords());
        result.setPages(pages);

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 课程状态修改 0未完成 1未上架 2 已上架 3已下架
     *
     * @param courseId 课程 id
     * @return
     */
    @RequestMapping(value = "changeStatus/{courseId}/{status}", method = RequestMethod.GET)
    public Result changeStatus(@PathVariable String courseId, @PathVariable Integer status) {

        Result result = new Result();

        courseServiceImpl.changeStatus(courseId, status);

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 课程定价
     *
     * @param course
     * @return
     */
    @RequestMapping(value = "pricing", method = RequestMethod.PUT)
    public Result pricing(@RequestBody Course course) {

        Result result = new Result();

        courseServiceImpl.pricing(course);

        result.success(200, "课程定价成功");

        return result;
    }

    /**
     * 课程下架
     *
     * @param courseId
     * @return
     */
    @RequestMapping(value = "soldout/{courseId}", method = RequestMethod.GET)
    public Result soldout(@PathVariable String courseId) {

        Result result = new Result();

        courseServiceImpl.changeStatus(courseId, 3);

        result.success(200, "课程下架成功");

        return result;
    }

    /**
     * 查询已经上架的课程
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result list() {

        Result result = new Result();

        result.putData("courseList", courseServiceImpl.list());

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 多条件查询上架课程
     * <p>
     * isfree           是否付费
     * directionId      是否按照课程方向查找
     * num              查询条数
     * news             是否按照最新上架
     * numberOfStudents 是否查询学习人数最多
     */
    @RequestMapping("findCourseByCondition")
    public Result findCourseByCondition(@RequestBody Map<String, Object> params) {

        Result result = new Result();

        List<Map<String, Object>> courseList = courseServiceImpl.findCourseByCondition(params);

        result.putData("courseList", courseList);

        result.success(200, "SUCCESS");

        return result;
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
    @RequestMapping(value = "findAssignCourse", method = RequestMethod.POST)
    public Result findAssignCourse(@RequestBody Map<String, Object> params) {

        Result result = new Result();

        System.out.println(params);

        List<Map<String, Object>> courseList = courseServiceImpl.findAssignCourse(params);

        result.putData("courseList", courseList);

        result.success(200, "SUCCESS");

        return result;
    }
}
