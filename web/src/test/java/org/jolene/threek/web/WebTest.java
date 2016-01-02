package org.jolene.threek.web;

import libspringtest.SpringWebTest;
import org.jolene.threek.CoreConfig;
import org.jolene.threek.test.LocalTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Jolene
 */
@WebAppConfiguration
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class, ServletConfig.class, LocalTestConfig.class})
public abstract class WebTest extends SpringWebTest {
}
