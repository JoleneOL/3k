package org.jolene.threek.web.controller;

import org.jolene.threek.web.AuthenticatedWebTest;
import org.jolene.threek.web.LoginAs;
import org.junit.Test;

/**
 * @author Jolene
 */
public class IndexControllerTest extends AuthenticatedWebTest {

    /**
     * 普通用户
     *
     * @throws Exception
     */
    @Test
    @LoginAs("USER")
    public void normal() throws Exception {
        System.out.println(driver.getPageSource());
        System.out.println(indexPage);

        // 当前无弹窗
        indexPage.noModals();

        // 测试点击 提供帮助
        indexPage.clickProviderForModal();

        // 测试点击 接受帮助
        indexPage.clickAccepterForModal();

        // TODO 首页还应该看到账户的明细 巴拉巴拉
    }
}