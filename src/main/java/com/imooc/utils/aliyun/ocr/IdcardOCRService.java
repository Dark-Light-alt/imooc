package com.imooc.utils.aliyun.ocr;

import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardRequest;

import java.util.Map;

/**
 * 身份证光学字符识别服务
 */
public interface IdcardOCRService {

    // 姓名
    String NAME = "name";

    // 性别
    String GENDER = "gender";

    // 出生日期
    String BIRTH_DATE = "birthDate";

    // 地址
    String ADDRESS = "address";

    // 国籍
    String NATIONALITY = "nationality";

    // 身份证号
    String ID_NUMBER = "idNumber";

    /**
     * 构建身份证识别请求
     *
     * @param regionId 地域 id
     * @param url      身份证存储路径
     * @return
     */
    default RecognizeIdentityCardRequest buildRequest(String regionId, String url) {

        RecognizeIdentityCardRequest request = new RecognizeIdentityCardRequest();
        request.setSysRegionId(regionId);
        request.setSide("face");
        request.setImageURL(url);

        return request;
    }

    /**
     * 身份证识别
     *
     * @param url 身份证存储路径
     * @return 身份证信息
     */
    Map<String, String> identity(String url);
}
