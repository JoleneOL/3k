package org.jolene.threek.web.controller.pages;

import org.jolene.threek.web.model.RegisterInfo;
import org.jolene.threek.web.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 注册页面
 *
 * @author Jolene
 */
public class RegisterPage extends AbstractPage {

    private WebElement code;
    private WebElement mobile;
    private WebElement password;
    private WebElement password2;
    @FindBy(className = "btn-success")
    private WebElement button;
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void validatePage() {
        assertThat(webDriver.getTitle())
                .contains("加入");
    }

    private void fillFormAndSubmit(RegisterInfo info) {
        code.clear();
        code.sendKeys(info.getCode());
        mobile.clear();
        mobile.sendKeys(info.getMobile());
        password.clear();
        password.sendKeys(info.getPassword());
        password2.clear();
        password2.sendKeys(info.getPassword2());
    }

    public void registerWithoutCode(RegisterInfo info) {
        fillFormAndSubmit(info);
        assertThat(getDangerAlertMessage("没有邀请码的错误警告"))
                .contains("邀请码");
    }


    public void registerWithIllegalCode(RegisterInfo info) {
        fillFormAndSubmit(info);
        assertThat(getDangerAlertMessage("邀请码错误的错误警告"))
                .contains("邀请码");
    }

    public void registerWithIllegalMobile(RegisterInfo info) {
        fillFormAndSubmit(info);
        assertThat(getDangerAlertMessage("手机号码错误的错误警告"))
                .contains("手机");
    }

    public void registerWithBadPassword(RegisterInfo info) {
        fillFormAndSubmit(info);
        assertThat(getDangerAlertMessage("密码有问题的错误警告"))
                .contains("密码");
    }

    public IndexPage registerSuccess(RegisterInfo info) {
        return null;
    }
}
