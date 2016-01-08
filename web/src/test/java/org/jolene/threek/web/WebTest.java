package org.jolene.threek.web;

import libspringtest.SpringWebTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.jolene.threek.CoreConfig;
import org.jolene.threek.common.PaymentMethod;
import org.jolene.threek.entity.User;
import org.jolene.threek.feature.MutableTransferable;
import org.jolene.threek.service.LoginService;
import org.jolene.threek.test.LocalTestConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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

    protected String randomEmailAddress() {
        return RandomStringUtils.randomAscii(random.nextInt(5) + 3)
                + "@"
                + RandomStringUtils.randomAscii(random.nextInt(5) + 3)
                + "."
                + RandomStringUtils.randomAscii(random.nextInt(2) + 2);
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
}
