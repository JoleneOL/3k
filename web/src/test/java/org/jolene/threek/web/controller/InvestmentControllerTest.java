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

    }

    @Test
    public void testDoAccept() throws Exception {

    }
}