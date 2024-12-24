package com.imzhy.fence.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 随机工具
 *
 * @author zhy
 * @since 2024.12.23
 */
public class RandomUtils {

    private static final Random random = new SecureRandom();

    private static final char[] CHAR_CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890".toCharArray();
    private static final char[] NUM_CHARS = "1234567890".toCharArray();

    /**
     * 生成长度为 32 的随机字符串
     *
     * @return 随机字符串
     */
    public static String generateStr32() {
        return generateStr(32);
    }

    /**
     * 生成长度为 64 的随机字符串
     *
     * @return 随机字符串
     */
    public static String generateStr64() {
        return generateStr(64);
    }

    /**
     * 生成长度为 128 的随机字符串
     *
     * @return 随机字符串
     */
    public static String generateStr128() {
        return generateStr(128);
    }

    /**
     * 生成长度为 256 的随机字符串
     *
     * @return 随机字符串
     */
    public static String generateStr256() {
        return generateStr(256);
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 长度，不能小于1
     * @return 随机字符串
     */
    public static String generateStr(int length) {
        if (length < 1) length = 32;
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++)
            chars[i] = CHAR_CHARS[((bytes[i] & 0xff) % CHAR_CHARS.length)];
        return new String(chars);
    }

    /**
     * 生成指定长度的随机数字
     *
     * @param length 长度，不能小于1
     * @return 随机数字
     */
    public static String generateNum(int length) {
        if (length < 1) length = 32;
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++)
            chars[i] = NUM_CHARS[((bytes[i] & 0xff) % NUM_CHARS.length)];
        return new String(chars);
    }
}
