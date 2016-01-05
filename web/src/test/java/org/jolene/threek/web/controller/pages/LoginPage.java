package org.jolene.threek.web.controller.pages;

import org.jolene.threek.web.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 登录页面
 *
 * @author Jolene
 */
public class LoginPage extends AbstractPage {
    private WebElement username;
    private WebElement password;
    @FindBy(css = "button[class~=btn-success]")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public static LoginPage at(WebDriver driver) {
        assertThat(driver.getCurrentUrl())
                .contains("/login");
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public IndexPage assertLoginSuccess(String username, String password) {
        doLogin(username, password);
        return IndexPage.at(webDriver);
    }

    private void doLogin(String username, String password) {
        this.username.clear();
        this.username.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);
        submitButton.click();
    }

    public LoginPage assertLoginWithBadUsername(String username, String password) {
        doLogin(username, password);
        LoginPage loginPage = LoginPage.at(webDriver);

        assertThat(loginPage.getDangerAlertMessage("错误警告"))
                .contains("用户名");

        return loginPage;
    }
}
