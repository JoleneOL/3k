package org.jolene.threek.service;

import org.jolene.threek.SystemConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.PostConstruct;

/**
 * 很多跟app相关的服务
 *
 * <p>另外还兼职了请求拦截器,用于保护应用程序在未被配置情况下进行访问</p>
 *
 * @author Jolene
 */
public interface AppService extends WebRequestInterceptor,UserDetailsService {

    @PostConstruct
    @Transactional
    void init();

    /**
     * @return 总是返回唯一不变的SystemConfig实例
     */
    @Transactional(readOnly = true)
    SystemConfig currentSystemConfig();

    /**
     * @return 重新获得一份配置
     */
    @Transactional(readOnly = true)
    SystemConfig readSystemConfig();

    /**
     * 保存当前配置到系统中
     */
    @Transactional
    void saveCurrentSystemConfig();
}
