package org.jolene.threek.web.controller;

import org.jolene.threek.entity.User;
import org.jolene.threek.service.InformationService;
import org.jolene.threek.web.AuthenticatedWebTest;
import org.jolene.threek.web.LoginAs;
import org.jolene.threek.web.controller.pages.ProfilePage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
@LoginAs("user")
public class UserControllerTest extends AuthenticatedWebTest {

    @Autowired
    private InformationService informationService;

    @Test
    public void testMyProfile() throws Exception {
        ProfilePage profilePage = indexPage.clickMyProfile();
        // 校验手机号码什么的

        assertThat(profilePage.getUserMobileText())
                .isEqualTo(currentUser.getUsername());
    }

    @Test
    public void testProfile() throws Exception {
        // 这会儿是看别人的
        Collection<User> userCollection = addNewUserUnder((User) currentUser, 1 + random.nextInt(3));
        indexPage.refresh();
        // 抓去新用户列表里的 然后点击它

        Map<String, User> userMap = new HashMap<>(userCollection.size());

        indexPage.seeExceptNewUsers(userCollection, (user, li) -> li.findElements(By.tagName("a")).forEach(link -> {
            userMap.put(link.getAttribute("href"), user);
        }));

        userMap.forEach((href, user) -> {
            driver.navigate().to(href);
            ProfilePage profilePage = UserControllerTest.this.initPage(ProfilePage.class);

            assertThat(profilePage.getUserMobileText())
                    .isEqualTo(informationService.mosaic(user.getUsername()));

        });
    }
}