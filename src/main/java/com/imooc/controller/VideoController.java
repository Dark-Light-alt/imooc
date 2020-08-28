package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Video;
import com.imooc.service.impl.VideoServiceImpl;
import com.imooc.utils.aliyun.oss.FileStorageService;
import com.imooc.utils.aliyun.oss.FileStorageServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("VideoController")
public class VideoController {

    @Resource
    private VideoServiceImpl videoServiceImpl;

    @Resource
    private FileStorageServiceImpl fileStorageServiceImpl;

    @RequestMapping(value = "findAllByChapter", method = RequestMethod.POST)
    public Result findAllByChapter(@RequestBody Map<String, String> param) {

        Result result = new Result();

        String chapterId = param.get("chapterId");

        Pages pages = JSONObject.parseObject(param.get("pages"), Pages.class);

        Page<Video> data = videoServiceImpl.findAllByChapter(pages, chapterId);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.putData("videoList", data.getRecords());
        result.setPages(pages);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Video video) {

        Result result = new Result();

        videoServiceImpl.append(video);

        result.success(200, "视频课程上传成功");

        return result;
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {

        Result result = new Result();

        InputStream in = file.getInputStream();

        String fileName = file.getOriginalFilename();

        String url = fileStorageServiceImpl.upload(in, fileName, FileStorageService.VIDEO);

        result.putData("url", url);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "remove/{videoId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String videoId) {

        Result result = new Result();

        videoServiceImpl.removeById(videoId);

        result.success(200, "视频课程删除成功");

        return result;
    }

    @RequestMapping(value = "findById/{videoId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String videoId) {

        Result result = new Result();

        result.putData("video", videoServiceImpl.findById(videoId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Video video) {

        Result result = new Result();

        videoServiceImpl.update(video);

        result.success(200, "视频课程修改成功");

        return result;
    }
}
