package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.OperationLogDao;
import com.imooc.entity.OperationLog;
import com.imooc.service.OperationLogService;
import com.imooc.utils.common.Pages;
import org.springframework.stereotype.Service;

/**
 * 操作日志服务实现
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao, OperationLog> implements OperationLogService {

    @Override
    public boolean append(OperationLog operationLog) {
        return baseMapper.insert(operationLog) != 0;
    }

    @Override
    public Page<OperationLog> findAll(Pages pages) {

        Page<OperationLog> page = new Page<>(pages.getCurrentPage(), pages.getPageSize());

        return page.setRecords(baseMapper.findAll(page, lambdaQueryWrapper(pages)));
    }

    private LambdaQueryWrapper<OperationLog> lambdaQueryWrapper(Pages pages) {

        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();

        wrapper.orderByDesc(OperationLog::getCreateTime);

        return wrapper;
    }
}
