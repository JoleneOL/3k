package org.jolene.threek.web.controller.pages;

import org.jolene.threek.web.pages.AbstractFramePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
public class HelpPage extends AbstractFramePage {
    public HelpPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void validatePage() {
        WebElement logo = webDriver.findElement(By.tagName("h2"));
        assertThat(logo.isDisplayed())
                .isTrue();
        assertThat(logo.getText())
                .contains("帮助");
    }
}
