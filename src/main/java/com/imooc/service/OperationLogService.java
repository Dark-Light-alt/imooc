package com.imooc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.OperationLog;
import com.imooc.utils.common.Pages;

/**
 * 操作日志服务
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 添加日志信息
     *
     * @param operationLog
     * @return
     */
    boolean append(OperationLog operationLog);

    /**
     * 分页查询日志信息
     *
     * @param pages
     * @return
     */
    Page<OperationLog> findAll(Pages pages);
}
