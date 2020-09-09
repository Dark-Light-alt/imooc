package com.imooc.controller;

import com.imooc.entity.Evaluate;
import com.imooc.exception.ApiException;
import com.imooc.service.impl.EvaluateServiceImpl;
import com.imooc.service.impl.MyCourseServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("EvaluateController")
public class EvaluateController {

    @Resource
    private EvaluateServiceImpl evaluateServiceImpl;

    @Resource
    private MyCourseServiceImpl myCourseServiceImpl;

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Evaluate evaluate) {

        Result result = new Result();

        // 判断用户是否学习过此课程
        if (myCourseServiceImpl.findCourseCountByCustomer(evaluate.getAuthor(), evaluate.getEvaluateBeResource()) == 0) {
            throw new ApiException(500, "您未学习过此视频，无法进行评价");
        }

        evaluateServiceImpl.append(evaluate);

        result.success(200, "评价发送成功");

        return result;
    }

    @RequestMapping(value = "findAll/{resourceId}", method = RequestMethod.GET)
    public Result findAll(@PathVariable String resourceId) {

        Result result = new Result();

        result.putData("evaluateList", evaluateServiceImpl.findAll(resourceId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "giveALike/{evaluateId}", method = RequestMethod.GET)
    public Result giveALike(@PathVariable String evaluateId) {

        Result result = new Result();

        evaluateServiceImpl.giveALike(evaluateId);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "rate/{resourceId}", method = RequestMethod.GET)
    public Result rate(@PathVariable String resourceId) {

        Result result = new Result();

        result.putData("rate", evaluateServiceImpl.rate(resourceId));

        result.success(200, "SUCCESS");

        return result;
    }
}
