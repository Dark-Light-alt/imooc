package com.imooc.utils.aliyun.ocr;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardResponse;
import com.imooc.utils.aliyun.BaseAliyunServer;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份证智能识别
 */
public class IDcardIdentify implements BaseAliyunServer {

    /**
     * 识别：正面
     *
     * @param imageURL oss 文件存储路径，不能为空
     * @return 身份证信息
     */
    public static Map<String, String> identify(String imageURL) {

        // 构建客户端
        IAcsClient client = BaseAliyunServer.getClient();

        RecognizeIdentityCardRequest request = new RecognizeIdentityCardRequest();
        request.setSysRegionId(regionId);
        request.setSide("face");
        request.setImageURL(imageURL);

        Map<String, String> idcardInfo = new HashMap<>();

        try {
            RecognizeIdentityCardResponse response = client.getAcsResponse(request);

            RecognizeIdentityCardResponse.Data.FrontResult frontResult = response.getData().getFrontResult();

            idcardInfo.put("name", frontResult.getName());
            idcardInfo.put("gender", frontResult.getGender());
            idcardInfo.put("birthDate", frontResult.getBirthDate());
            idcardInfo.put("address", frontResult.getAddress());
            idcardInfo.put("nationality", frontResult.getNationality());
            idcardInfo.put("idNumber", frontResult.getIDNumber());

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

        return idcardInfo;
    }

    public static void main(String[] args) {

        Map<String, String> identify = identify("https://imoocs.oss-cn-shanghai.aliyuncs.com/idcard/idcard.png");

        System.out.println(identify);
    }
}
