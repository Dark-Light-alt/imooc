package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Chapter;
import com.imooc.utils.common.Pages;

import java.util.List;

/**
 * 章节实现
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 添加章节
     *
     * @param chapter
     * @return
     */
    boolean append(Chapter chapter);

    /**
     * 根据章节 id 删除对应的章节数据和视频
     *
     * @param chapterId
     * @return
     */
    boolean removeChapterAndVideo(String chapterId);

    /**
     * 根据数据源 id 删除对应的所有章节
     *
     * @param resource 数据源 id
     * @return
     */
    boolean removeChapterByResource(String resource);

    /**
     * 修改章节信息
     *
     * @param chapter
     * @return
     */
    boolean update(Chapter chapter);

    /**
     * 根据数据源 id 查询到对应的章节 id
     *
     * @param resource
     * @return
     */
    List<String> findChapterIdByResource(String resource);

    /**
     * 根据 id 查询章节信息
     *
     * @param chapterId 章节 id
     * @return
     */
    Chapter findById(String chapterId);

    /**
     * 查询某个章节源的所有章节
     *
     * @param pages
     * @param chapterResource 章节源：课程 id /专刊 id
     * @return
     */
    Page<Chapter> findChapter(Pages pages, String chapterResource);
}
