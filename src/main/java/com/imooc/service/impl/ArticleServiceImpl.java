package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.ArticleDao;
import com.imooc.entity.Article;
import com.imooc.exception.ApiException;
import com.imooc.service.ArticleService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    /**
     * 根据章节查询所有文章
     *
     * @param pages
     * @param chapterId
     * @return
     */
    @Override
    public Page<Article> findAll(Pages pages, String chapterId) {
        Page<Article> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Article::getChapterId, chapterId);

        //根据文章标题模糊查询
        wrapper.like(Article::getArticleName, pages.getSearchs().get("articleName"));

        //根据时间排序
        wrapper.orderByAsc(Article::getCreateTime);

        return baseMapper.selectPage(page, wrapper);
    }

    /**
     * 根据主键删除文章
     *
     * @param articleId
     * @return
     */
    @Override
    public boolean deleteArticle(String articleId) {
        return baseMapper.deleteById(articleId) != 0;
    }

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    @Override
    public boolean insertArticle(Article article) {
        //标题不能为null
        valid(article);

        return baseMapper.insert(article) != 0;
    }

    /**
     * 修改文章
     *
     * @param article
     * @return
     */
    @Override
    public boolean update(Article article) {
        //标题不能为null
        valid(article);

        return baseMapper.updateById(article) != 0;
    }

    /**
     * 根据主键查询文章
     *
     * @param articleId
     * @return
     */
    @Override
    public Article findById(String articleId) {
        return baseMapper.selectById(articleId);
    }

    /**
     * 根据自定义条件删除文章
     * @param wrapper
     * @return
     */
    @Override
    public int deleteByWrapper(LambdaQueryWrapper<Article> wrapper) {
        return baseMapper.delete(wrapper);
    }

    /**
     * 根据主键articleId删除文章
     * @param articleId
     * @return
     */
    @Override
    public int deleteById(String articleId) {
        return baseMapper.deleteById(articleId);
    }

    @Override
    public List<Article> selectByWrapper(LambdaQueryWrapper<Article> wrapper) {
        return baseMapper.selectList(wrapper);
    }

    /**
     * 验证参数是否合法
     */
    private void valid(Article article) {
        if (!CommonUtils.isNotEmpty(article.getArticleName())) {
            throw new ApiException(500, "标题不能为空");
        }
    }
}
