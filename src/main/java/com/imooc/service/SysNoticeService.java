package com.imooc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.entity.SysNotice;

import java.util.List;

/**
 * 用户系统提示服务
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 根据用户 id 查询全部系统提示
     *
     * @param customerId 员工 id
     * @return
     */
    List<SysNotice> findAllByCustomerId(String customerId);

    /**
     * 根据 id 标记为已读
     *
     * @param sysNoticeId 提示 id
     * @return
     */
    int markRead(String sysNoticeId);

    /**
     * 标记该用户的所有信息为已读
     *
     * @param customerId
     * @return
     */
    int markReadAll(String customerId);

    /**
     * 根据提示 id 删除
     *
     * @param sysNoticeId
     * @return
     */
    int removeById(String sysNoticeId);

    /**
     * 根据时间删除已经标记为已读的提示
     * 时间为 3 个月前
     *
     * @return
     */
    int removeByTime();

    boolean append(SysNotice sysNotice);
}
