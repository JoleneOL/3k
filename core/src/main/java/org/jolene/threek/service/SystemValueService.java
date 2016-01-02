package org.jolene.threek.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

/**
 * 系统属性服务
 * @author Jolene
 */
public interface SystemValueService {
    /**
     * 获取属性以文本格式
     * @param consumer 消耗者
     * @param id 主键
     */
    @Transactional(readOnly = true)
    void asText(Consumer<String> consumer,String id);

    /**
     * 获取属性以整型格式
     * @param consumer 消耗者
     * @param id 主键
     */
    @Transactional(readOnly = true)
    void asInt(Consumer<Integer> consumer,String id);
    /**
     * 获取属性以双精度格式
     * @param consumer 消耗者
     * @param id 主键
     */
    @Transactional(readOnly = true)
    void asDouble(Consumer<Double> consumer,String id);
}
