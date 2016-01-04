package org.jolene.threek.web.pages;

import org.openqa.selenium.NoSuchElementException;
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
    @FindBy(className = "growl-info")
    protected WebElement infoAlert;

    public AbstractPage(WebDriver driver) {
        this.webDriver = driver;
    }

    /**
     * 校验页面是否清爽 是否没有任何弹出框
     *
     * @param page 校验页面
     * @return
     */
    public static boolean isClean(AbstractPage page) {
        try {
            if (page.dangerAlert.isDisplayed())
                return false;
        } catch (NoSuchElementException ignored) {

        }
        try {
            if (page.infoAlert.isDisplayed())
                return false;
        } catch (NoSuchElementException ignored) {

        }
        return true;
    }

    // class="gritter-item-wrapper growl-danger" role="alert"
}
