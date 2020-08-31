package com.imooc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Carousel;
import com.imooc.service.impl.CarouselServiceImpl;
import com.imooc.utils.aliyun.oss.FileStorageService;
import com.imooc.utils.aliyun.oss.FileStorageServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("CarouselController")
public class CarouselController {

    @Resource
    private CarouselServiceImpl carouselServiceImpl;

    @Resource
    private FileStorageServiceImpl fileStorageServiceImpl;

    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody Pages pages) {

        Result result = new Result();

        Page<Carousel> data = carouselServiceImpl.findAll(pages);

        pages.setLastPage(data.getPages());
        pages.setTotal(data.getTotal());

        result.putData("carouselList", data.getRecords());
        result.setPages(pages);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Result upload(@RequestParam MultipartFile file) throws IOException {

        Result result = new Result();

        String fileName = file.getOriginalFilename();

        InputStream in = file.getInputStream();

        String url = fileStorageServiceImpl.upload(in, fileName, FileStorageService.IMG);

        result.putData("url", url);

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "append", method = RequestMethod.PUT)
    public Result append(@RequestBody Carousel carousel) {

        Result result = new Result();

        carouselServiceImpl.append(carousel);

        result.success(200, "轮播信息添加成功");

        return result;
    }

    @RequestMapping(value = "remove/{carouselId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String carouselId) {

        Result result = new Result();

        carouselServiceImpl.remove(carouselId);

        result.success(200, "轮播信息删除成功");

        return result;
    }

    @RequestMapping(value = "findAllList", method = RequestMethod.GET)
    public Result findAllList() {

        Result result = new Result();

        result.putData("carouselList", carouselServiceImpl.findAllList());

        result.success(200, "SUCCESS");

        return result;
    }
}
