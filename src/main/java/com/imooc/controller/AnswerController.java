package com.imooc.controller;

import com.imooc.entity.Answer;
import com.imooc.service.impl.AnswerServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("AnswerController")
public class AnswerController {

    @Resource
    private AnswerServiceImpl answerServiceImpl;

    @RequestMapping(value = "findByQuestion/{questionId}", method = RequestMethod.GET)
    public Result findByQuestion(@PathVariable String questionId) {

        Result result = new Result();

        result.putData("answerList", answerServiceImpl.findByQuestion(questionId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Answer answer) {

        Result result = new Result();

        answerServiceImpl.append(answer);

        result.success(200, "回答成功");

        return result;
    }

    @RequestMapping(value = "like/{answerId}", method = RequestMethod.GET)
    public Result like(@PathVariable String answerId) {

        Result result = new Result();

        answerServiceImpl.like(answerId);

        result.success(200, "SUCCESS");

        return result;
    }
}
