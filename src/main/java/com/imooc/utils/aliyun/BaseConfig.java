package com.imooc.utils.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application.yml")
public class BaseConfig {

    @Value("${aliyun.regionId}")
    private String regionId;

    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.secret}")
    private String secret;

    @Value("${aliyun.ossEndPoint}")
    private String ossEndPoint;

    /**
     * 构建客户端
     *
     * @return
     */
    public IAcsClient acsClient() {

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);

        return new DefaultAcsClient(profile);
    }

    /**
     * 构建对象存储客户端
     *
     * @return
     */
    public OSS ossClient() {
        return new OSSClientBuilder().build(ossEndPoint, accessKeyId, secret);
    }
}
