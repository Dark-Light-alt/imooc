package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Chapter;
import com.imooc.service.impl.ChapterServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("ChapterController")
public class ChapterController {

    @Resource
    private ChapterServiceImpl chapterServiceImpl;

    /**
     * 添加章节
     *
     * @param chapter
     * @return
     */
    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Chapter chapter) {

        Result result = new Result();

        chapterServiceImpl.append(chapter);

        result.success(200, "章节信息添加成功");

        return result;
    }

    /**
     * 查询某个章节源的所有章节
     */
    @RequestMapping(value = "findChapter", method = RequestMethod.POST)
    public Result findChapter(@RequestBody Map<String, String> params) {

        Result result = new Result();

        String chapterResource = params.get("chapterResource");

        Pages pages = JSONObject.parseObject(params.get("pages"), Pages.class);

        Page<Chapter> data = chapterServiceImpl.findChapter(pages, chapterResource);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.putData("chapterList", data.getRecords());
        result.setPages(pages);

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 根据章节 id 查询章节信息
     *
     * @param chapterId
     * @return
     */
    @RequestMapping(value = "findById/{chapterId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String chapterId) {

        Result result = new Result();

        result.putData("chapter", chapterServiceImpl.findById(chapterId));

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 修改章节信息
     *
     * @param chapter
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Chapter chapter) {

        Result result = new Result();

        chapterServiceImpl.update(chapter);

        result.success(200, "章节信息修改成功");

        return result;
    }

    /**
     * 根据章节 id 删除章节信息和对应的视频
     *
     * @param chapterId
     * @return
     */
    @RequestMapping(value = "removeChapterAndVideo/{chapterId}", method = RequestMethod.DELETE)
    public Result removeChapterAndVideo(@PathVariable String chapterId) {

        Result result = new Result();

        chapterServiceImpl.removeChapterAndVideo(chapterId);

        result.success(200, "章节信息删除成功");

        return result;
    }
}
