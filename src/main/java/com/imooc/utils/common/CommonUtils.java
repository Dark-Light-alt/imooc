package com.imooc.utils.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

/**
 * 工具类
 */
public final class CommonUtils {

    private CommonUtils() {

    }

    /**
     * 集合是否为空
     *
     * @param c
     * @return 空返回true, 不为空返回false
     */
    public static boolean isBlank(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    /**
     * 集合是否不为空
     *
     * @param c
     * @return 不为空返回true, 空返回false
     */
    public static boolean isNotBlank(Collection<?> c) {
        return !CommonUtils.isBlank(c);
    }

    /**
     * 判断字符串是否为(null "")
     *
     * @return 空返回true, 不为空返回false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }


    /**
     * 判断字符串是不为(null "")
     *
     * @return 不为空返回true, 为空返回false
     */
    public static boolean isNotEmpty(String str) {
        return !CommonUtils.isEmpty(str);
    }

    /**
     * 集合中是否包含值中的一个
     *
     * @param list
     * @param t
     * @return
     */
    public static <T> boolean containOne(Collection<T> list, T... t) {
        for (T obj : t) {
            if (list.contains(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 时间戳格式化为日期
     *
     * @param ts
     * @param pattern
     * @return
     */
    public static String tsToDateStr(long ts, String pattern) {
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(ts));
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 获取当前年月日时分秒
     *
     * @return
     */
    public static String getCurrDateTime() {
        return formatCurrDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前日期,使用 yyyy-MM-dd格式
     *
     * @return
     */
    public static String getCurrDate() {
        return formatCurrDate("yyyy-MM-dd");
    }

    /**
     * 获取当前时间,使用 HH:mm:ss格式
     *
     * @return
     */
    public static String getCurrTime() {
        return formatCurrDate("HH:mm:ss");
    }

    /**
     * 获取当前日期,自定义日期格式
     *
     * @param pattern
     * @return
     */
    public static String getDateCustomPattern(String pattern) {
        return formatCurrDate(pattern);
    }

    /**
     * 转换当前日期,自定义格式
     *
     * @param pattern 日期转换格式,为空时默认使用 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatCurrDate(String pattern) {
        if (CommonUtils.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        return CommonUtils.tsToDateStr(System.currentTimeMillis(), pattern);
    }

    /**
     * 获取错误堆栈信息
     *
     * @param e
     * @return
     */
    public static String getExceptionAllinformation(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

    /**
     * 校验集合中是否有重复值
     *
     * @param lsStr 集合
     * @return true 重复,false 不重复
     */
    public static boolean checkRepetition(List<String> lsStr) {
        if (CommonUtils.isBlank(lsStr)) {
            return false;
        }
        return lsStr.stream().distinct().count() != lsStr.size();
    }
}
