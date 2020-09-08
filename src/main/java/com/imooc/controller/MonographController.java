package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Article;
import com.imooc.entity.Monograph;
import com.imooc.service.impl.MonographServiceImpl;
import com.imooc.utils.aliyun.oss.FileStorageService;
import com.imooc.utils.aliyun.oss.FileStorageServiceImpl;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("MonographController")
public class MonographController {

    @Resource
    FileStorageServiceImpl fileStorageServiceImpl;

    @Resource
    MonographServiceImpl monographServiceImpl;

    /**
     * 修改
     * @param monograph
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Result update(@RequestBody Monograph monograph){
        System.out.println("monograph:"+monograph);

        Result result = new Result();

        boolean update = monographServiceImpl.update(monograph);


        result.success(200,"操作成功");


        return result;
    }


    /**
     * 根据monographId查询
     * @param monographId
     * @return
     */
    @RequestMapping(value = "findById/{monographId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("monographId")String monographId){
        Result result = new Result();

        Monograph monograph = monographServiceImpl.findById(monographId);

        result.putData("monograph",monograph);

        result.success(200,"SUCCESS");

        return result;
    }

    /**
     * 修改专栏状态
     * @param map
     * @return
     */
    @RequestMapping(value = "updateOffShelf",method = RequestMethod.POST)
    public Result soldOut(@RequestBody Map map){
        String monographId = map.get("monographId").toString();

        int offShelf = Integer.parseInt(map.get("offShelf").toString());
        Result result = new Result();

        boolean update = monographServiceImpl.updateOffShelf(monographId,offShelf);

        if(update){
            result.success(200,"操作成功");
        }

        return result;
    }

    /**
     * 添加
     * @param monograph
     * @return
     */
    @RequestMapping(value = "append",method = RequestMethod.PUT)
    public Result append(@RequestBody Monograph monograph){
        System.out.println(monograph);

        Result result = new Result();

        boolean append = monographServiceImpl.append(monograph);

        if(append){
            result.success(200,"操作成功");
        }

        return result;
    }


    /**
     * 分页关联查询专栏和作者
     * @param map
     * @return
     */
    @RequestMapping(value = "pageFindMonographAuthor",method = RequestMethod.POST)
    public Result pageFindMonographAuthor(@RequestBody Map map){
        Result result = new Result();

        Pages pages =  JSONObject.parseObject(map.get("pages").toString(),Pages.class);

        Page<Monograph> data = null;

        if(null != map.get("employeeId")){
            //如果员工编号不为null
            //查询员工的专刊
            String employeeId = map.get("employeeId").toString();
            data = monographServiceImpl.findAllByEmployeeId(pages,employeeId);

            System.out.println(data);
        }else
        {
            //根据完成状态分页查询
            data = monographServiceImpl.pageFindMonographAuthor(pages);
        }

        //设置总页数和总条数
        pages.setTotal(data.getTotal());
        pages.setLastPage(data.getPages());

        result.setPages(pages);
        result.putData("monographList",data.getRecords());

        result.success(200,"SUCCESS");

        return result;
    }

    /**
     * 删除专栏
     * @param monographId
     * @return
     */
    @RequestMapping(value = "delete/{monographId}",method = RequestMethod.GET)
    public Result delete(@PathVariable("monographId") String monographId){

        Result result = new Result();

        int i = monographServiceImpl.delete(monographId);

        if(i>0){
            result.success(200,"操作成功");
        }

        return result;
    }


    @RequestMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {

        Result result = new Result();

        InputStream inputStream = file.getInputStream();

        String fileName = file.getOriginalFilename();

        String url = fileStorageServiceImpl.upload(inputStream, fileName, FileStorageService.IMG);

        result.putData("url",url);

        result.success(200,"SUCCESS");

        return result;
    }

    @RequestMapping(value = "previewMonograph",method = RequestMethod.POST)
    public Result previewMonograph(@RequestBody Map map){
        Result result = new Result();


        String monographId = map.get("monographId").toString();
        System.out.println(monographId);
        if(null != monographId){
            Monograph monograph = monographServiceImpl.previewMonograph(monographId);

            result.putData("monograph",monograph);
        }

        result.success(200,"SUCCESS");

        return result;
    }

    /**
     * 根据条件查询所有上架专刊
     * @return
     */
    @RequestMapping(value = "listAllMonograph",method = RequestMethod.GET)
    public Result listAllMonograph(){
        Result result = new Result();

        //查询所有试读的文章
        List<Monograph> monographList = monographServiceImpl.listAllMonograph(null,2);

        result.putData("monographList",monographList);

        result.success(200,"SUCCESS");

        return result;
    }


    /**
     * 查询专刊下的章节文章
     * @return
     */
    @RequestMapping(value = "listAllArticle/{articleId}",method = RequestMethod.GET)
    public Result listAllArticle(@PathVariable("articleId") String articleId){
        Result result = new Result();

        //根据文章编号查询专刊
        Monograph monograph = monographServiceImpl.findMonographByArticleId(articleId);
        String monographId = monograph.getMonographId();

        //查询专刊下的所有章节和文章
        List<Monograph> monographList = monographServiceImpl.listAllMonograph(monographId,2);

        result.putData("monographList",monographList);

        result.success(200,"SUCCESS");

        return result;
    }



}
