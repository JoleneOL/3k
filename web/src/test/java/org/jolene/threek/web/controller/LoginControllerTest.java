package org.jolene.threek.web.controller;

import org.jolene.threek.entity.User;
import org.jolene.threek.repository.LoginRepository;
import org.jolene.threek.service.LoginService;
import org.jolene.threek.web.WebTest;
import org.jolene.threek.web.controller.pages.IndexPage;
import org.jolene.threek.web.controller.pages.LoginPage;
import org.jolene.threek.web.pages.AbstractPage;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author Jolene
 */
public class LoginControllerTest extends WebTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private LoginRepository loginRepository;

    /**
     * 级别太低 不做这种测试了
     *
     * @throws Exception no
     */
    @Ignore
    @Test
    public void netLogin() throws Exception {

        HttpSession session = mockMvc.perform(
                post("/auth")
        )
                .andDo(print())
                .andReturn().getRequest().getSession();

        mockMvc.perform(
                get("/login?type=error")
                        .session((MockHttpSession) session)
        )
                .andDo(print());
    }

    @Test
    public void testLogin() throws Exception {

        driver.get("http://localhost");
        LoginPage page = initPage(LoginPage.class);

        // 页面应该没有弹出的信息或者错误
        assertThat(page)
                .matches(AbstractPage::isClean, "页面应该清爽");

        appService.currentSystemConfig().setTitle(UUID.randomUUID().toString());
        page.refresh();
        assertThat(driver.getTitle()).contains(appService.currentSystemConfig().getTitle());
        page.logoContains(appService.currentSystemConfig().getTitle());

        String[] strings = new String[random.nextInt(5) + 1];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = UUID.randomUUID().toString();
        }
        appService.currentSystemConfig().setWelcomeFeatures(strings);
        page.refresh();
        page.assertFeatures(strings);


        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        String rawPassword = UUID.randomUUID().toString();

        user = loginService.changeLoginWithRawPassword(user, rawPassword);

        try {

            page = page.assertLoginWithBadPassword(user.getUsername(), rawPassword + rawPassword);
//

            user.setEnabled(false);
            loginRepository.save(user);
            page = page.assertLoginWithDisabled(user.getUsername(), rawPassword);
//
            user.setEnabled(true);
            user.setAccountNonLocked(false);
            loginRepository.save(user);
            page = page.assertLoginWithLocked(user.getUsername(), rawPassword);

            user.setEnabled(true);
            user.setAccountNonLocked(true);
            loginRepository.save(user);
            IndexPage indexPage = page.assertLoginSuccess(user.getUsername(), rawPassword);

            // 看到自己的logo和名字 以及登出的link
            indexPage.seeLoginAs(user);

            page = indexPage.logout();

            assertThat(page.getInfoAlertMessage("登出消息")).contains("欢迎");

            //再次访问首页
            driver.get("http://localhost");
            page = initPage(LoginPage.class);

        } finally {
            loginRepository.delete(user);
        }

    }
}