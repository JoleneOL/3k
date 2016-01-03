package org.jolene.threek.web.controller.admin;

import org.jolene.threek.SystemConfig;
import org.jolene.threek.service.AppService;
import org.jolene.threek.web.WebTest;
import org.jolene.threek.web.controller.admin.pages.ConfigPage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 配置测试,按照2种情况,1是当前已配置,2是当前未配置,则需要管理员身份进行配置
 *
 * @author Jolene
 */
public class ConfigControllerTest extends WebTest {

    @Autowired
    private AppService appService;

    @Test
    public void firstConfig() throws Exception {
        appService.currentSystemConfig().setConfigRequired(true);
        driver.get("http://localhost");
        ConfigPage configPage = ConfigPage.at(driver);
        // 修改其中的数据,然后从数据库中重新获取配置 并且获得正确的结果
        doRandomConfig(configPage);
    }

    /**
     * 修改其中的数据,然后从数据库中重新获取配置 并且获得正确的结果
     *
     * @param configPage 配置页面
     */
    private void doRandomConfig(ConfigPage configPage) {
        String title = UUID.randomUUID().toString();
        configPage = configPage.changeTitle(title);
        SystemConfig systemConfig = appService.readSystemConfig();
        assertThat(systemConfig)
                .isEqualTo(appService.currentSystemConfig());
    }
}