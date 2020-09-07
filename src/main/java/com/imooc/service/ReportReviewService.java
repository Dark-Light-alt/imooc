package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.ReportReview;

import java.util.Date;
import java.util.List;

/**
 * 举报审核服务
 */
public interface ReportReviewService extends IService<ReportReview> {

    /**
     * 添加举报信息
     *
     * @param reportReview
     * @return
     */
    int append(ReportReview reportReview);

    /**
     * 根据举报类型查询出未审核的举报信息
     *
     * @param reportType 举报类型：0 视频 1 评论 2 问题 3 回答
     * @param status     审核状态：0 未审核 1 待审核 2 已审核
     * @return
     */
    List<ReportReview> findByReportType(Integer reportType, Integer status);

    /**
     * 审核结果
     *
     * @param reportReviewId 审核 id
     * @param status         状态 0 未审核 1 待审核 2 已审核
     * @param result         审核结果
     * @param time           审核时间
     * @return
     */
    int audit(String reportReviewId, Integer status, String result, Date time);

    /**
     * 设置视频审核的任务 id
     *
     * @param reportReviewId 审核 id
     * @param status         状态 0 未审核 1 待审核 2 已审核
     * @param taskId         任务 id
     * @return
     */
    int setTaskId(String reportReviewId, Integer status, String taskId);
}
