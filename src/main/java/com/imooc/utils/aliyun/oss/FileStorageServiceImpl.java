package com.imooc.utils.aliyun.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.imooc.utils.aliyun.BaseConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 文件存储服务实现
 */
@Component
public class FileStorageServiceImpl implements FileStorageService {

    @Resource
    private BaseConfig baseConfig;

    /**
     * 上传文件
     *
     * @param file     文件
     * @param type     文件所属类别
     * @param uploader 上传者
     * @return url
     */
    @Override
    public String upload(File file, String type, String uploader) {

        // 构建 oss 对象存储客户端
        OSS ossClient = baseConfig.ossClient();

        String url = null;

        try {
            InputStream in = new FileInputStream(file);

            // 获取文件后缀
            String suffix = getSuffix(file.getName());

            // 获取文件上传至服务器的类型
            String contentType = contentType(suffix);

            // 创建上传的 object 的元数据
            ObjectMetadata meta = new ObjectMetadata();

            // 设置上传内容类型
            meta.setContentType(contentType);
            // 设置文件被下载时网页的缓存行为
            meta.setCacheControl("no-cache");

            // 对文件进行重命名
            String rename = rename(type, suffix, uploader);

            // 创建上传请求
            PutObjectRequest request = new PutObjectRequest(BUCKET, rename, in, meta);

            ossClient.putObject(request);

            // 获取文件上传之后的 url
            url = getURL(rename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            ossClient.shutdown();
        }

        return url;
    }
}
