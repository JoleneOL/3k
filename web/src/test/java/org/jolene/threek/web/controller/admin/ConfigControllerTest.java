package org.jolene.threek.web.controller.admin;

import org.jolene.threek.SystemConfig;
import org.jolene.threek.entity.Manager;
import org.jolene.threek.service.AppService;
import org.jolene.threek.support.InterestReward;
import org.jolene.threek.web.WebTest;
import org.jolene.threek.web.controller.admin.pages.ConfigPage;
import org.jolene.threek.web.controller.pages.LoginPage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 配置测试,按照2种情况,1是当前未配置,2是当前已配置,则需要管理员身份进行配置
 *
 * @author Jolene
 */
@ActiveProfiles("_config_test")
public class ConfigControllerTest extends WebTest {

    @Autowired
    private AppService appService;

    /**
     * 再次配置,此时是需要管理员权限即 ROOT
     */
    @Test
    public void reConfig() {
        appService.currentSystemConfig().setConfigRequired(false);
        driver.get("http://localhost");

        // 建立一个管理员
        Manager manager = new Manager();
        manager.setUsername(UUID.randomUUID().toString());
        manager.setRoles(new HashSet<>());
        manager.getRoles().add("ROOT");

        String rawPassword = UUID.randomUUID().toString();
        manager = loginService.changeLoginWithRawPassword(manager, rawPassword);

        LoginPage page = initPage(LoginPage.class);
        page.assertLoginSuccess(manager.getUsername(), rawPassword);

        // TODO goto config page via IndexPage
        driver.navigate().to("http://localhost/config");

        ConfigPage configPage = initPage(ConfigPage.class);
        // 修改其中的数据,然后从数据库中重新获取配置 并且获得正确的结果
        doRandomConfig(configPage);

        // TODO login with other Login & assert the 403 response
    }

    /**
     * 首次运行时必须配置 此时无需登录即可完成配置
     *
     * @throws Exception
     */
    @Test
    public void firstConfig() throws Exception {
        appService.currentSystemConfig().setConfigRequired(true);
        driver.get("http://localhost");
        ConfigPage configPage = initPage(ConfigPage.class);

        // 修改其中的数据,然后从数据库中重新获取配置 并且获得正确的结果
        doRandomConfig(configPage);
    }

    /**
     * 修改其中的数据,然后从数据库中重新获取配置 并且获得正确的结果
     *
     * @param configPage 配置页面
     */
    private void doRandomConfig(ConfigPage configPage) {
        SystemConfig model = new SystemConfig();
        model.setConfigRequired(false);
        model.setTitle(UUID.randomUUID().toString());
        model.setDirectRewardRate(randomDouble(0.1, 0.9, 2));
        model.setDirectRewardRate2(randomDouble(0.1, 0.9, 2));
        model.setIndexBottomNotice(UUID.randomUUID().toString());
        model.setIndexTopNotice(UUID.randomUUID().toString());
        model.setInspectStartDayOfMonth(random.nextInt(10) + 1);
        model.setInspectEndDayOfMonth(random.nextInt(10) + 1 + model.getInspectStartDayOfMonth());
        model.setUrl(randomHttpURL());
        model.setWelcomeMessage(UUID.randomUUID().toString());
        String[] strings = new String[random.nextInt(10) + 1];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = UUID.randomUUID().toString();
        }
        model.setWelcomeFeatures(strings);
        model.setRegWelcomeMessage(UUID.randomUUID().toString());
        model.setOnlyInvite(random.nextBoolean());

        model.setStock((random.nextInt(1000) + 1) * 100);
        model.setRate(randomDouble(0.1, 0.9, 2));
        model.setMaxLots(random.nextInt(88) + 1);
        model.setQueueDays(random.nextInt(9) + 1);
        model.setMaxOperateHours(random.nextInt(24) + 1);
        model.setMaxOrders(random.nextInt(88) + 1);
        model.setLeaderRate(randomDouble(0.001, 0.009, 6));
        model.setLeaderStopInvests(random.nextInt(8888) + 1);
        model.setLeaderStopMembers(random.nextInt(99) + 1);
        model.setLeaderStopRecommends(random.nextInt(99) + 1);

        for (int i = 1; i <= 10; i++) {
            InterestReward interestReward = model.getInterestRewards().get(i);
            interestReward.setMax(random.nextInt(9));
            interestReward.setReach(random.nextInt(10));
            interestReward.setRate(randomDouble(0.1, 0.9, 2));
        }

        model.setUserHelpMessage(UUID.randomUUID().toString());

        // 提交配置
        configPage.submit(model);

        SystemConfig systemConfig = appService.readSystemConfig();
        assertThat(systemConfig)
                .as("与数据库保存比较")
                .isEqualTo(model);
        assertThat(systemConfig)
                .as("与系统当前比较")
                .isEqualTo(appService.currentSystemConfig());
    }
}