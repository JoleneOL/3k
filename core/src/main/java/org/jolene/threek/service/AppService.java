package org.jolene.threek.service;

import org.jolene.threek.SystemConfig;

/**
 * 很多跟app相关的服务
 * @author Jolene
 */
public interface AppService {

    /**
     *
     * @return 总是返回唯一不变的SystemConfig实例
     */
    SystemConfig currentSystemConfig();

}
