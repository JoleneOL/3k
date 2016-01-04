package org.jolene.threek.web.controller;

import org.jolene.threek.web.WebTest;
import org.jolene.threek.web.controller.pages.IndexPage;
import org.jolene.threek.web.controller.pages.LoginPage;
import org.jolene.threek.web.pages.AbstractPage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
public class LoginControllerTest extends WebTest {

    @Test
    public void testLogin() throws Exception {

        driver.get("http://localhost");
        LoginPage page = LoginPage.at(driver);

        System.out.println(driver.getPageSource());
        // 页面应该没有弹出的信息或者错误
        assertThat(page)
                .matches(AbstractPage::isClean, "页面应该清爽");


        page = page.assertLoginFailed("", "");
        IndexPage indexPage = page.assertLoginSuccess("", "");
    }
}