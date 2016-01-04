package org.jolene.threek.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

/**
 * 系统属性服务
 *
 * @author Jolene
 */
public interface SystemValueService {

    /**
     * 以字符串数组方式保存
     *
     * @param texts 数组
     * @param id    主键
     */
    @Transactional
    void asTexts(String[] texts, String id);

    /**
     * 以字符串保存
     *
     * @param text 文本
     * @param id   主键
     */
    @Transactional
    void asText(String text, String id);

    /**
     * 以整型保存
     *
     * @param value 数据
     * @param id    主键
     */
    @Transactional
    void asInt(int value, String id);

    /**
     * 以双精度保存
     *
     * @param value 数据
     * @param id    主键
     */
    @Transactional
    void asDouble(double value, String id);

    /**
     * 以布尔保存
     *
     * @param value 数据
     * @param id    主键
     */
    @Transactional
    void asBoolean(boolean value, String id);

    /**
     * 获取属性以文本格式
     *
     * @param consumer 消耗者
     * @param id       主键
     */
    @Transactional(readOnly = true)
    void asText(Consumer<String> consumer, String id);

    /**
     * 获取属性以文本数组格式
     *
     * @param consumer 消耗者
     * @param id       主键
     */
    @Transactional(readOnly = true)
    void asTexts(Consumer<String[]> consumer, String id);

    /**
     * 获取属性以布尔格式
     *
     * @param consumer 消耗者
     * @param id       主键
     */
    @Transactional(readOnly = true)
    void asBoolean(Consumer<Boolean> consumer, String id);

    /**
     * 获取属性以整型格式
     *
     * @param consumer 消耗者
     * @param id       主键
     */
    @Transactional(readOnly = true)
    void asInt(Consumer<Integer> consumer, String id);

    /**
     * 获取属性以双精度格式
     *
     * @param consumer 消耗者
     * @param id       主键
     */
    @Transactional(readOnly = true)
    void asDouble(Consumer<Double> consumer, String id);
}
