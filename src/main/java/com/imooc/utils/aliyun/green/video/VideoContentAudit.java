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
import com.imooc.utils.aliyun.BaseAliyunServer;

import java.util.Arrays;
import java.util.UUID;

/**
 * 视频智能审核
 * terrorism：涉恐内容
 * porn：涉黄
 */
public class VideoContentAudit implements BaseAliyunServer {

    /**
     * 提交审核
     * 200：提交审核成功
     *
     * @param videoURL 视频地址
     * @param scenes   审核类型
     * @return stakId
     * @throws Exception
     */
    public static String submitAudit(String videoURL, String scenes) throws Exception {

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);

        DefaultProfile.addEndpoint(regionId, "Green", "green.cn-shanghai.aliyuncs.com");

        DefaultAcsClient client = new DefaultAcsClient(profile);

        VideoAsyncScanRequest request = new VideoAsyncScanRequest();
        request.setSysAcceptFormat(FormatType.JSON);
        request.setSysMethod(MethodType.POST);

        JSONObject task = new JSONObject();
        task.put("dataId", UUID.randomUUID().toString());
        task.put("url", videoURL);

        JSONObject requestData = new JSONObject();
        requestData.put("scenes", Arrays.asList(scenes));
        requestData.put("tasks", Arrays.asList(task));

        request.setHttpContent(requestData.toString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);

        request.setSysConnectTimeout(3000);
        request.setSysReadTimeout(6000);

        HttpResponse httpResponse = client.doAction(request);

        String taskId = null;

        if (httpResponse.isSuccess()) {
            JSONObject jsonObject = JSON.parseObject(new String(httpResponse.getHttpContent(), "UTF-8"));
            System.out.println(JSON.toJSONString(jsonObject, true));

            if (200 == jsonObject.getInteger("code")) {
                JSONArray datas = jsonObject.getJSONArray("data");
                for (Object data : datas) {
                    if (200 == ((JSONObject) data).getInteger("code")) {
                        taskId = ((JSONObject) data).getString("taskId");
                    } else {
                        System.out.println("未提交审核");
                    }
                }
            }
        }
        return taskId;
    }

    /**
     * 查看审核结果
     * 280：未审核完成
     * 200：审核完成
     *
     * @param taskId
     * @return 审核结果
     * @throws Exception
     */
    public static boolean viewAuditResults(String taskId) throws Exception {

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);

        DefaultProfile.addEndpoint(regionId, "Green", "green.cn-shanghai.aliyuncs.com");

        DefaultAcsClient client = new DefaultAcsClient(profile);

        VideoAsyncScanResultsRequest resultsRequest = new VideoAsyncScanResultsRequest();
        resultsRequest.setSysAcceptFormat(FormatType.JSON);

        JSONArray tasks = new JSONArray();
        tasks.add(taskId);

        resultsRequest.setHttpContent(JSON.toJSONString(tasks).getBytes("UTF-8"), "UTF-8", FormatType.JSON);

        resultsRequest.setSysConnectTimeout(3000);
        resultsRequest.setSysReadTimeout(6000);

        HttpResponse httpResponse = client.doAction(resultsRequest);

        if (httpResponse.isSuccess()) {
            JSONObject jsonObject = JSON.parseObject(new String(httpResponse.getHttpContent(), "UTF-8"));
            System.out.println(JSON.toJSONString(jsonObject, true));

            if (200 == jsonObject.getInteger("code")) {
                JSONArray datas = jsonObject.getJSONArray("data");
                for (Object data : datas) {
                    if (200 == ((JSONObject) data).getInteger("code")) {
                        JSONArray sceneResults = ((JSONObject) data).getJSONArray("results");
                        for (Object sceneResult : sceneResults) {
                            String suggestion = ((JSONObject) sceneResult).getString("suggestion");
                            if ("pass".equals(suggestion)) {
                                System.out.println("未发现违规内容");
                            } else {
                                System.out.println("发现违规内容");
                            }
                        }
                    } else {
                        System.out.println("正在审核");
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) throws Exception {

        //String taskId = submitAudit("https://imoocs.oss-cn-shanghai.aliyuncs.com/video/work2.mp4","terrorism");
        viewAuditResults("viOGd@NmubbV5R7HTQydJCS-1t1FqF");
    }

}
