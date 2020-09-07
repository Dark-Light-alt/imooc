package com.imooc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.ReportReasonDao;
import com.imooc.entity.ReportReason;
import com.imooc.exception.ApiException;
import com.imooc.service.ReportReasonSerivce;
import com.imooc.utils.common.CommonUtils;
import org.springframework.stereotype.Service;

/**
 * 举报原因服务实现
 */
@Service
public class ReportReasonSerivceImpl extends ServiceImpl<ReportReasonDao, ReportReason> implements ReportReasonSerivce {
    
    @Override
    public int append(ReportReason reportReason) {

        verify(reportReason);

        return baseMapper.insert(reportReason);
    }

    @Override
    public int update(ReportReason reportReason) {

        verify(reportReason);

        return baseMapper.updateById(reportReason);
    }

    private void verify(ReportReason reportReason) {
        if (!CommonUtils.isNotEmpty(reportReason.getReportReasonName())) {
            throw new ApiException(500, "举报原因名不能为空");
        }

        if (!CommonUtils.isNotEmpty(reportReason.getDimension())) {
            throw new ApiException(500, "检测维度不能为空");
        }
    }
}
