package com.imooc.controller;

import com.imooc.entity.ReportReason;
import com.imooc.service.impl.ReportReasonSerivceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("ReportReasonController")
public class ReportReasonController {

    @Resource
    private ReportReasonSerivceImpl reportReasonSerivceImpl;

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody ReportReason reportReason) {

        Result result = new Result();

        reportReasonSerivceImpl.append(reportReason);

        result.success(200, "举报原因添加成功");

        return result;
    }

    @RequestMapping(value = "remove/{reportReasonId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String reportReasonId) {

        Result result = new Result();

        reportReasonSerivceImpl.removeById(reportReasonId);

        result.success(200, "举报原因删除成功");

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody ReportReason reportReason) {

        Result result = new Result();

        reportReasonSerivceImpl.update(reportReason);

        result.success(200, "举报原因修改成功");

        return result;
    }

    @RequestMapping(value = "findById/{reportReasonId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String reportReasonId) {

        Result result = new Result();

        result.putData("reportReason", reportReasonSerivceImpl.getById(reportReasonId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {

        Result result = new Result();

        result.putData("reportReasonList", reportReasonSerivceImpl.list());

        result.success(200, "SUCCESS");

        return result;
    }
}
