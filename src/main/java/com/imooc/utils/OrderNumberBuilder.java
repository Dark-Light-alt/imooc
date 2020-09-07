package com.imooc.utils;

import cn.hutool.core.util.NumberUtil;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号生成器
 */
@Component
public class OrderNumberBuilder {

    // 时间格式化
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public String builder() {

        // 获取当前时间
        String nowTime = format.format(new Date());

        int[] numbers = NumberUtil.generateRandomNumber(0, 9, 4);

        StringBuffer number = new StringBuffer();

        for (int num : numbers) {
            number.append(Integer.toString(num));
        }

        // 拼接随机数
        return (nowTime + number.toString());
    }
}
