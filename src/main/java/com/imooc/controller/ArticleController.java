package com.imooc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Article;
import com.imooc.service.impl.ArticleServiceImpl;
import com.imooc.utils.ReadAndWriteFile.ReadAndWriteFile;
import com.imooc.utils.common.Pages;
import com.imooc.utils.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("ArticleController")
public class ArticleController {

    @Resource
    ArticleServiceImpl articleServiceImpl;

    /**
     * 根据章节分页查询文章
     * @param map
     * @return
     */
    @RequestMapping(value = "findAll",method = RequestMethod.POST)
    public Result findAll(@RequestBody Map map){
        Result result = new Result();

        String chapterId = map.get("chapterId").toString();

        Pages pages = JSONObject.parseObject(map.get("pages").toString(), Pages.class);

        Page<Article> data = articleServiceImpl.findAll(pages, chapterId);

        pages.setTotal(data.getTotal());
        pages.setLastPage(data.getPages());

        result.putData("articleList",data.getRecords());
        result.setPages(pages);

        result.success(200, "SUCCESS");

        return result;
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @RequestMapping(value = "deleteArticle/{articleId}",method = RequestMethod.DELETE)
    public Result deleteArticle(@PathVariable("articleId") String articleId){
        Result result = new Result();

        boolean b = articleServiceImpl.deleteArticle(articleId);

        if(b){
            result.success(200,"删除成功");
        }

        return result;
    }


    /**
     * 添加文章
     * @param map
     * @return
     */
    @RequestMapping(value = "insertArticle",method = RequestMethod.POST)
    public Result insertArticle(@RequestBody Map map){

        Result result = new Result();

        String str = map.get("str").toString();
        Article article = JSONObject.parseObject(map.get("article").toString(), Article.class);

        //给文件命名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        //文件路径
        String url = "F:\\Project\\project3\\downloadFiles\\"+fileName+".txt";

        //写文件
        ReadAndWriteFile.writeFile(url,str);

        article.setArticleUrl(url);
        //插入数据
        boolean b = articleServiceImpl.insertArticle(article);

        if(b){
            result.success(200,"操作成功,跳转页面");
        }

        return result;
    }


    /**
     * 读取文章
     * @param articleId
     * @return
     */
    @RequestMapping(value = "readFile/{articleId}",method = RequestMethod.GET)
    public Result readFile(@PathVariable("articleId") String articleId){

        Result result = new Result();

        //根据主键查询文章路径
        Article article = articleServiceImpl.findById(articleId);
        String url = article.getArticleUrl();

        //读取文件
        String fileContent = ReadAndWriteFile.readFile(url);

        result.putData("fileContent",fileContent);

        result.putData("article",article);

        result.success(200,"SUCCESS");

        return result;
    }

    /**
     * 修改文章
     * @param map
     * @return
     */
    @RequestMapping(value = "updateArticle",method = RequestMethod.POST)
    public Result updateArticle(@RequestBody Map map){

        Result result = new Result();

        //重写文件
        String articleContent = map.get("articleContent").toString();
        Article article = JSONObject.parseObject(map.get("article").toString(), Article.class);

        //获取文件路径
        String url = article.getArticleUrl();

        //写文件
        ReadAndWriteFile.writeFile(url,articleContent);

        //修改数据
        boolean b = articleServiceImpl.update(article);

        if(b){
            result.success(200,"操作成功,跳转页面");
        }

        return result;
    }

    /**
     * 根据主键查询文章
     * @param articleId
     * @return
     */
    @RequestMapping(value = "findById/{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("articleId") String articleId){

        Result result = new Result();

        Article article = articleServiceImpl.findById(articleId);

        result.putData("articleInfo",article);

        result.success(200,"SUCCESS");

        return result;
    }

}
