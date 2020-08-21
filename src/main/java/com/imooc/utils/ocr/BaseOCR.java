package com.imooc.utils.ocr;

import java.util.Map;

/**
 * 图像识别接口
 */
public interface BaseOCR {

    String APP_ID = "22104841";

    String API_KEY = "1oZPpd1jCMGinzm9ca8CjyPv";

    String SECRET_KEY = "sdlmaUxQdxxMsdnWvmegx843MiRsfBke";

    Map<String, Object> discern(String imgUrl);
}
