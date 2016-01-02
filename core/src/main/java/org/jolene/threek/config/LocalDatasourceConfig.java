package org.jolene.threek.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

/**
 * 本地数据源,只有在非容器环境中开启
 * @author Jolene
 */
@Configuration
@Profile("!container")
@ImportResource("classpath:local_datasource.xml")
public class LocalDatasourceConfig {
}
