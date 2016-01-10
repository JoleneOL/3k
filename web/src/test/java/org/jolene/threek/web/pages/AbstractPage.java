package org.jolene.threek.web.pages;

import org.jolene.threek.service.ResourceService;
import org.jolene.threek.web.WebTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
public abstract class AbstractPage {
    protected WebDriver webDriver;
    /**
     * 单元测试实例
     */
    protected WebTest testInstance;
    /**
     * 相关资源服务
     */
    protected ResourceService resourceService;
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

    public void setTestInstance(WebTest testInstance) {
        this.testInstance = testInstance;
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 刷新当前页面,跟浏览器刷新是一致的,并在完成之后调用验证
     */
    public void refresh() {
        webDriver.navigate().refresh();
        PageFactory.initElements(webDriver, this);
        validatePage();
    }

    /**
     * 最基础的验证页面,以校验这个页面是否为我们所需要的逻辑页面
     */
    public abstract void validatePage();

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
