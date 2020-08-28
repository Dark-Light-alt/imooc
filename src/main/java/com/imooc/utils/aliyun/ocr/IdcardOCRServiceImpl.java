package com.imooc.utils.aliyun.ocr;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardResponse;
import com.imooc.utils.aliyun.BaseConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证光学字符识别服务实现
 */
@Component
public class IdcardOCRServiceImpl implements IdcardOCRService {

    @Resource
    private BaseConfig baseConfig;

    @Override
    public Map<String, String> identity(String url) {

        // 构建客户端
        IAcsClient client = baseConfig.acsClient();

        // 构建身份证识别请求
        RecognizeIdentityCardRequest request = buildRequest(baseConfig.getRegionId(), url);

        Map<String, String> idcardInfo = new HashMap<>();

        try {
            RecognizeIdentityCardResponse response = client.getAcsResponse(request);

            RecognizeIdentityCardResponse.Data.FrontResult frontResult = response.getData().getFrontResult();

            idcardInfo.put(NAME, frontResult.getName());
            idcardInfo.put(GENDER, frontResult.getGender());
            idcardInfo.put(BIRTH_DATE, frontResult.getBirthDate());
            idcardInfo.put(ADDRESS, frontResult.getAddress());
            idcardInfo.put(NATIONALITY, frontResult.getNationality());
            idcardInfo.put(ID_NUMBER, frontResult.getIDNumber());

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

        return idcardInfo;
    }
}
