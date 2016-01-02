package org.jolene.threek.service;

import org.jolene.threek.CoreTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jolene
 */
public class AppServiceTest extends CoreTest {

    @Autowired
    private AppService appService;

    @Test
    public void testCurrentSystemConfig() throws Exception {
        System.out.println(appService);
    }
}