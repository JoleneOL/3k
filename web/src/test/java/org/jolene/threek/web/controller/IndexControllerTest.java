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

        indexPage.noModals();

        indexPage.clickProviderForModal();

        indexPage.clickAccepterForModal();
    }
}