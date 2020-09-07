package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.SysNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户的系统提示
 */
@Mapper
public interface SysNoticeDao extends BaseMapper<SysNotice> {

    int append(@Param("title") String title, @Param("content") String content, @Param("customerId") String customerId);
}
