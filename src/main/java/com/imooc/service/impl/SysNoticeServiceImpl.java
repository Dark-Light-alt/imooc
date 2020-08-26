package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.SysNoticeDao;
import com.imooc.entity.SysNotice;
import com.imooc.service.SysNoticeService;

import java.util.List;

/**
 * 系统提示服务实现
 */
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeDao, SysNotice> implements SysNoticeService {

    /**
     * 根据用户 id 查询所属的系统提示
     *
     * @param customerId 员工 id
     * @return
     */
    @Override
    public List<SysNotice> findAllByCustomerId(String customerId) {

        LambdaQueryWrapper<SysNotice> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(SysNotice::getCustomerId, customerId);

        wrapper.orderByDesc(SysNotice::getCreateTime);

        return baseMapper.selectList(wrapper);
    }

    /**
     * 将某个提示标记为已读
     *
     * @param sysNoticeId 提示 id
     * @return
     */
    @Override
    public int markRead(String sysNoticeId) {

        SysNotice sysNotice = new SysNotice();
        sysNotice.setIsreading(1);

        LambdaUpdateWrapper<SysNotice> wrapper = new LambdaUpdateWrapper<>();

        wrapper.eq(SysNotice::getSysNoticeId, sysNoticeId);

        return baseMapper.update(sysNotice, wrapper);
    }

    @Override
    public int markReadAll(String customerId) {

        SysNotice sysNotice = new SysNotice();
        sysNotice.setIsreading(1);

        LambdaUpdateWrapper<SysNotice> wrapper = new LambdaUpdateWrapper<>();

        wrapper.eq(SysNotice::getCustomerId, customerId);

        return baseMapper.update(sysNotice, wrapper);
    }

    @Override
    public int removeById(String sysNoticeId) {
        return baseMapper.deleteById(sysNoticeId);
    }

    @Override
    public int removeByTime() {

        LambdaQueryWrapper<SysNotice> wrapper = new LambdaQueryWrapper<>();

        return 0;
    }
}
