package org.jolene.threek.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

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
     * @return true 页面没有警告信息则返回true
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

    /**
     * @param message 如果没有显示使用的错误信息
     * @return 获取危险提示框显示的文字
     * @see #dangerAlert
     */
    public String getDangerAlertMessage(String message) {
        assertThat(dangerAlert.isDisplayed())
                .as(message)
                .isTrue();

        try {
            WebElement p = dangerAlert.findElement(By.cssSelector("p"));
            return p.getText();
        } catch (NotFoundException ex) {
            throw new AssertionError(message);
        }

    }

    // class="gritter-item-wrapper growl-danger" role="alert"
}
