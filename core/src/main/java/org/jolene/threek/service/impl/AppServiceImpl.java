package org.jolene.threek.service.impl;

import org.jolene.threek.SystemConfig;
import org.jolene.threek.exception.ConfigRequiredException;
import org.jolene.threek.service.AppService;
import org.jolene.threek.service.SystemValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Jolene
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private Environment environment;
    private SystemConfig systemConfig;
    @Autowired
    private SystemValueService systemValueService;

    @Override
    @Transactional(readOnly = true)
    public synchronized SystemConfig currentSystemConfig() {
        if (systemConfig == null) {
            systemConfig = new SystemConfig();
            systemValueService.asText(systemConfig::setTitle, SystemConfig.TITLE);
            systemValueService.asText(systemConfig::setWelcomeMessage, SystemConfig.WELCOME_MESSAGE);
            systemValueService.asText(systemConfig::setUrl, SystemConfig.URL);
            systemValueService.asBoolean(systemConfig::setConfigRequired, SystemConfig.CONFIG_REQUIRED);
            systemValueService.asInt(systemConfig::setMaxLots, SystemConfig.MAX_LOTS);
            systemValueService.asInt(systemConfig::setQueueDays, SystemConfig.QUEUE_DAYS);
            systemValueService.asInt(systemConfig::setStock, SystemConfig.STOCK);
            systemValueService.asDouble(systemConfig::setRate, SystemConfig.RATE);
            systemValueService.asTexts(systemConfig::setWelcomeFeatures, SystemConfig.WELCOME_FEATURES);
        }
        return systemConfig;
    }

    @Override
    public void preHandle(WebRequest request) throws Exception {
        //开发友好
        if (environment.acceptsProfiles("development") && !environment.acceptsProfiles("_config_test")) {
            return;
        }
        if (this.currentSystemConfig().isConfigRequired()) {
            throw new ConfigRequiredException();
        }
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}
