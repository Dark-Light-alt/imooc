package com.imooc.controller;

import com.imooc.entity.Chapter;
import com.imooc.service.impl.ChapterServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("ChapterController")
public class ChapterController {

    @Resource
    ChapterServiceImpl chapterServiceImpl;

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @RequestMapping(value = "append",method = RequestMethod.PUT)
    public Result append(@RequestBody Chapter chapter){
        Result result = new Result();

        boolean append = chapterServiceImpl.append(chapter);

        if(append){
            result.success(200,"操作成功");
        }

        return result;
    }
}
