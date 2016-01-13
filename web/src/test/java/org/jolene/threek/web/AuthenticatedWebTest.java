package org.jolene.threek.web;

import org.jolene.threek.entity.Email;
import org.jolene.threek.entity.Login;
import org.jolene.threek.entity.Manager;
import org.jolene.threek.entity.Ticket;
import org.jolene.threek.entity.User;
import org.jolene.threek.service.TicketService;
import org.jolene.threek.web.controller.pages.IndexPage;
import org.jolene.threek.web.controller.pages.LoginPage;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * 已认证（登录）的一个web测试,需要使用{@link LoginAs}标注配合
 * <p>测试方法内可以使用{@link #currentUser 当前用户},{@link #indexPage 首页},{@link #session session}</p>
 *
 * @author Jolene
 */
@RunWith(AuthenticatedWebTest.AuthenticatedRunner.class)
public abstract class AuthenticatedWebTest extends WebTest {

    /**
     * 既然登录了 必然会有首页.
     * driver版本
     */
    protected IndexPage indexPage;
    /**
     * net版本
     * TODO 尚未实现
     */
    protected MockHttpSession session;
    /**
     * 当前用户
     */
    protected Login currentUser;
    private LoginAs currentLogin;

    @Autowired
    private TicketService ticketService;

    @Before
    public void authenticate() throws IllegalAccessException, InstantiationException {
        if (currentLogin == null) {
            throw new IllegalStateException("使用AuthenticatedWebTest作为测试基类那么需要使用@LoginAs标注声明所登录的角色.");
        }
        driver.manage().deleteAllCookies();

        currentUser = currentLogin.value()[0].getType().newInstance();

        if (currentUser instanceof Manager) {
            ((Manager) currentUser).setRoles(new HashSet<>());
            for (LoginType type : currentLogin.value()) {
                ((Manager) currentUser).getRoles().add(type.getRole());
            }
        }

        currentUser.setUsername(UUID.randomUUID().toString());
        String rawPassword = UUID.randomUUID().toString();
        currentUser = loginService.changeLoginWithRawPassword(currentUser, rawPassword);

        driver.get("http://localhost");
        LoginPage loginPage = initPage(LoginPage.class);

        indexPage = loginPage.assertLoginSuccess(currentUser.getUsername(), rawPassword);
    }

    /**
     * 给{@link #currentUser 当前用户}随机几个入场券
     * <p>当然如果当前用户不是一个最终用户会报错</p>
     *
     * @return 生成的入场券
     */
    protected Collection<Ticket> giveCurrentSomeTicket() {
        return giveCurrentSomeTicket(random.nextInt(22) + 1);
    }

    /**
     * 给{@link #currentUser 当前用户}几个入场券
     * <p>当然如果当前用户不是一个最终用户会报错</p>
     *
     * @param number 数量
     * @return 生成的入场券
     */
    protected Collection<Ticket> giveCurrentSomeTicket(int number) {
        User user = (User) currentUser;
        //弄几个入场券给他
        return ticketService.newTickets(number, ticket -> ticket.setClaimant(user));
    }

    /**
     * 当前用户接收随机邮件
     *
     * @param sender 发送者,如果是null则会创建一个用户
     * @param count  接受邮件数量
     * @return 新邮件集合
     */
    protected Collection<Email> receiveMailFrom(Login sender, int count) {
        ArrayList<Email> emails = new ArrayList<>();
        while (count-- > 0) {
            Login theSender = sender;
            if (theSender == null) {
                theSender = addNewUserUnder(null, 1).stream().findAny().get();
            }
            emails.addAll(sendMail(theSender, currentUser, 1));
        }
        return emails;
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
