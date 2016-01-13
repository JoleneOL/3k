package org.jolene.threek.web.controller.pages;

import org.jolene.threek.web.pages.AbstractFramePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
public class ProfilePage extends AbstractFramePage {
    @FindBy(css = ".fa-mobile-phone ~ span")
    private WebElement mobile;

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public WebElement getMobile() {
        return mobile;
    }

    @Override
    public void validatePage() {
        WebElement logo = webDriver.findElement(By.tagName("h2"));
        assertThat(logo.isDisplayed())
                .isTrue();
        assertThat(logo.getText())
                .contains("个人信息");
    }
}
