package com.imooc.utils.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 阿里云服务配置
 */
public interface BaseAliyunServer {

    String regionId = "cn-shanghai";

    String accessKeyId = "LTAI4GCx7gPz7p6Ex3XAH1nK";

    String secret = "W6WloKxtfoPS5yKtCuYXGFddVl7t0g";

    /**
     * 构建客户端
     *
     * @return
     */
    static IAcsClient getClient() {

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);

        return new DefaultAcsClient(profile);
    }
}
