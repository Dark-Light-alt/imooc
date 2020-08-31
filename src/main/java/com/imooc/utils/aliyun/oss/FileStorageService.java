package com.imooc.utils.aliyun.oss;

import java.io.InputStream;
import java.util.UUID;

/**
 * 文件存储服务
 */
public interface FileStorageService {

    // 存储空间名
    String BUCKET = "imoocs";

    // 文章存储路径
    String ARTICLE = "article";

    // 资料存储路径
    String DATA = "data";

    // 身份证存储路径
    String IDCARD = "idcard";

    // 其他图片存储路径
    String IMG = "img";

    // 笔记存储路径
    String NOTES = "notes";

    // 学籍证明存储路径
    String STUDENT_STATUS = "student_status";

    // 课程视频存储路径
    String VIDEO = "video";

    /**
     * 文件上传
     *
     * @param in       输入流对象
     * @param fileName 文件名
     * @param type     文件类别
     * @return url
     */
    String upload(InputStream in, String fileName, String type);

    /**
     * 根据文件后缀获取 contentType
     *
     * @param suffix 文件后缀
     * @return
     */
    default String contentType(String suffix) {
        String contentType = null;
        switch (suffix) {
            case "bmp":
                contentType = "image/bmp";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            case "png":
            case "jpeg":
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "html":
                contentType = "text/html";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "vsd":
                contentType = "application/vnd.visio";
                break;
            case "ppt":
            case "pptx":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "doc":
            case "docx":
                contentType = "application/msword";
                break;
            case "xml":
                contentType = "text/xml";
                break;
            case "mp4":
                contentType = "video/mp4";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }
        return contentType;
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     * @return
     */
    default String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 重命名
     *
     * @param type   文件所属类别
     * @param suffix 文件后缀
     * @return 新文件名
     */
    default String rename(String type, String suffix) {
        return (type + "/" + UUID.randomUUID().toString() + "." + suffix);
    }

    /**
     * 获取文件上传之后的 url
     *
     * @param rename 文件重命名之后的名字
     * @return url
     */
    default String getURL(String rename) {
        return "https://" + BUCKET + ".oss-cn-shanghai.aliyuncs.com/" + rename;
    }
}
