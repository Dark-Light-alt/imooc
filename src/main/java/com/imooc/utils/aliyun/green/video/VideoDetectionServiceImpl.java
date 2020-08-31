package com.imooc.utils.aliyun.green.video;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.green.model.v20180509.VideoAsyncScanRequest;
import com.aliyuncs.green.model.v20180509.VideoAsyncScanResultsRequest;
import com.aliyuncs.http.HttpResponse;
import com.imooc.utils.aliyun.BaseConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 视频检测服务实现
 */
@Component
public class VideoDetectionServiceImpl implements VideoDetectionService {

    @Resource
    private BaseConfig baseConfig;

    /**
     * 提交审核任务
     *
     * @param scenes 识别场景
     * @param dataId 数据 id
     * @param url    视频路径
     * @return taskId 用于查询检测结果
     */
    @Override
    public String submitTask(String scenes, String dataId, String url) {

        // 构建视频检测客户端
        DefaultAcsClient client = buildClient(baseConfig.getRegionId(), baseConfig.getAccessKeyId(), baseConfig.getSecret());

        // 构建视频扫描请求
        VideoAsyncScanRequest request = getRequest(scenes, dataId, url);

        HttpResponse httpResponse = null;

        try {
            httpResponse = client.doAction(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        // 提交审核任务
        String taskId = submit(httpResponse);

        return taskId;
    }

    /**
     * 根据 taskId 查询检测结果
     *
     * @param taskId 任务 id
     * @return 检测结果：true 违规 / false 正常 / null 正在审核
     */
    @Override
    public Boolean getResult(String taskId) {

        // 构建视频检测客户端
        DefaultAcsClient client = buildClient(baseConfig.getRegionId(), baseConfig.getAccessKeyId(), baseConfig.getSecret());

        // 构建视频扫描结果请求
        VideoAsyncScanResultsRequest request = getResultRequest(taskId);

        HttpResponse httpResponse = null;

        try {
            httpResponse = client.doAction(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        if (null != httpResponse && httpResponse.isSuccess()) {

            JSONObject jsonObject = null;

            try {
                jsonObject = JSON.parseObject(new String(httpResponse.getHttpContent(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return null;
            }

            if (200 == jsonObject.getInteger("code")) {
                JSONArray datas = jsonObject.getJSONArray("data");
                for (Object data : datas) {
                    if (200 == ((JSONObject) data).getInteger("code")) {
                        JSONArray sceneResults = ((JSONObject) data).getJSONArray("results");
                        for (Object sceneResult : sceneResults) {
                            String suggestion = ((JSONObject) sceneResult).getString("suggestion");
                            if ("pass".equals(suggestion)) {
                                System.out.println("未发现违规内容");
                                return false;
                            } else {
                                System.out.println("发现违规内容");
                                return true;
                            }
                        }
                    } else {
                        System.out.println("正在审核");
                        return null;
                    }
                }
            }
        }

        return null;
    }
}
