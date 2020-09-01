package com.imooc.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Article;
import com.imooc.utils.common.Pages;

import java.util.List;

public interface ArticleService extends IService<Article> {

    /**
     *  根据章节分页查询文章
     * @param pages
     * @param chapterId
     * @return
     */
    Page<Article> findAll(Pages pages,String chapterId);

    /**
     * 根据主键删除文章
     * @param articleId
     * @return
     */
    boolean deleteArticle(String articleId);

    /**
     * 添加文章
     * @param article
     * @return
     */
    boolean insertArticle(Article article);

    /**
     * 修改文章
     * @param article
     * @return
     */
    boolean update(Article article);

    /**
     * 根据主键查询
     * @param articleId
     * @return
     */
    Article findById(String articleId);

    /**
     * 根据条件删除文章
     * @param wrapper
     * @return
     */
    int deleteByWrapper(LambdaQueryWrapper<Article> wrapper);

    /**
     * 根据主键articleId删除文章
     * @param articleId
     * @return
     */
    int deleteById(String articleId);

    /**
     * 根据条件查询文章列表
     * @param wrapper
     * @return
     */
    List<Article> selectByWrapper(LambdaQueryWrapper<Article> wrapper);
}
