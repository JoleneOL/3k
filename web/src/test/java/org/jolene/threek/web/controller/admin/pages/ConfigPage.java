package org.jolene.threek.web.controller.admin.pages;

import org.jolene.threek.SystemConfig;
import org.jolene.threek.web.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
public class ConfigPage extends AbstractPage {

    @FindBy(css = "form")
    private WebElement form;
    private WebElement title;
    @FindBy(css = "button[class~=btn-primary]")
    private WebElement submitButton;

    public ConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void validatePage() {
        assertThat(webDriver.getCurrentUrl())
                .contains("/config");
    }

    public void submit(SystemConfig systemConfig) {
        title.clear();
        title.sendKeys(systemConfig.getTitle());

        // TODO 更多的输入操作

        submitButton.click();
//        return PageFactory.initElements(webDriver, ConfigPage.class);
    }
}
