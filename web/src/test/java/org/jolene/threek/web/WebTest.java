package org.jolene.threek.web;

import libspringtest.SpringWebTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.jolene.threek.CoreConfig;
import org.jolene.threek.common.PaymentMethod;
import org.jolene.threek.entity.Email;
import org.jolene.threek.entity.EmailContent;
import org.jolene.threek.entity.Login;
import org.jolene.threek.entity.User;
import org.jolene.threek.feature.MutableTransferable;
import org.jolene.threek.repository.EmailContentRepository;
import org.jolene.threek.repository.EmailRepository;
import org.jolene.threek.service.AppService;
import org.jolene.threek.service.LoginService;
import org.jolene.threek.service.ResourceService;
import org.jolene.threek.test.LocalTestConfig;
import org.jolene.threek.web.pages.AbstractPage;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * @author Jolene
 */
@WebAppConfiguration
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class, ServletConfig.class, LocalTestConfig.class})
@Transactional
public abstract class WebTest extends SpringWebTest {


    @Autowired
    protected LoginService loginService;
    @Autowired
    protected ResourceService resourceService;
    @Autowired
    protected EmailRepository emailRepository;
    @Autowired
    protected EmailContentRepository emailContentRepository;
    @Autowired
    protected AppService appService;

    /**
     * 初始化逻辑页面
     * <p>会首先{@link AbstractPage#validatePage() 验证}该页面</p>
     *
     * @param clazz 该页面相对应的逻辑页面的类
     * @param <T>   该页面相对应的逻辑页面
     * @return 页面实例
     */
    public <T extends AbstractPage> T initPage(Class<T> clazz) {
        T page = PageFactory.initElements(driver, clazz);
        page.setResourceService(resourceService);
        page.setTestInstance(this);
        page.validatePage();
        return page;
    }

    /**
     * 模拟一套支付方式出来
     *
     * @param transferable 可更新支付方式的受支付者
     * @param methods      需要更新的支付方式
     */
    protected void updatePaymentDetails(MutableTransferable transferable, PaymentMethod[] methods) {
        for (PaymentMethod method : methods) {
            switch (method) {
                case alipay:
                    transferable.updatePaymentDetails(PaymentMethod.alipay, Collections.singletonMap(""
                            , randomEmailAddress()));
                    break;
                case wxpay:
                    transferable.updatePaymentDetails(PaymentMethod.wxpay, Collections.singletonMap(""
                            , randomEmailAddress()));
                    break;
                default:
                    HashMap<String, String> data = new HashMap<>();
                    data.put("含户名", RandomStringUtils.randomAscii(2 + random.nextInt(3)));
                    data.put("银行名称", RandomStringUtils.randomAscii(4 + random.nextInt(10)));
                    data.put("开户行", RandomStringUtils.randomAscii(7 + random.nextInt(16)));
                    data.put("银行卡号", RandomStringUtils.randomNumeric(10 + random.nextInt(22)));
                    transferable.updatePaymentDetails(PaymentMethod.bank, data);
            }
        }
    }

    /**
     * 增加一个用户,他是由user引导而来的
     *
     * @param user
     */
    protected Collection<User> addNewUserUnder(User user, int number) {
        ArrayList<User> userArrayList = new ArrayList<>();
        while (number-- > 0) {
            User user1 = new User();
            user1.setGuide(user);
            user1.setUsername(UUID.randomUUID().toString());
            loginService.changeLoginWithRawPassword(user1, UUID.randomUUID().toString());
            userArrayList.add(user1);
        }
        return userArrayList;
    }

    /**
     * 发送邮件
     *
     * @param sender   发送者
     * @param receiver 接受者
     * @param count    数量
     * @return 新邮件集合
     */
    protected Collection<Email> sendMail(@NotNull Login sender, @NotNull Login receiver, int count) {
        ArrayList<Email> emails = new ArrayList<>();
        while (count-- > 0) {
            EmailContent content = new EmailContent();
            content.setBelong(sender);
            content.setTitle(UUID.randomUUID().toString());
            content.setSent(true);
            HashSet<String> stringHashSet = new HashSet<>();
            stringHashSet.add(receiver.getUsername());
            content.setRecipients(stringHashSet);
            content = emailContentRepository.save(content);
            Email email = new Email();
            email.setBelong(receiver);
            email.setContent(content);
            emails.add(emailRepository.save(email));
        }
        return emails;
    }
}
