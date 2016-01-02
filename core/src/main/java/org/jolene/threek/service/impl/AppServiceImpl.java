package org.jolene.threek.service.impl;

import org.jolene.threek.SystemConfig;
import org.jolene.threek.service.AppService;
import org.jolene.threek.service.SystemValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jolene
 */
@Service
public class AppServiceImpl implements AppService {

    private SystemConfig systemConfig;
    @Autowired
    private SystemValueService systemValueService;

    @Override
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
}
