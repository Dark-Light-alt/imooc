package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.Datas;
import com.imooc.utils.common.Pages;

import java.util.List;


/**
 * 资料服务
 */
public interface DatasService extends IService<Datas> {

    /**
     * 根据课程 id 进行删除
     */
    boolean removeByCourseId(String courseId);

    /**
     * 查询某个课程下的所有资料
     *
     * @return
     */
    Page<Datas> findAllByCourseId(Pages pages, String courseId);

    /**
     * 查询某个课程的所有资料
     *
     * @param courseId
     * @return
     */
    List<Datas> findAllByCourseId(String courseId);
}
