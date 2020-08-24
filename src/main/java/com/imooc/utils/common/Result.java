package com.imooc.utils.common;

import lombok.Data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 前后端交互统一响应协议
 */
@Data
public class Result {

    // 数据头：存放消息和状态信息
    private Map<String, Object> meta = new HashMap<>();

    // 数据体：存放数据信息
    private Map<String, Object> data = new HashMap<>();

    // 分页数据
    private Pages pages;

    public Result() {
    }

    /**
     * 向数据头中状态信息
     *
     * @param key
     * @param value
     */
    public void putMeta(String key, Object value) {
        this.getMeta().put(key, value);
    }

    /**
     * 向数据体中添加数据
     *
     * @param key
     * @param value
     */
    public void putData(String key, Object value) {
        this.getData().put(key, value);
    }

    /**
     * 访问成功时，返回数据
     *
     * @param code 状态码
     * @param msg  消息
     */
    public void success(Integer code, String msg) {
        this.putMeta("access", Boolean.TRUE);
        this.putMeta("code", code);
        this.putMeta("msg", msg);
        this.putMeta("timestamp", new Timestamp(System.currentTimeMillis()));
    }

    /**
     * 访问失败时，返回数据
     *
     * @param code 状态码
     * @param msg  消息
     */
    public void error(Integer code, String msg) {
        this.putMeta("access", Boolean.FALSE);
        this.putMeta("code", code);
        this.putMeta("msg", msg);
        this.putMeta("timestamp", new Timestamp(System.currentTimeMillis()));
    }
}
