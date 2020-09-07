package com.imooc.controller;

import com.imooc.entity.Comments;
import com.imooc.service.impl.CommentsServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("CommentsController")
public class CommentsController {

    @Resource
    private CommentsServiceImpl commentsServiceImpl;

    @RequestMapping(value = "findAll/{videoId}", method = RequestMethod.GET)
    public Result findAll(@PathVariable String videoId) {

        Result result = new Result();

        result.putData("commentList", commentsServiceImpl.findAll(videoId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Comments comments) {

        Result result = new Result();

        commentsServiceImpl.append(comments);

        result.success(200, "评论发布成功");

        return result;
    }

    @RequestMapping(value = "like/{commentId}", method = RequestMethod.GET)
    public Result like(@PathVariable String commentId) {

        Result result = new Result();

        commentsServiceImpl.like(commentId);

        result.success(200, "SUCCESS");

        return result;
    }
}
