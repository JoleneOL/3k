package org.jolene.threek.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

/**
 * @author Jolene
 */
@Configuration
@Profile("container")
@ImportResource("classpath:datasource.xml")
public class DataSourceConfig {
}
