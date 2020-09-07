package com.imooc.controller;

import com.imooc.entity.MyCourse;
import com.imooc.service.impl.MyCourseServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("MyCourseController")
public class MyCourseController {

    @Resource
    private MyCourseServiceImpl myCourseServiceImpl;

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody MyCourse myCourse) {

        Result result = new Result();

        myCourseServiceImpl.append(myCourse);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "findByCustomer", method = RequestMethod.POST)
    public Result find(@RequestBody Map<String, String> params) {

        Result result = new Result();

        String customerId = params.get("customerId");

        Integer isfree = null;

        if (null != params.get("isfree") && !"null".equals(params.get("isfree"))) {
            isfree = Integer.valueOf(params.get("isfree"));
        }

        result.putData("courseList", myCourseServiceImpl.findByCustomer(customerId, isfree));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "remove/{myCourseId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String myCourseId) {

        Result result = new Result();

        myCourseServiceImpl.removeById(myCourseId);

        result.success(200, "课程清除成功");

        return result;
    }
}
