package org.jolene.threek.web.controller;

import org.jolene.threek.web.WebTest;
import org.jolene.threek.web.controller.pages.IndexPage;
import org.jolene.threek.web.controller.pages.LoginPage;
import org.junit.Test;

/**
 * @author Jolene
 */
public class LoginControllerTest extends WebTest {

    @Test
    public void testLogin() throws Exception {

        driver.get("http://localhost");
        LoginPage page = LoginPage.at(driver);

        page = page.assertLoginFailed("", "");
        IndexPage indexPage = page.assertLoginSuccess("", "");
    }
}