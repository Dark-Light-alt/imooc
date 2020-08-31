package com.imooc.utils.aliyun.green.text;

import com.aliyuncs.imageaudit.model.v20191230.ScanTextRequest;
import com.aliyuncs.imageaudit.model.v20191230.ScanTextResponse;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文本检测服务
 */
public interface TextDetectionService {

    // 文字涉政内容识别
    String POLITICS = "politics";

    // 文字辱骂内容识别
    String ABUSE = "abuse";

    // 文字涉恐内容识别
    String TERRORISM = "terrorism";

    // 文字鉴黄内容识别
    String PORN = "porn";

    /**
     * 文字违规内容识别
     *
     * @param content 文本内容
     * @param label   识别类型
     * @return 识别结果：true 违规 / false 正常
     */
    boolean detection(String content, String label);

    /**
     * 获取文字扫描请求
     *
     * @param regionId 地域 id
     * @return
     */
    default ScanTextRequest getRequest(String regionId) {

        ScanTextRequest request = new ScanTextRequest();

        request.setSysRegionId(regionId);

        return request;
    }

    /**
     * 设置识别任务
     *
     * @param content 文本内容
     * @return
     */
    default ScanTextRequest.Tasks setTasks(String content) {

        ScanTextRequest.Tasks tasks = new ScanTextRequest.Tasks();

        tasks.setContent(content);

        return tasks;
    }

    /**
     * 设置识别场景
     *
     * @param label 识别场景
     * @return
     */
    default ScanTextRequest.Labels setLabels(String label) {

        ScanTextRequest.Labels labels = new ScanTextRequest.Labels();

        labels.setLabel(label);

        return labels;
    }

    /**
     * 获取识别结果
     *
     * @param response 识别响应
     * @return 识别结果：true 违规 / false 正常
     */
    default boolean getResult(ScanTextResponse response, String label) {

        AtomicBoolean flag = new AtomicBoolean(false);

        List<ScanTextResponse.Data.Element> elements = response.getData().getElements();

        elements.stream().map(ScanTextResponse.Data.Element::getResults).forEach(results -> results.forEach(result -> {
            if (label.equals(result.getLabel()) && "block".equals(result.getSuggestion())) {
                flag.set(true);
            }
        }));

        return flag.get();
    }
}
