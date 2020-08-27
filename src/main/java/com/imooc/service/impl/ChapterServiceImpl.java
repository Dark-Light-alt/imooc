package com.imooc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.ChapterDao;
import com.imooc.entity.Chapter;
import com.imooc.exception.ApiException;
import com.imooc.service.ChapterService;
import com.imooc.utils.common.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
