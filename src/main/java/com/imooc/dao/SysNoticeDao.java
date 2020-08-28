package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.SysNotice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户的系统提示
 */
@Mapper
public interface SysNoticeDao extends BaseMapper<SysNotice> {
}
