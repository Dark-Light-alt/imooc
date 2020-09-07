package com.imooc.controller;

import com.imooc.entity.ReportReview;
import com.imooc.service.impl.ReportReviewServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("ReportReviewController")
public class ReportReviewController {

    @Resource
    private ReportReviewServiceImpl reportReviewServiceImpl;

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody ReportReview reportReview) {

        Result result = new Result();

        reportReviewServiceImpl.append(reportReview);

        result.success(200, "举报信息已提交，请等待处理结果");

        return result;
    }
}
