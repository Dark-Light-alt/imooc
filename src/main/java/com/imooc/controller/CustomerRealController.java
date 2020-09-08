package com.imooc.controller;

import com.imooc.service.impl.CustomerRealServiceImpl;
import com.imooc.utils.aliyun.oss.FileStorageService;
import com.imooc.utils.aliyun.oss.FileStorageServiceImpl;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("CustomerRealController")
public class CustomerRealController {

    @Resource
    private CustomerRealServiceImpl customerRealServiceImpl;

    @Resource
    private FileStorageServiceImpl fileStorageServiceImpl;

    @RequestMapping(value = "findByCustomer/{customerId}", method = RequestMethod.GET)
    public Result findByCustomer(@PathVariable String customerId) {

        Result result = new Result();

        result.putData("customerReal", customerRealServiceImpl.findByCustomer(customerId));

        result.success(200, "SUCCESS");

        return result;
    }

    @RequestMapping(value = "authentication", method = RequestMethod.POST)
    public Result authentication(@RequestParam("file") MultipartFile file, @RequestParam("customerId") String customerId) throws IOException {

        Result result = new Result();

        InputStream in = file.getInputStream();

        String fileName = file.getOriginalFilename();

        String url = fileStorageServiceImpl.upload(in, fileName, FileStorageService.IDCARD);

        customerRealServiceImpl.authentication(customerId, url);

        result.success(200, "身份证认证成功");

        return result;
    }
}
