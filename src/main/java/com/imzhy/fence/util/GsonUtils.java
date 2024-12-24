package com.imzhy.fence.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.Objects;

/**
 * gson 工具
 *
 * @author zhy
 * @since 2024.12.10
 */
public class GsonUtils {

    /**
     * 私有化构造类
     */
    private GsonUtils() {
    }

    private static Gson gson = null;

    private static Gson gsonSerializeNulls = null;

    /**
     * 获取实例
     *
     * @return 实例
     */
    public static Gson getInstance() {
        if (Objects.isNull(gson)) {
            synchronized (GsonUtils.class) {
                if (Objects.isNull(gson)) gson = new GsonBuilder()
                        .registerTypeAdapter(
                                Date.class,
                                (JsonSerializer<Date>) (src, typeOfSrc, context)
                                        -> src == null ? null : new com.google.gson.JsonPrimitive(src.getTime())
                        )
                        .create();
            }
        }
        return gson;
    }

    /**
     * 获取实例（null 值也要序列化）
     *
     * @return 实例
     */
    public static Gson getInstanceSerializeNulls() {
        if (Objects.isNull(gsonSerializeNulls)) {
            synchronized (GsonUtils.class) {
                if (Objects.isNull(gsonSerializeNulls))
                    gsonSerializeNulls = new GsonBuilder()
                            .serializeNulls()
                            .registerTypeAdapter(
                                    Date.class,
                                    (JsonSerializer<Date>) (src, typeOfSrc, context)
                                            -> src == null ? null : new com.google.gson.JsonPrimitive(src.getTime())
                            )
                            .create();
            }
        }
        return gsonSerializeNulls;
    }

    /**
     * object 转换成对象（泛型支持）
     *
     * @param object    转换前对象
     * @param typeToken typeToken
     * @param <K>       转换后对象
     * @return 对象
     */
    public static <K> K get(Object object, TypeToken<K> typeToken) {
        if (Objects.isNull(object)) return null;
        String result = object instanceof String ? (String) object : getToString(object);
        return getInstance().fromJson(result, typeToken.getType());
    }

    /**
     * object 转换成对象
     *
     * @param object 转换前对象
     * @param clz    clz
     * @param <K>    转换后对象
     * @return 对象
     */
    public static <K> K get(Object object, Class<K> clz) {
        if (Objects.isNull(object)) return null;
        String result = object instanceof String ? (String) object : getToString(object);
        return getInstance().fromJson(result, clz);
    }

    /**
     * 将对象序列化成 json
     *
     * @param object 对象
     * @return json str
     */
    public static String getToString(Object object) {
        // 避免转换成 null 字符串
        if (Objects.isNull(object)) return null;
        return getInstance().toJson(object);
    }
}
