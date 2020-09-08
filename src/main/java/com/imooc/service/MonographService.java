package com.imooc.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Monograph;
import com.imooc.utils.common.Pages;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MonographService extends IService<Monograph> {


    /**
     * 分页查询用户的所有专刊
     * @param pages
     * @return
     */
    Page<Monograph> findAllByEmployeeId(Pages pages,String employeeId);

    /**
     * 修改
     * @param monograph
     * @return
     */
    boolean update(Monograph monograph);

    /**
     * 根据主键查询
     * @param monographId
     * @return
     */
    Monograph findById(String monographId);

    /**
     * 修改专栏状态
     * @param monographId
     * @param status
     * @return
     */
    boolean updateOffShelf(String monographId,Integer status);

    /**
     * 添加专栏
     * @param monograph
     * @return
     */
    boolean append(Monograph monograph);


    /**
     * 分页关联查询专栏和作者
     * @param pages
     * @return
     */
    Page<Monograph> pageFindMonographAuthor(Pages pages);


    /**
     * 删除专栏
     * @param monographId
     * @return
     */
    int delete(String monographId);

    /**
     * 预览专刊
     * @param monographId
     * @return
     */
    Monograph previewMonograph(String monographId);


    /**
     * 根据文章查询专刊
     * @param articleId
     * @return
     */
    Monograph findMonographByArticleId(String articleId);

    /**
     * 根据条件查询所有专刊和章节文章
     * @param monographId
     * @param offShlef
     * @return
     */
    List<Monograph> listAllMonograph(String monographId,Integer offShlef);
}
