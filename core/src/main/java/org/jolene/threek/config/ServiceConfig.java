package org.jolene.threek.config;

import org.luffy.lib.libspring.logging.LoggingConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Jolene
 */
@Configuration
@Import(LoggingConfig.class)
@ComponentScan(basePackages = {
        "org.jolene.threek.service"
})
public class ServiceConfig {
}
