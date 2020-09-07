package com.imooc.crontab;

import com.imooc.service.impl.OrdersServiceImpl;
import com.imooc.service.impl.ReportReviewServiceImpl;
import com.imooc.service.impl.SysNoticeServiceImpl;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务
 */
@Component
@Configurable
@EnableScheduling
public class Crontab {

    // cron 表达式在线生成网站
    // https://cron.qqe2.com/

    @Resource
    private SysNoticeServiceImpl sysNoticeServiceImpl;

    @Resource
    private OrdersServiceImpl ordersServiceImpl;

    @Resource
    private ReportReviewServiceImpl reportReviewServiceImpl;

    /**
     * 清除系统提示信息：清除 3 个月前已读的系统提示信息
     * cron 表达式：每天的凌晨开始执行
     */
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void clearSystemPrompt() {
        sysNoticeServiceImpl.removeByTime();
    }

    /**
     * 关闭超时订单：每 10 分钟执行一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void closeTheOrder() {
        ordersServiceImpl.closeTheOrder();
    }

    /**
     * 课程问题审核：每 60 分钟执行一次
     */
    @Scheduled(cron = "0 0/60 * * * ?")
    public void questionAudit() {
        reportReviewServiceImpl.questionAudit();
    }

    /**
     * 课程问题回答审核：每 60 分钟执行一次
     */
    @Scheduled(cron = "0 0/60 * * * ?")
    public void answerAudit() {
        reportReviewServiceImpl.answerAudit();
    }

    /**
     * 评论审核：每 60 分钟执行一次
     */
    @Scheduled(cron = "0 0/60 * * * ?")
    public void commentAudit() {
        reportReviewServiceImpl.commentAudit();
    }

    /**
     * 提交视频审核任务：每 60 分钟执行一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void submitVideoAuditTask() {
        reportReviewServiceImpl.submitVideoAuditTask();
    }

    /**
     * 获取视频审核结果：每 60 分钟执行一次
     */
    @Scheduled(cron = "0 0/60 * * * ?")
    public void getVideoAuditResult() {
        reportReviewServiceImpl.getVideoAuditResult();
    }
}
