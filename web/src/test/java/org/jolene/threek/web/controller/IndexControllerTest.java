package org.jolene.threek.web.controller;

import org.jolene.threek.web.AuthenticatedWebTest;
import org.jolene.threek.web.LoginAs;
import org.junit.Test;

/**
 * @author Jolene
 */
public class IndexControllerTest extends AuthenticatedWebTest {

    @Test
    @LoginAs("USER")
    public void testIndex() throws Exception {
        System.out.println(driver.getPageSource());
        System.out.println(indexPage);
    }
}