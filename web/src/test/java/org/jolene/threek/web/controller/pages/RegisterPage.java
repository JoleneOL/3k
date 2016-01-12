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
        if (info.getCode() != null)
            code.sendKeys(info.getCode());
        mobile.clear();
        if (info.getMobile() != null)
            mobile.sendKeys(info.getMobile());
        password.clear();
        if (info.getPassword() != null)
            password.sendKeys(info.getPassword());
        password2.clear();
        if (info.getPassword2() != null)
            password2.sendKeys(info.getPassword2());

        button.click();
    }

    public void registerWithoutCode(RegisterInfo info) {
        fillFormAndSubmit(info);
        assertThat(validateLabelFor(code).isDisplayed())
                .isTrue();
        assertThat(validateLabelFor(code).getText())
                .contains("必填");
    }


    public void registerWithIllegalCode(RegisterInfo info) {
        fillFormAndSubmit(info);
        assertThat(getDangerAlertMessage("邀请码错误的错误警告"))
                .contains("邀请码");
    }

    public void registerWithIllegalMobile(RegisterInfo info) {
        fillFormAndSubmit(info);
        assertThat(validateLabelFor(mobile).isDisplayed())
                .isTrue();
        assertThat(validateLabelFor(mobile).getText())
                .contains("手机");
    }

    public void registerWithBadPassword(RegisterInfo info) {
        fillFormAndSubmit(info);
        assertThat(validateLabelFor(password).isDisplayed())
                .isTrue();
        assertThat(validateLabelFor(password).getText())
                .contains("密码");
    }

    public IndexPage registerSuccess(RegisterInfo info) {
        return null;
    }
}
