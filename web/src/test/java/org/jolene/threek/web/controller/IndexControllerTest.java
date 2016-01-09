package org.jolene.threek.web.controller;

import org.jolene.threek.entity.Ticket;
import org.jolene.threek.entity.User;
import org.jolene.threek.service.AppService;
import org.jolene.threek.web.AuthenticatedWebTest;
import org.jolene.threek.web.LoginAs;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Jolene
 */
public class IndexControllerTest extends AuthenticatedWebTest {

    @Autowired
    private AppService appService;
    /**
     * 普通用户
     *
     * @throws Exception
     */
    @Test
    @LoginAs("USER")
    public void normal() throws Exception {
        appService.currentSystemConfig().setTitle("中文");
        System.out.println(driver.getPageSource());
        System.out.println(indexPage);

        // 当前无弹窗
        indexPage.noModals();

        // 测试点击 提供帮助
        indexPage.clickProviderForModal();

        // 测试点击 接受帮助
        indexPage.clickAccepterForModal();

        // 这里是一个普通用户则可以看到 它拥有的余额 和 入场券
        indexPage.seeExceptBalance(0d);
        indexPage.seeExceptTicketCount(0);

        User user = (User) currentUser;
        user.setBalance(randomDouble(10, 999999999, 1));

        Collection<Ticket> tickets = giveCurrentSomeTicket();

        driver.navigate().refresh();
        PageFactory.initElements(driver, indexPage);

        indexPage.seeExceptBalance(user.getBalance());
        indexPage.seeExceptTicketCount(tickets.size());

        System.out.println(driver.getTitle());

        // 新注册用户通知

        indexPage.seeExceptNewUsers(Collections.emptySet());

        // 增加一个它的下线
        Collection<User> newUsers = addNewUserUnder(user, random.nextInt(3) + 1);
        driver.navigate().refresh();
        PageFactory.initElements(driver, indexPage);

        indexPage.seeExceptNewUsers(newUsers);

        // 新邮件通知

        indexPage.seeExceptNewEmails(Collections.emptySet());
    }

}