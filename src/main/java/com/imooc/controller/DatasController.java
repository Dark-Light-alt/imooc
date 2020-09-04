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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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

    @RequestMapping(value = "remove/{dataId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String dataId) {

        Result result = new Result();

        datasServiceImpl.remove(dataId);

        result.success(200, "资料删除成功");

        return result;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result update(@RequestBody Datas datas) {

        Result result = new Result();

        datasServiceImpl.update(datas);

        result.success(200, "资料信息修改成功");

        return result;
    }

    @RequestMapping(value = "download", method = RequestMethod.POST)
    public void download(@RequestBody Map<String, String> params, HttpServletResponse response) throws UnsupportedEncodingException {

        String url = params.get("url");

        url = url.substring(url.lastIndexOf(FileStorageService.DATA));
        System.out.println(url);

        InputStream in = fileStorageServiceImpl.getInputStream(url);

        String fileName = url.substring(url.lastIndexOf("/") + 1);

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        byte[] bytes = new byte[1024];

        BufferedInputStream bis = null;

        OutputStream os = null;

        try {
            os = response.getOutputStream();

            bis = new BufferedInputStream(in);

            int len = 0;

            while ((len = bis.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }

            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
