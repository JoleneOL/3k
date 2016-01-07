package org.jolene.threek.web.controller;

import org.jolene.threek.common.PaymentMethod;
import org.jolene.threek.entity.User;
import org.jolene.threek.repository.ProvideOrderRepository;
import org.jolene.threek.service.AppService;
import org.jolene.threek.web.AuthenticatedWebTest;
import org.jolene.threek.web.LoginAs;
import org.jolene.threek.web.model.ProvideInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
@LoginAs("USER")
public class InvestmentControllerTest extends AuthenticatedWebTest {

    @Autowired
    private AppService appService;
    @Autowired
    private ProvideOrderRepository provideOrderRepository;

    @Test
    public void testDoProvide() throws Exception {

        ProvideInfo info = new ProvideInfo();
        info.setLot(appService.currentSystemConfig().getMaxLots() + 1);

        info.setPaymentMethods(randomArray(PaymentMethod.values(), 1));
        indexPage.provideTooManyLots(info);

        info.setLot(random.nextInt(appService.currentSystemConfig().getMaxLots()) + 1);

        indexPage.provideWithoutTicket(info);

        giveCurrentSomeTicket(1);

        indexPage.provideSuccess(info);

        // 校验数据结果
        assertThat(provideOrderRepository.countOrderingByUse((User) currentUser))
                .isEqualTo(1);

        // 之前只给了一个入场券 所以它应该已经被消耗掉了
        // 先回到首页
        driver.get("http://localhost");
        indexPage.seeExceptTicketCount(0);

        indexPage.provideWithoutTicket(info);

        // 入场券测试已经通过了, 再给它几个
        giveCurrentSomeTicket(appService.currentSystemConfig().getMaxOrders() + 1);

        int count = appService.currentSystemConfig().getMaxOrders() - 1;
        while (count-- > 0) {
            indexPage.provideSuccess(info);
        }

        // 这个时候是无法继续接受帮助的
        indexPage.provideTooManyOrders(info);
        // 校验数据结果
        assertThat(provideOrderRepository.countOrderingByUse((User) currentUser))
                .isEqualTo(appService.currentSystemConfig().getMaxOrders());

    }

    @Test
    public void testDoAccept() throws Exception {

    }
}