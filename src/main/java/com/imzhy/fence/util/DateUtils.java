package com.imzhy.fence.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 时间工具类
 * G: 公元
 * y: 四位数年份
 * M: 月分
 * d: 日
 * h: 12小时制（1 - 12）
 * H: 24小时制（0 - 23）
 * m: 分
 * s: 秒
 * S: 毫秒
 * <p>
 * E: 一周中的周几
 * D: 一年中的第几天
 * w: 一年中的第几个星期
 * a: 上午 / 下午 标记符
 * k: 时（1 - 24）
 * K: 时（在上午或下午 0 - 11）
 *
 * @author zhy
 * @since 2024.12.23
 */
public class DateUtils {

    /**
     * 私有化
     */
    private DateUtils() {
    }

    // 只包含日期
    public static final String FORMAT_TIME_STANDARD_ONLY_DATE = "yyyy-MM-dd";
    // 简单格式化
    public static final String FORMAT_TIME_SIMPLE = "yyyyMMddHHmmss";
    // 标准格式化
    public static final String FORMAT_TIME_STANDARD = "yyyy-MM-dd HH:mm:ss";
    // 标准格式化，带毫秒
    public static final String FORMAT_TIME_STANDARD_MILLISECOND = "yyyy-MM-dd HH:mm:ss SSS";

    // 东八时区：上海
    private static final ZoneId ZONE_ID_OF_ASIA_SHANGHAI = ZoneId.of("Asia/Shanghai");
    // 默认时区
    private static final ZoneId ZONE_ID_OF_DEFAULT = ZoneId.systemDefault();
    // 偏移：UTC+0800
    private static final ZoneOffset ZONE_OFFSET_OF_EIGHT = ZoneOffset.of("+0800");
    // 默认偏移时区
    private static final ZoneOffset ZONE_OFFSET_OF_DEFAULT = ZonedDateTime.now().getOffset();

    /**
     * 获取当前时间的时间戳
     *
     * @return 毫秒数
     */
    public static Long getNowTimeStamp() {
        return getTimeStampOfLocalDateTime(getNowLocalDateTime());
    }

    /**
     * 获取本地时间
     *
     * @return 本地时间
     */
    public static LocalDateTime getNowLocalDateTime() {
        return LocalDateTime.now(ZONE_ID_OF_DEFAULT);
    }

    /**
     * 获取当前时间 {@link LocalDate}
     *
     * @return {@link LocalDate}
     */
    public static LocalDate getNowLocalDate() {
        return LocalDate.now(ZONE_ID_OF_DEFAULT);
    }

    /**
     * 根据时间获取时间戳
     *
     * @param localDateTime localDateTime
     * @return 时间戳
     */
    public static Long getTimeStampOfLocalDateTime(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) return null;
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZONE_ID_OF_DEFAULT);
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 根据时间获取时间戳
     *
     * @param localDate {@link LocalDate}
     * @return 时间戳
     */
    public static Long getTimeStampOfLocalDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) return null;
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZONE_ID_OF_DEFAULT);
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }


    /**
     * 根据时间戳获取 {@link LocalDateTime}
     *
     * @param time 时间戳
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime getLocalDateTimeOfTimeStamp(Long time) {
        if (Objects.isNull(time)) return null;
        // 使用系统默认的 offset，不在使用东八区
        Instant instant = Instant.ofEpochMilli(time);
        ZonedDateTime zonedDateTime = instant.atZone(ZONE_ID_OF_DEFAULT);
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * 根据时间戳获取 {@link LocalDate}
     *
     * @param time 时间戳
     * @return {@link LocalDate}
     */
    public static LocalDate getLocalDateOfTimeStamp(Long time) {
        if (Objects.isNull(time)) return null;
        // 使用系统默认的 offset，不在使用东八区
        Instant instant = Instant.ofEpochMilli(time);
        ZonedDateTime zonedDateTime = instant.atZone(ZONE_ID_OF_DEFAULT);
        return zonedDateTime.toLocalDate();
    }

    // ======================================== 华丽分割线 ======================================

    /**
     * 获取偏移后的时间
     *
     * @param localDateTime 指定时间
     * @param hour          小时
     * @return 偏移后的时间戳
     */
    public static long getNextDateTimeStamp(LocalDateTime localDateTime, Long hour) {
        return getNextDateTimeStamp(localDateTime, 0L, hour);
    }

    /**
     * 获取偏移后的时间
     *
     * @param localDateTime 指定时间
     * @param day           day
     * @param hour          小时
     * @return 偏移后的时间戳
     */
    public static long getNextDateTimeStamp(LocalDateTime localDateTime, Long day, Long hour) {
        return getNextDateTimeStamp(localDateTime, 0L, day, hour);
    }

    /**
     * 获取偏移后的时间
     *
     * @param localDateTime 指定时间
     * @param month         month
     * @param day           day
     * @param hour          小时
     * @return 偏移后的时间戳
     */
    public static long getNextDateTimeStamp(LocalDateTime localDateTime, Long month, Long day, Long hour) {
        return getNextDateTimeStamp(localDateTime, 0L, month, day, hour);
    }

    /**
     * 获取偏移后的时间
     *
     * @param localDateTime 指定时间
     * @param year          year
     * @param month         month
     * @param day           day
     * @param hour          小时
     * @return 偏移后的时间戳
     */
    public static Long getNextDateTimeStamp(LocalDateTime localDateTime, Long year, Long month, Long day, Long hour) {
        localDateTime = Objects.isNull(localDateTime) ? getNowLocalDateTime() : localDateTime;
        if (Objects.nonNull(year)) localDateTime = localDateTime.plusYears(year);
        if (Objects.nonNull(month)) localDateTime = localDateTime.plusMonths(month);
        if (Objects.nonNull(day)) localDateTime = localDateTime.plusDays(day);
        if (Objects.nonNull(hour)) localDateTime = localDateTime.plusHours(hour);
        return getTimeStampOfLocalDateTime(localDateTime);
    }

    /**
     * 将 {@link LocalDate} 转换成日期字符串
     * <p>
     * 因为是 LocalDate，所以格式化后不带时间，yyyy-MM-dd 格式
     *
     * @param localDate {@link LocalDate}
     * @return 转换后的日期字符串
     */
    public static String getDateFormatOfLocalDate(LocalDate localDate) {
        if (Objects.isNull(localDate)) return null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_TIME_STANDARD_ONLY_DATE);
        return dateTimeFormatter.format(localDate);
    }
}
