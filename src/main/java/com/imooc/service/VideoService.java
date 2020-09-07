package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Video;
import com.imooc.utils.common.Pages;

import java.util.List;

/**
 * 视频服务
 */
public interface VideoService extends IService<Video> {

    /**
     * 添加视频信息
     *
     * @param video
     * @return
     */
    boolean append(Video video);

    /**
     * 根据章节 id 删除所有的视频
     *
     * @param chapters
     * @return
     */
    boolean removeByChapterId(List<String> chapters);

    /**
     * 根据视频 id 删除视频
     *
     * @param videoId
     * @return
     */
    boolean removeById(String videoId);

    /**
     * 禁用/启用
     * @param videoId 视频 id
     * @param isenable 0 启用 1 禁用
     * @return
     */
    int changeIsenable(String videoId, Integer isenable);

    /**
     * 修改视频信息
     *
     * @param video
     * @return
     */
    boolean update(Video video);

    /**
     * 根据视频 id 查询视频信息
     *
     * @param videoId
     * @return
     */
    Video findById(String videoId);

    /**
     * 根据章节 id 查询所属视频
     *
     * @param chapterId 章节 id
     * @return
     */
    Page<Video> findAllByChapter(Pages pages, String chapterId);
}
