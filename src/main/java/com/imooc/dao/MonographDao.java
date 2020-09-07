package com.imooc.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.Monograph;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MonographDao extends BaseMapper<Monograph> {

    /**
     * 根据offshelf状态查询专栏和作者
     * @param page
     * @param wrapper
     * @return
     */
    List<Monograph> pageFindMonographAuthor(Page<Monograph> page, @Param(Constants.WRAPPER) QueryWrapper<Monograph> wrapper);

    /**
     * 预览专刊
     * @param monograph_id
     * @return
     */
    Monograph previewMonograph(String monograph_id);

    /**
     * 根据文章id查询专刊
     * @param articleId
     * @return
     */
    Monograph findMonographByArticleId(String articleId);

    /**
     * 根据条件查询专刊和章节文章
     * @param monographId
     * @param offShlef
     * @return
     */
    List<Monograph> listAllMonograph(@Param("monographId") String monographId, @Param("offShelf") Integer offShlef);

    
}
