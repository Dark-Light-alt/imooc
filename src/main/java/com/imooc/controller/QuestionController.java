package com.imooc.controller;

import com.imooc.entity.Question;
import com.imooc.service.impl.QuestionServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("QuestionController")
public class QuestionController {

    @Resource
    private QuestionServiceImpl questionServiceImpl;

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Question question) {

        Result result = new Result();

        questionServiceImpl.append(question);

        result.success(200, "问题发布成功，静静等待其他小伙伴的帮助吧！");

        return result;
    }

    /**
     * 根据课程 id 查询问题
     *
     * @param courseId 课程 id
     * @return
     */
    @RequestMapping(value = "findByCourse/{courseId}", method = RequestMethod.GET)
    public Result findByCourse(@PathVariable String courseId) {

        Result result = new Result();

        result.putData("questionList", questionServiceImpl.findByCourse(courseId));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 根据视频 id 查询问题
     *
     * @param videoId 视频 id
     * @return
     */
    @RequestMapping(value = "findByVideo/{videoId}", method = RequestMethod.GET)
    public Result findByVideo(@PathVariable String videoId) {

        Result result = new Result();

        result.putData("questionList", questionServiceImpl.findByVideo(videoId));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 根据问题 id 查询信息
     *
     * @param questionId 问题 id
     * @return
     */
    @RequestMapping(value = "findById/{questionId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String questionId) {

        Result result = new Result();

        result.putData("question", questionServiceImpl.findById(questionId));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 浏览数增加
     *
     * @param questionId 问题 id
     * @return
     */
    @RequestMapping(value = "browse/{questionId}", method = RequestMethod.GET)
    public Result browse(@PathVariable String questionId) {

        Result result = new Result();

        questionServiceImpl.browse(questionId);

        result.success(200, "SUCCESS");

        return result;
    }
}
