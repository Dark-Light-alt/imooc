package com.imooc.utils.aliyun.green.text;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.imageaudit.model.v20191230.ScanTextRequest;
import com.imooc.utils.aliyun.BaseConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 文本检测服务实现
 */
@Component
public class TextDetectionServiceImpl implements TextDetectionService {

    @Resource
    private BaseConfig baseConfig;

    /**
     * 文字违规内容识别
     *
     * @param content 文本内容
     * @param label   识别类型
     * @return 识别结果：true 违规 / false 正常
     */
    @Override
    public boolean detection(String content, String label) {

        // 构建客户端
        IAcsClient client = baseConfig.acsClient();

        // 获取文本扫描请求
        ScanTextRequest request = getRequest(baseConfig.getRegionId());

        // 设置识别任务
        ScanTextRequest.Tasks tasks = setTasks(content);

        // 设置识别场景
        ScanTextRequest.Labels labels = setLabels(label);

        request.setTaskss(Arrays.asList(tasks));
        request.setLabelss(Arrays.asList(labels));

        boolean result = false;

        try {
            // 获取识别结果
            result = getResult(client.getAcsResponse(request), label);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

        return result;
    }
}
