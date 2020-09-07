package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.ReportReviewDao;
import com.imooc.entity.*;
import com.imooc.exception.ApiException;
import com.imooc.service.ReportReviewService;
import com.imooc.utils.aliyun.green.text.TextDetectionServiceImpl;
import com.imooc.utils.aliyun.green.video.VideoDetectionService;
import com.imooc.utils.aliyun.green.video.VideoDetectionServiceImpl;
import com.imooc.utils.common.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 举报审核服务实现
 */
@Service
public class ReportReviewServiceImpl extends ServiceImpl<ReportReviewDao, ReportReview> implements ReportReviewService {

    // 文字检测服务
    @Resource
    private TextDetectionServiceImpl textDetectionServiceImpl;

    // 视频内容审核
    @Resource
    private VideoDetectionServiceImpl videoDetectionServiceImpl;

    // 系统通知服务
    @Resource
    private SysNoticeServiceImpl sysNoticeServiceImpl;

    // 举报维度服务
    @Resource
    private ReportReasonSerivceImpl reportReasonSerivceImpl;

    // 课程问题服务
    @Resource
    private QuestionServiceImpl questionServiceImpl;

    // 问题回答服务
    @Resource
    private AnswerServiceImpl answerServiceImpl;

    // 评论服务
    @Resource
    private CommentsServiceImpl commentsServiceImpl;

    // 课程视频服务
    @Resource
    private VideoServiceImpl videoServiceImpl;

    /**
     * 添加举报信息
     *
     * @param reportReview
     * @return
     */
    @Override
    public int append(ReportReview reportReview) {

        if (!CommonUtils.isNotEmpty(reportReview.getReportReasonId())) {
            throw new ApiException(500, "举报原因不能为空");
        }

        if (!CommonUtils.isNotEmpty(reportReview.getReportDescribe())) {
            throw new ApiException(500, "举报描述不能为空");
        }

        return baseMapper.insert(reportReview);
    }

    /**
     * 根据举报类型查询出未审核的举报信息
     *
     * @param reportType 举报类型：0 视频 1 评论 2 问题 3 回答
     * @param status     审核状态：0 未审核 1 待审核 2 已审核
     * @return
     */
    @Override
    public List<ReportReview> findByReportType(Integer reportType, Integer status) {

        LambdaQueryWrapper<ReportReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReportReview::getReportType, reportType);
        wrapper.eq(ReportReview::getReviewStatus, status);

