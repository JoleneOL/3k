package org.jolene.threek.web.pages;

import org.openqa.selenium.WebDriver;

/**
 * @author Jolene
 */
public abstract class AbstractPage {
    protected WebDriver webDriver;

    public AbstractPage(WebDriver driver) {
        this.webDriver = driver;
    }
}
