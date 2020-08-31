package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Chapter;
import com.imooc.utils.common.Pages;

import java.util.List;

public interface ChapterService extends IService<Chapter> {

    /**
     * 添加章节
     *
     * @param chapter
     * @return
     */
    boolean append(Chapter chapter);

    boolean removeChapterAndVideo(String chapterId);

    boolean removeChapterByResource(String resource);

    /**
     * 修改章节信息
     *
     * @param chapter
     * @return
     */
    boolean update(Chapter chapter);

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

    /**
     * 根据专刊分页查询文章和章节
     * @return
     */
    List<Chapter> findACByMid(String monographId);

    /**
     * 根据id删除章节
     * @param chapterId
     * @return
     */
    boolean deleteChatsAndArts(String chapterId);
}
