package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.VideoDao;
import com.imooc.entity.Video;
import com.imooc.exception.ApiException;
import com.imooc.service.VideoService;
import com.imooc.utils.common.CommonUtils;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 视频服务实现
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoDao, Video> implements VideoService {

    /**
     * 添加课程视频信息
     *
     * @param video
     * @return
     */
    @Override
    public boolean append(Video video) {

        valid(video);

        return baseMapper.insert(video) != 0;
    }

    /**
     * 根据章节id删除视频
     *
     * @param chapters
     * @return
     */
    @Override
    public boolean removeByChapterId(List<String> chapters) {

        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();

        wrapper.in(Video::getChapterId, chapters);

        return baseMapper.delete(wrapper) != 0;
    }

    /**
     * 根据课程视频 id 删除视频信息
     *
     * @param videoId
     * @return
     */
    @Override
    public boolean removeById(String videoId) {
        return baseMapper.deleteById(videoId) != 0;
    }

    /**
     * 禁用/启用
     *
     * @param videoId  视频 id
     * @param isenable 0 启用 1 禁用
     * @return
     */
    @Override
    public int changeIsenable(String videoId, Integer isenable) {

        LambdaUpdateWrapper<Video> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Video::getVideoIsenable, isenable);

        wrapper.eq(Video::getVideoId, videoId);

        return baseMapper.update(null, wrapper);
    }

    @Override
    public boolean update(Video video) {
        // 验证
        valid(video);

        return baseMapper.updateById(video) != 0;
    }

    @Override
    public Video findById(String videoId) {

        return baseMapper.selectById(videoId);
    }

    /**
     * 根据章节查询到所属的课程视频
     *
     * @param pages
     * @param chapterId 章节 id
     * @return
     */
    @Override
    public Page<Video> findAllByChapter(Pages pages, String chapterId) {

        Page<Video> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        LambdaQueryWrapper<Video> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Video::getChapterId, chapterId);

        String videoName = pages.getSearchs().get("videoName");

        if (CommonUtils.isNotEmpty(videoName)) {
            wrapper.like(Video::getVideoName, videoName);
        }

        wrapper.orderByAsc(Video::getCreateTime);

        return baseMapper.selectPage(page, wrapper);
    }


    private void valid(Video video) {

        if (!CommonUtils.isNotEmpty(video.getVideoName())) {
            throw new ApiException(500, "视频课程名不能为空");
        }

        if (!CommonUtils.isNotEmpty(video.getVideoUrl())) {
            throw new ApiException(500, "视频必须上传");
        }
    }
}
