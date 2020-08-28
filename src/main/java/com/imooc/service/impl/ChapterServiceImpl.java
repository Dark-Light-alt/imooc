package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.ChapterDao;
import com.imooc.entity.Chapter;
import com.imooc.exception.ApiException;
import com.imooc.service.ChapterService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterDao, Chapter> implements ChapterService {

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
     * 根据课程 id 删除对应的所有章节和章节所对应的的视频
     *
     * @param courseId 章节 id
     * @return
     */
    @Override
    public boolean removeCourseChapter(String courseId) {
        return false;
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
     * 验证字段不为空
     *
     * @param chapter
     */
    public void vaild(Chapter chapter) {
        if (!CommonUtils.isNotEmpty(chapter.getChapterName())) {
            throw new ApiException(500, "标题不能为空");
        }
        if (!CommonUtils.isNotEmpty(chapter.getChapterAbout())) {
            throw new ApiException(500, "简介不能为空");
        }
    }
}
