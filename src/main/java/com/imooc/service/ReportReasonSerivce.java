package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.ReportReason;

import java.util.List;

/**
 * 举报原因服务
 */
public interface ReportReasonSerivce extends IService<ReportReason> {

    int append(ReportReason reportReason);

    int update(ReportReason reportReason);
}
