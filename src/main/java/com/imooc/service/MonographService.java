package com.imooc.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Monograph;
import com.imooc.utils.common.Pages;

public interface MonographService extends IService<Monograph> {

    /**
     * 分页查询所有
     * @param pages
     * @return
     */
    Page<Monograph> findAll(Pages pages);

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
     * 下架专栏
     * @param monographId
     * @return
     */
    boolean soldOut(String monographId);

    /**
     * 添加专栏
     * @param monograph
     * @return
     */
    boolean append(Monograph monograph);


    /**
     * 分页查询专栏和章节
     * @param pages
     * @return
     */
    Page<Monograph> pageFindMonograph(Pages pages);
}
