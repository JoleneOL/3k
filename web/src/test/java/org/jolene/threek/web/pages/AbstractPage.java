package org.jolene.threek.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Jolene
 */
public abstract class AbstractPage {
    protected WebDriver webDriver;

    @FindBy(className = "growl-danger")
    protected WebElement dangerAlert;

    public AbstractPage(WebDriver driver) {
        this.webDriver = driver;
    }

    // class="gritter-item-wrapper growl-danger" role="alert"
}
