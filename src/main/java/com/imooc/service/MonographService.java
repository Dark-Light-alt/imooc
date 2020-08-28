package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Monograph;
import com.imooc.utils.common.Pages;

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
}
