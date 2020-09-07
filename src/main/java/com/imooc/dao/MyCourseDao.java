package com.imooc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.MyCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyCourseDao extends BaseMapper<MyCourse> {

    /**
     * 查询用户的课程
     *
     * @param customerId 用户 id
     * @param isfree     0 免费 1 付费 null 全部
     * @return
     */
    List<Map<String, Object>> findByCustomer(@Param("customerId") String customerId, @Param("isfree") Integer isfree);
}
