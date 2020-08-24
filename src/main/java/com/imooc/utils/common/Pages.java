package com.imooc.utils.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页数据
 */
@Data
public class Pages {

    //当前页
    private Integer currentPage = 1;

    //每页多少条
    private Integer pageSize = 7;

    //总共多少条数据
    private Long total;

    //尾页
    private Long lastPage;

    //搜索内容
    private Map<String, String> searchs = new HashMap<>();

    //是否启用
    private boolean flag = true;
}
