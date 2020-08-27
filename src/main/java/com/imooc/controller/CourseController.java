package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Course;
import com.imooc.service.impl.CourseServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("CourseController")
public class CourseController {

    @Resource
    private CourseServiceImpl courseServiceImpl;

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
}
