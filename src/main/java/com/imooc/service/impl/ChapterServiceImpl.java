package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.ArticleDao;
import com.imooc.dao.ChapterDao;
import com.imooc.entity.Article;
import com.imooc.entity.Chapter;
import com.imooc.exception.ApiException;
import com.imooc.service.ChapterService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterDao, Chapter> implements ChapterService {

    @Resource
    ArticleDao articleDao;

    @Resource
    private VideoServiceImpl videoServiceImpl;

    /**
     * 添加章节
     *
     * @param chapter
     * @return
     */
    @Override
    public boolean append(Chapter chapter) {
        vaild(chapter);

        return baseMapper.insert(chapter) != 0;
    }

    /**
     * 根据章节 id 删除对应的章节数据和视频
     *
     * @param chapterId
     * @return
     */
    @Override
    public boolean removeChapterAndVideo(String chapterId) {

        // 删除对应的视频
        videoServiceImpl.removeByChapterId(Arrays.asList(chapterId));

        return baseMapper.deleteById(chapterId) != 0;
    }

    /**
     * 根据数据源 id 删除对应的所有章节
     *
     * @param resource 数据源 id
     * @return
     */
    @Override
    public boolean removeChapterByResource(String resource) {
        LambdaQueryWrapper<Chapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chapter::getChapterResource, resource);

        return baseMapper.delete(wrapper) != 0;
    }

    /**
     * 修改章节信息
     *
     * @param chapter
     * @return
     */
    @Override
    public boolean update(Chapter chapter) {

        vaild(chapter);

        return baseMapper.updateById(chapter) != 0;
    }

    /**
     * 根据数据源 id 查询到对应的章节 id
     *
     * @param resource
     * @return
     */
    @Override
    public List<String> findChapterIdByResource(String resource) {
        return baseMapper.findChapterIdByResource(resource);
    }

    /**
     * 根据 id 查询章节信息
     *
     * @param chapterId 章节 id
     * @return
     */
    @Override
    public Chapter findById(String chapterId) {
        return baseMapper.selectById(chapterId);
    }

    /**
     * 查询某个章节源的所有章节
     *
     * @param pages
     * @param chapterResource 章节源：课程 id /专刊 id
     * @return
     */
    @Override
    public Page<Chapter> findChapter(Pages pages, String chapterResource) {

        Page<Chapter> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        LambdaQueryWrapper<Chapter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chapter::getChapterResource, chapterResource);

        String chapterName = pages.getSearchs().get("chapterName");

        if (CommonUtils.isNotEmpty(chapterName)) {
            wrapper.like(Chapter::getChapterName, chapterName);
        }

        wrapper.orderByAsc(Chapter::getCreateTime);

        return baseMapper.selectPage(page, wrapper);
    }


    /**
     * 删除章节和文章
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChatsAndArts(String chapterId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Article::getChapterId,chapterId);

        //先删除章节下的文章
        articleDao.delete(wrapper);

        //在删除章节
        int i = baseMapper.deleteById(chapterId);
        return i != 0;
    }

    /**
     * 验证字段不为空
     *
     * @param chapter
     */
    private void vaild(Chapter chapter) {
        if (!CommonUtils.isNotEmpty(chapter.getChapterName())) {
            throw new ApiException(500, "标题不能为空");
        }
        if (!CommonUtils.isNotEmpty(chapter.getChapterAbout())) {
            throw new ApiException(500, "简介不能为空");
        }
    }
}
