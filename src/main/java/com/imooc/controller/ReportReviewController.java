package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.ReportReview;
import com.imooc.service.impl.ReportReviewServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

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

    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String, String> params) {

        Result result = new Result();

        Integer reportType = null;

        System.out.println("啊啊啊啊啊   "+params.get("reportType"));

        if (null != params.get("reportType") && !"null".equals(params.get("reportType"))) {
            reportType = Integer.valueOf(params.get("reportType"));
        }

        Pages pages = JSONObject.parseObject(params.get("pages"), Pages.class);

        Page<ReportReview> data = reportReviewServiceImpl.findAll(pages, reportType);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.putData("reportReviewList", data.getRecords());
        result.setPages(pages);

        result.success(200, "SUCCESS");

        return result;
    }
}
