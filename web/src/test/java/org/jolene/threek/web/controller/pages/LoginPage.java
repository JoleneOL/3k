package org.jolene.threek.web.controller.pages;

import org.jolene.threek.web.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    @Override
    public void validatePage() {
        assertThat(webDriver.getCurrentUrl())
                .contains("/login");
    }

    public IndexPage assertLoginSuccess(String username, String password) {
        doLogin(username, password);
        return testInstance.initPage(IndexPage.class);
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
        LoginPage loginPage = testInstance.initPage(LoginPage.class);

        assertThat(loginPage.getDangerAlertMessage("错误警告"))
                .contains("未找到");

        return loginPage;
    }

    public LoginPage assertLoginWithBadPassword(String username, String password) {
        doLogin(username, password);
        LoginPage loginPage = testInstance.initPage(LoginPage.class);

        assertThat(loginPage.getDangerAlertMessage("错误警告"))
                .contains("密码错误");

        return loginPage;
    }

    public LoginPage assertLoginWithDisabled(String username, String password) {
        doLogin(username, password);
        LoginPage loginPage = testInstance.initPage(LoginPage.class);

        assertThat(loginPage.getDangerAlertMessage("错误警告"))
                .contains("禁用");

        return loginPage;
    }

    public LoginPage assertLoginWithLocked(String username, String password) {
        // TODO 被锁定的账户需求可能会发生变化
        doLogin(username, password);
        LoginPage loginPage = testInstance.initPage(LoginPage.class);

        assertThat(loginPage.getDangerAlertMessage("错误警告"))
                .contains("锁定");

        return loginPage;
    }
}
