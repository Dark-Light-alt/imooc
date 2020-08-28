package com.imooc.utils.aliyun.green.video;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.green.model.v20180509.VideoAsyncScanRequest;
import com.aliyuncs.green.model.v20180509.VideoAsyncScanResultsRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 视频检测服务
 */
public interface VideoDetectionService {

    // 涉恐内容检测
    String TERRORISM = "terrorism";

    // 鉴黄内容检测
    String PORN = "porn";

    /**
     * 构建视频检测客户端
     *
     * @param regionId
     * @param accessKeyId
     * @param secret
     * @return
     */
    default DefaultAcsClient buildClient(String regionId, String accessKeyId, String secret) {
        // 创建配置文件对象
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);

        // 添加目的
        DefaultProfile.addEndpoint(regionId, "Green", "green.cn-shanghai.aliyuncs.com");

        // 构建客户端
        DefaultAcsClient client = new DefaultAcsClient(profile);

        return client;
    }

    /**
     * 设置检测任务
     *
     * @param scenes 识别场景
     * @param dataId 数据 id
     * @param url    视频路径
     * @return
     */
    default VideoAsyncScanRequest getRequest(String scenes, String dataId, String url) {

        // 构建视频扫描请求
        VideoAsyncScanRequest request = new VideoAsyncScanRequest();

        // 设置接收格式
        request.setSysAcceptFormat(FormatType.JSON);

        // 设置请求方式
        request.setSysMethod(MethodType.POST);

        // 设置检测任务
        JSONObject tasks = new JSONObject();
        tasks.put("dataId", dataId);
        tasks.put("url", url);
        JSONObject requestData = new JSONObject();
        requestData.put("scenes", Arrays.asList(scenes));
        requestData.put("tasks", Arrays.asList(tasks));

        try {
            // 设置请求体
            request.setHttpContent(requestData.toString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 设置连接超时时间
        request.setSysConnectTimeout(3000);
        // 设置读取超时时间
        request.setSysReadTimeout(6000);

        return request;
    }

    /**
     * 提交检测任务
     *
     * @param response
     * @return 任务 id：taskId 用于查询检测结果
     */
    default String submit(HttpResponse response) {

        String taskId = null;

        if (null != response && response.isSuccess()) {

            JSONObject jsonObject = null;
            try {
                jsonObject = JSON.parseObject(new String(response.getHttpContent(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return null;
            }

            if (200 == jsonObject.getInteger("code")) {
                JSONArray datas = jsonObject.getJSONArray("data");
                for (Object data : datas) {
                    if (200 == ((JSONObject) data).getInteger("code")) {
                        taskId = ((JSONObject) data).getString("taskId");
                    } else {
                        System.out.println("未提交审核");
                        return null;
                    }
                }
            }
        }

        return taskId;
    }

    /**
     * 获取查询结果请求
     *
     * @param taskId 检测任务 id
     * @return
     */
    default VideoAsyncScanResultsRequest getResultRequest(String taskId) {

        // 构建视频扫描结果请求
        VideoAsyncScanResultsRequest request = new VideoAsyncScanResultsRequest();

        // 设置接收格式
        request.setSysAcceptFormat(FormatType.JSON);

        // 封装任务 id
        JSONArray tasks = new JSONArray();
        tasks.add(taskId);

        try {
            // 设置请求体
            request.setHttpContent(JSON.toJSONString(tasks).getBytes("UTF-8"), "UTF-8", FormatType.JSON);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 设置请求连接超时时间
        request.setSysConnectTimeout(3000);

        // 设置读取超时时间
        request.setSysReadTimeout(6000);

        return request;
    }

    /**
     * 提交检测任务
     *
     * @param scenes 识别场景
     * @param dataId 数据 id
     * @param url    视频路径
     * @return
     */
    String submitTask(String scenes, String dataId, String url);

    /**
     * 获取检测结果
     * 280：未审核完成
     * 200：审核完成
     *
     * @param taskId
     * @return
     */
    Boolean getResult(String taskId);
}
