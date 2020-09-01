package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.dao.SysNoticeDao;
import com.imooc.entity.SysNotice;
import com.imooc.service.SysNoticeService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 系统提示服务实现
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeDao, SysNotice> implements SysNoticeService {


    /**
     * 添加系统提示
     *
     * @param sysNotice
     * @return
     */
    @Override
    public boolean append(SysNotice sysNotice) {
        return baseMapper.insert(sysNotice) != 0;
    }

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

    /**
     * 删除 3 个月之前已读的系统信息
     *
     * @return
     */
    @Override
    public int removeByTime() {

        // 获取日历实例
        Calendar calender = Calendar.getInstance();
        // 设置当前时间
        calender.setTime(new Date());
        // 设置为前 3 个月
        calender.add(Calendar.MONTH, -3);
        // 获取 3 个月前的时间
        Date time = calender.getTime();

        LambdaQueryWrapper<SysNotice> wrapper = new LambdaQueryWrapper<>();

        // 小于或等于 3 个月前的时间
        wrapper.le(SysNotice::getCreateTime, time);

        wrapper.eq(SysNotice::getIsreading, 1);

        return baseMapper.delete(wrapper);
    }
}
