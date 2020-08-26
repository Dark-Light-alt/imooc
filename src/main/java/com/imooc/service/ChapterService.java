package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Chapter;

public interface ChapterService extends IService<Chapter> {
    /**
     * 添加章节
     * @param chapter
     * @return
     */
    boolean append(Chapter chapter);
}
