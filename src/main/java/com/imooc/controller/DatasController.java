package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Datas;
import com.imooc.service.impl.DatasServiceImpl;
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
@RequestMapping("DatasController")
public class DatasController {

    @Resource
    private DatasServiceImpl datasServiceImpl;

    @Resource
    private FileStorageServiceImpl fileStorageServiceImpl;

    @RequestMapping(value = "findAllByCourseId", method = RequestMethod.POST)
    public Result findAllByCourseId(@RequestBody Map<String, String> params) {

        String courseId = params.get("courseId");

        Pages pages = JSONObject.parseObject(params.get("pages"), Pages.class);

        Result result = new Result();

        Page<Datas> data = datasServiceImpl.findAllByCourseId(pages, courseId);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());
        result.setPages(pages);

        result.putData("datasList", data.getRecords());

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {

        Result result = new Result();

        InputStream in = file.getInputStream();

        String fileName = file.getOriginalFilename();

        String url = fileStorageServiceImpl.upload(in, fileName, FileStorageService.DATA);

        result.putData("url", url);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Datas datas) {

        Result result = new Result();

        datasServiceImpl.append(datas);

        result.success(200, "资料上传成功");

        return result;
    }
}
