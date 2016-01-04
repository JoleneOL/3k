package org.jolene.threek.service.impl;

import org.jolene.threek.K3Version;
import org.jolene.threek.SystemConfig;
import org.jolene.threek.exception.ConfigRequiredException;
import org.jolene.threek.repository.LoginRepository;
import org.jolene.threek.service.AppService;
import org.jolene.threek.service.SystemValueService;
import org.jolene.threek.service.VersionService;
import org.jolene.threek.support.InterestReward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.PostConstruct;

/**
 * @author Jolene
 */
@Service("appService")
public class AppServiceImpl implements AppService {

    @Autowired
    private Environment environment;
    private SystemConfig systemConfig;
    @Autowired
    private SystemValueService systemValueService;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private VersionService versionService;

    @Override
    @PostConstruct
    @Transactional
    public void init() {
        versionService.systemUpgrade("org.jolene.3k.database.version", K3Version.class, K3Version.init, version -> {

            // do something while upgrade.
        });
    }

    @Override
    @Transactional(readOnly = true)
    public synchronized SystemConfig currentSystemConfig() {
        if (systemConfig == null) {
            systemConfig = readSystemConfig();
        }
        return systemConfig;
    }

    @Override
    @Transactional(readOnly = true)
    public SystemConfig readSystemConfig() {
        SystemConfig systemConfig = new SystemConfig();
        systemValueService.asText(systemConfig::setTitle, SystemConfig.TITLE);
        systemValueService.asText(systemConfig::setWelcomeMessage, SystemConfig.WELCOME_MESSAGE);
        systemValueService.asText(systemConfig::setUrl, SystemConfig.URL);
        systemValueService.asBoolean(systemConfig::setConfigRequired, SystemConfig.CONFIG_REQUIRED);
        systemValueService.asInt(systemConfig::setMaxLots, SystemConfig.MAX_LOTS);
        systemValueService.asInt(systemConfig::setQueueDays, SystemConfig.QUEUE_DAYS);
        systemValueService.asInt(systemConfig::setStock, SystemConfig.STOCK);
        systemValueService.asDouble(systemConfig::setRate, SystemConfig.RATE);
        systemValueService.asTexts(systemConfig::setWelcomeFeatures, SystemConfig.WELCOME_FEATURES);

        systemValueService.asInt(systemConfig::setMaxOperateHours, SystemConfig.MAX_OPERATE_HOURS);
        systemValueService.asInt(systemConfig::setMaxOrders, SystemConfig.MAX_ORDERS);
        systemValueService.asInt(systemConfig::setInspectStartDayOfMonth, SystemConfig.INSPECT_START);
        systemValueService.asInt(systemConfig::setInspectEndDayOfMonth, SystemConfig.INSPECT_END);

        systemValueService.asDouble(systemConfig::setDirectRewardRate, SystemConfig.DIRECT_REWARD_RATE);
        systemValueService.asDouble(systemConfig::setDirectRewardRate2, SystemConfig.DIRECT_REWARD_RATE2);

        systemValueService.asTexts(strings -> {
            for (int i = 0; i < strings.length; i++) {
                String[] values = strings[i].split(",");
                systemConfig.getInterestRewards().put(i + 1, new InterestReward(Double.parseDouble(values[0])
                        , Integer.parseInt(values[1]), Integer.parseInt(values[2])));
            }
        }, SystemConfig.INTEREST_REWARDS);
        return systemConfig;
    }

    @Override
    @Transactional
    public void saveCurrentSystemConfig() {
        systemValueService.asText(systemConfig.getTitle(), SystemConfig.TITLE);
        systemValueService.asText(systemConfig.getWelcomeMessage(), SystemConfig.WELCOME_MESSAGE);
        systemValueService.asText(systemConfig.getUrl(), SystemConfig.URL);
        systemValueService.asBoolean(systemConfig.isConfigRequired(), SystemConfig.CONFIG_REQUIRED);
        systemValueService.asInt(systemConfig.getMaxLots(), SystemConfig.MAX_LOTS);
        systemValueService.asInt(systemConfig.getQueueDays(), SystemConfig.QUEUE_DAYS);
        systemValueService.asInt(systemConfig.getStock(), SystemConfig.STOCK);
        systemValueService.asDouble(systemConfig.getRate(), SystemConfig.RATE);
        systemValueService.asTexts(systemConfig.getWelcomeFeatures(), SystemConfig.WELCOME_FEATURES);

        systemValueService.asInt(systemConfig.getMaxOperateHours(), SystemConfig.MAX_OPERATE_HOURS);
        systemValueService.asInt(systemConfig.getMaxOrders(), SystemConfig.MAX_ORDERS);
        systemValueService.asInt(systemConfig.getInspectStartDayOfMonth(), SystemConfig.INSPECT_START);
        systemValueService.asInt(systemConfig.getInspectEndDayOfMonth(), SystemConfig.INSPECT_END);

        systemValueService.asDouble(systemConfig.getDirectRewardRate(), SystemConfig.DIRECT_REWARD_RATE);
        systemValueService.asDouble(systemConfig.getDirectRewardRate2(), SystemConfig.DIRECT_REWARD_RATE2);

        // 1-10
        String[] interestRewardStrings = new String[10];
        for (int i = 1; i <= 10; i++) {
            InterestReward reward = systemConfig.getInterestRewards().get(i);
            interestRewardStrings[i - 1] = String.format("%s,%d,%d", reward.getRate(), reward.getReach(), reward.getMax());
        }
        systemValueService.asTexts(interestRewardStrings, SystemConfig.INTEREST_REWARDS);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = loginRepository.findByUsername(username);
        if (userDetails == null)
            throw new UsernameNotFoundException("没有找到用户:" + username);
        return userDetails;
    }
}
