package org.jolene.threek.web.controller.pages;

import org.jolene.threek.web.pages.AbstractPage;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 注册页面
 *
 * @author Jolene
 */
public class RegisterPage extends AbstractPage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void validatePage() {
        assertThat(webDriver.getTitle())
                .contains("加入");
    }
}
