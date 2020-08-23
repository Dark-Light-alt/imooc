package com.imooc.utils.aliyun.green.text;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.imageaudit.model.v20191230.ScanTextRequest;
import com.aliyuncs.imageaudit.model.v20191230.ScanTextResponse;
import com.imooc.utils.aliyun.BaseAliyunServer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 文本内容智能审核
 * politics：文字涉政内容识别
 * abuse：文字辱骂内容识别
 * terrorism：文字涉恐内容识别
 * porn：文字鉴黄内容识别
 */
public class TextContentAudit implements BaseAliyunServer {

    /**
     * 审核
     *
     * @param content 文本内容
     * @param label   检测场景
     * @return 检测结果：true 违规，false 正常
     */
    public static boolean audit(String content, String label) {

        if (null != content && "".equals(content)) {
            System.out.println("文本内容不能为空");
        }

        // 构建客户端
        IAcsClient client = BaseAliyunServer.getClient();

        ScanTextRequest request = new ScanTextRequest();
        request.setSysRegionId("cn-shanghai");

        ScanTextRequest.Tasks tasks = new ScanTextRequest.Tasks();
        tasks.setContent(content);

        ScanTextRequest.Labels labels = new ScanTextRequest.Labels();
        labels.setLabel(label);

        request.setTaskss(Arrays.asList(tasks));
        request.setLabelss(Arrays.asList(labels));

        // 检测结果：true 违规，false 正常
        AtomicBoolean flag = new AtomicBoolean(false);

        try {
            ScanTextResponse response = client.getAcsResponse(request);

            List<ScanTextResponse.Data.Element> elements = response.getData().getElements();

            elements.stream().map(ScanTextResponse.Data.Element::getResults).forEach(results -> results.forEach(result -> {
                if (label.equals(result.getLabel()) && "block".equals(result.getSuggestion())) {
                    flag.set(true);
                }
            }));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

        return flag.get();
    }

    public static void main(String[] args) {

        boolean audit = TextContentAudit.audit("李帅傻逼", "abuse");

        System.out.println(audit);
    }
}
