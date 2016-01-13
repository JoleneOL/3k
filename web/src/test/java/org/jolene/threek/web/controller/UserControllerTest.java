package org.jolene.threek.web.controller;

import org.jolene.threek.web.AuthenticatedWebTest;
import org.jolene.threek.web.LoginAs;
import org.jolene.threek.web.controller.pages.ProfilePage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
@LoginAs("user")
public class UserControllerTest extends AuthenticatedWebTest {

    @Test
    public void testMyProfile() throws Exception {
        ProfilePage profilePage = indexPage.clickMyProfile();
        // 校验手机号码什么的
        System.out.println(profilePage);

        assertThat(profilePage.getMobile().getText())
                .isEqualTo(currentUser.getUsername());
    }

    @Test
    public void testProfile() throws Exception {

    }
}