package org.jolene.threek.web;

import org.jolene.threek.entity.User;
import org.jolene.threek.service.LoginService;
import org.jolene.threek.web.controller.pages.IndexPage;
import org.jolene.threek.web.controller.pages.LoginPage;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * 已认证（登录）的一个web测试,需要使用{@link LoginAs}标注配合
 *
 * @author Jolene
 */
@RunWith(AuthenticatedWebTest.AuthenticatedRunner.class)
public abstract class AuthenticatedWebTest extends WebTest {

    /**
     * 既然登录了 必然会有首页.
     */
    protected IndexPage indexPage;
    private LoginAs currentLogin;

    @Autowired
    private LoginService loginService;

    @Before
    public void authenticate() {
        if (currentLogin == null) {
            throw new IllegalStateException("使用AuthenticatedWebTest作为测试基类那么需要使用@LoginAs标注声明所登录的角色.");
        }

        User user = new User();

        user.setUsername(UUID.randomUUID().toString());
        String rawPassword = UUID.randomUUID().toString();
        user = loginService.changeLoginWithRawPassword(user, rawPassword);

        driver.get("http://localhost");
        LoginPage loginPage = LoginPage.at(driver);

        indexPage = loginPage.assertLoginSuccess(user.getUsername(), rawPassword);
    }

    public static class AuthenticatedRunner extends SpringJUnit4ClassRunner {

        public AuthenticatedRunner(Class<?> clazz) throws InitializationError {
            super(clazz);
        }

        @Override
        protected Statement withBefores(FrameworkMethod frameworkMethod, Object testInstance, Statement statement) {
            if (testInstance instanceof AuthenticatedWebTest) {
                // 抓取LoginAs
                LoginAs defaultLogin = testInstance.getClass().getAnnotation(LoginAs.class);
                LoginAs methodLogin = frameworkMethod.getAnnotation(LoginAs.class);
                ((AuthenticatedWebTest) testInstance).currentLogin = methodLogin != null ? methodLogin : defaultLogin;
            }
            return super.withBefores(frameworkMethod, testInstance, statement);
        }
    }
}