        return baseMapper.selectList(wrapper);
    }

    /**
     * 审核结果
     *
     * @param reportReviewId 审核 id
     * @param status         状态 0 未审核 1 待审核 2 已审核
     * @param result         审核结果
     * @param time           审核时间
     * @return
     */
    @Override
    public int audit(String reportReviewId, Integer status, String result, Date time) {

        LambdaUpdateWrapper<ReportReview> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(ReportReview::getReviewStatus, status);
        wrapper.set(ReportReview::getReviewResult, result);
        wrapper.set(ReportReview::getReviewTime, time);

        wrapper.eq(ReportReview::getReportReviewId, reportReviewId);

        return baseMapper.update(null, wrapper);
    }

    /**
     * 设置视频审核的任务 id
     *
     * @param reportReviewId 审核 id
     * @param status         状态 0 未审核 1 待审核 2 已审核
     * @param taskId         任务 id
     * @return
     */
    @Override
    public int setTaskId(String reportReviewId, Integer status, String taskId) {

        LambdaUpdateWrapper<ReportReview> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(ReportReview::getReviewStatus, status);
        wrapper.set(ReportReview::getTaskId, taskId);

        wrapper.eq(ReportReview::getReportReviewId, reportReviewId);

        return baseMapper.update(null, wrapper);
    }

    /**
     * 课程问题审核
     */
    @Transactional
    public void questionAudit() {

        // 举报者通知
        SysNotice reportPerson = new SysNotice();
        reportPerson.setTitle("举报审核");
        // 被举报者通知
        SysNotice personBeReport = new SysNotice();
        personBeReport.setTitle("举报审核");

        // 查询出课程问题的举报信息
        List<ReportReview> reportReviews = findByReportType(2, 0);

        reportReviews.forEach(reportReview -> {

            // 查询出课程问题
            Question question = questionServiceImpl.getById(reportReview.getResourceBeReport());

            // 通知举报人
            reportPerson.setCustomerId(reportReview.getReportPerson());

            // 问题未被禁用时进行审核
            if (question.getQuestionIsenable() == 0) {

                // 查询出举报维度
                ReportReason reportReason = reportReasonSerivceImpl.getById(reportReview.getReportReasonId());

                // 查看举报维度是否存在
                if (reportReason != null) {

                    // 对课程问题标题和内容进行拼接
                    String content = question.getTitle() + question.getContent();

                    // 文字内容审核
                    boolean result = textDetectionServiceImpl.detection(content, reportReason.getDimension());

                    if (result) {
                        // 通知举报人
                        reportPerson.setContent("尊敬的用户，您举报的课程问题 < " + question.getTitle() + " > 经审核存在内容违规！我们已经对其进行警告处理，感谢您对慕课的支持");
                        sysNoticeServiceImpl.append(reportPerson);

                        // 通知被举报人
                        personBeReport.setCustomerId(reportReview.getPersonBeReport());
                        personBeReport.setContent("您的课程问题 < " + question.getTitle() + "> 经审核存在内容违规！请注意您的个人行为！！！");
                        sysNoticeServiceImpl.append(personBeReport);

                        // 禁用课程问题
                        questionServiceImpl.changeIsenable(question.getQuestionId(), 1);

                        audit(reportReview.getReportReviewId(), 2, "审核通过", new Date());
                    } else {
                        audit(reportReview.getReportReviewId(), 2, "审核未通过", new Date());
                    }

                } else {
                    // 通知举报人
                    reportPerson.setContent("尊敬的用户，您举报的课程问题 < " + question.getTitle() + " > 的检测维度不存在！");
                    sysNoticeServiceImpl.append(reportPerson);
                    audit(reportReview.getReportReviewId(), 2, "检测维度不存在", new Date());
                }
            } else {
                // 通知举报人
                reportPerson.setContent("尊敬的用户，您举报的课程问题 < " + question.getTitle() + " > 已经被删除！感谢您对慕课的支持！！！");
                sysNoticeServiceImpl.append(reportPerson);
                audit(reportReview.getReportReviewId(), 2, "举报内容已经被禁用", new Date());
            }
        });
    }

    /**
     * 问题回答审核
     */
    @Transactional
    public void answerAudit() {

        // 举报者通知
        SysNotice reportPerson = new SysNotice();
        reportPerson.setTitle("举报审核");
        // 被举报者通知
        SysNotice personBeReport = new SysNotice();
        personBeReport.setTitle("举报审核");

        // 查询出问题回答的举报信息
        List<ReportReview> reportReviews = findByReportType(3, 0);

        reportReviews.forEach(reportReview -> {

            // 查询出问题回答信息
            Answer answer = answerServiceImpl.getById(reportReview.getResourceBeReport());

            // 通知举报人
            reportPerson.setCustomerId(reportReview.getReportPerson());

            // 回答未被禁用时进行审核
            if (answer.getAnswerIsenable() == 0) {

                // 查询出举报维度
                ReportReason reportReason = reportReasonSerivceImpl.getById(reportReview.getReportReasonId());

                // 查看举报维度是否存在
                if (reportReason != null) {

                    // 文字内容审核
                    boolean result = textDetectionServiceImpl.detection(answer.getContent(), reportReason.getDimension());

                    if (result) {

                        // 通知举报人
                        reportPerson.setContent("尊敬的用户，您举报的问题回答 < " + answer.getContent() + " > 经审核存在内容违规！我们已经对其进行警告处理，感谢您对慕课的支持");
                        sysNoticeServiceImpl.append(reportPerson);

                        // 通知被举报人
                        personBeReport.setCustomerId(reportReview.getPersonBeReport());
                        personBeReport.setContent("您的问题回答 < " + answer.getContent() + "> 经审核存在内容违规！请注意您的个人行为！！！");
                        sysNoticeServiceImpl.append(personBeReport);

                        // 禁用问题回答
                        answerServiceImpl.changeIsenable(answer.getAnswerId(), 1);

                        audit(reportReview.getReportReviewId(), 2, "审核通过", new Date());
                    } else {
                        audit(reportReview.getReportReviewId(), 2, "审核未通过", new Date());
                    }

                } else {
                    // 通知举报人
                    reportPerson.setContent("尊敬的用户，您举报的问题回答 < " + answer.getContent() + " > 的检测维度不存在！");
                    sysNoticeServiceImpl.append(reportPerson);
                    audit(reportReview.getReportReviewId(), 2, "检测维度不存在", new Date());
                }

            } else {
                // 通知举报人
                reportPerson.setContent("尊敬的用户，您举报的问题回答 < " + answer.getContent() + " > 已经被删除！感谢您对慕课的支持！！！");
                sysNoticeServiceImpl.append(reportPerson);
                audit(reportReview.getReportReviewId(), 2, "举报内容已经被禁用", new Date());
            }
        });
    }

    /**
     * 评论审核
     */
    @Transactional
    public void commentAudit() {

        // 举报者通知
        SysNotice reportPerson = new SysNotice();
        reportPerson.setTitle("举报审核");
        // 被举报者通知
        SysNotice personBeReport = new SysNotice();
        personBeReport.setTitle("举报审核");

        // 查询出评论的举报信息
        List<ReportReview> reportReviews = findByReportType(1, 0);

        reportReviews.forEach(reportReview -> {

            // 查询出被举报的评论
            Comments comment = commentsServiceImpl.getById(reportReview.getResourceBeReport());

            // 通知举报人
            reportPerson.setCustomerId(reportReview.getReportPerson());

            // 评论未禁用时进行审核
            if (comment.getCommentStatus() == 0) {

                // 查询出举报维度
                ReportReason reportReason = reportReasonSerivceImpl.getById(reportReview.getReportReasonId());

                // 查看举报维度是否存在
                if (reportReason != null) {

                    // 文字内容审核
                    boolean result = textDetectionServiceImpl.detection(comment.getContent(), reportReason.getDimension());

                    if (result) {

                        // 通知举报人
                        reportPerson.setContent("尊敬的用户，您举报的评论 < " + comment.getContent() + " > 经审核存在内容违规！我们已经对其进行警告处理，感谢您对慕课的支持");
                        sysNoticeServiceImpl.append(reportPerson);

                        // 通知被举报人
                        personBeReport.setCustomerId(reportReview.getPersonBeReport());
                        personBeReport.setContent("您的评论 < " + comment.getContent() + "> 经审核存在内容违规！请注意您的个人行为！！！");
                        sysNoticeServiceImpl.append(personBeReport);

                        // 禁用评论
                        commentsServiceImpl.changeStatus(reportReview.getResourceBeReport(), 1);

                        audit(reportReview.getReportReviewId(), 2, "审核通过", new Date());
                    } else {
                        audit(reportReview.getReportReviewId(), 2, "审核未通过", new Date());
                    }

                } else {
                    // 通知举报人
                    reportPerson.setContent("尊敬的用户，您举报的评论 < " + comment.getContent() + " > 的检测维度不存在！");
                    sysNoticeServiceImpl.append(reportPerson);
                    audit(reportReview.getReportReviewId(), 2, "检测维度不存在", new Date());
                }

            } else {
                // 通知举报人
                reportPerson.setContent("尊敬的用户，您举报的评论 < " + comment.getContent() + " > 已经被删除！感谢您对慕课的支持！！！");
                sysNoticeServiceImpl.append(reportPerson);
                audit(reportReview.getReportReviewId(), 2, "举报内容已经被禁用", new Date());
            }
        });
    }


    /**
     * 提交视频审核任务
     */
    public void submitVideoAuditTask() {

        // 举报者通知
        SysNotice reportPerson = new SysNotice();
        reportPerson.setTitle("举报审核");

        // 查询出未审核的视频举报信息
        List<ReportReview> reportReviews = findByReportType(0, 0);

        reportReviews.forEach(reportReview -> {

            // 查询出被举报视频
            Video video = videoServiceImpl.getById(reportReview.getResourceBeReport());

            // 通知举报人
            reportPerson.setCustomerId(reportReview.getReportPerson());

            // 当课程视频未被禁用时进行审核
            if (video.getVideoIsenable() == 0) {

                // 查询出检测维度
                ReportReason reportReason = reportReasonSerivceImpl.getById(reportReview.getReportReasonId());

                // 判断检测维度是否存在，目前视频内容审核只支持 涉恐 和 鉴黄
                if (null != reportPerson && VideoDetectionService.PORN.equals(reportReason.getDimension()) || VideoDetectionService.TERRORISM.equals(reportReason.getDimension())) {

                    // 提交审核任务
                    String taskId = videoDetectionServiceImpl.submitTask(reportReason.getDimension(), reportReview.getReportReviewId(), video.getVideoUrl());

                    // 更改审核信息，并设置 task id
                    setTaskId(reportReview.getReportReviewId(), 1, taskId);

                } else {
                    // 通知举报人
                    reportPerson.setContent("尊敬的用户，您举报的课程视频 < " + video.getVideoName() + " > 的检测维度不存在！");
                    sysNoticeServiceImpl.append(reportPerson);
                    audit(reportReview.getReportReviewId(), 2, "检测维度不存在", new Date());
                }

            } else {
                // 通知举报人
                reportPerson.setContent("尊敬的用户，您举报的课程视频 < " + video.getVideoName() + " > 已经被删除！感谢您对慕课的支持！！！");
                sysNoticeServiceImpl.append(reportPerson);
                audit(reportReview.getReportReviewId(), 2, "课程视频已经被禁用", new Date());
            }
        });
    }

    // 获取视频审核结果
    public void getVideoAuditResult() {

        // 举报者通知
        SysNotice reportPerson = new SysNotice();
        reportPerson.setTitle("举报审核");

        // 查询出待审核的视频举报信息
        List<ReportReview> reportReviews = findByReportType(0, 1);

        reportReviews.forEach(reportReview -> {

            // 查询出被举报视频
            Video video = videoServiceImpl.getById(reportReview.getResourceBeReport());

            // 通知举报人
            reportPerson.setCustomerId(reportReview.getReportPerson());

            // 当课程视频未被禁用时进行获取
            if (video.getVideoIsenable() == 0) {

                // 获取到任务 id
                String taskId = reportReview.getTaskId();

                // 根据任务 id 查询到审核结果，null 正在审核 true 违规 false 正常
                Boolean result = videoDetectionServiceImpl.getResult(taskId);

                if (null != result) {
                    if (result) {

                        // 通知举报人
                        reportPerson.setContent("尊敬的用户，您举报课程视频 < " + video.getVideoName() + " > 经审核存在内容违规！我们已经对其进行下架处理，感谢您对慕课的支持");
                        sysNoticeServiceImpl.append(reportPerson);

                        // 禁用课程视频
                        videoServiceImpl.changeIsenable(video.getVideoId(), 1);

                        audit(reportReview.getReportReviewId(), 2, "审核通过", new Date());
                    } else {
                        audit(reportReview.getReportReviewId(), 2, "审核未通过", new Date());
                    }
                }

            } else {
                // 通知举报人
                reportPerson.setContent("尊敬的用户，您举报的课程视频 < " + video.getVideoName() + " > 已经被删除！感谢您对慕课的支持！！！");
                sysNoticeServiceImpl.append(reportPerson);
                audit(reportReview.getReportReviewId(), 2, "课程视频已经被禁用", new Date());
            }
        });

    }
}
