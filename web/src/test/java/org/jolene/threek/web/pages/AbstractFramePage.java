package org.jolene.threek.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.thymeleaf.util.NumberUtils;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 展示整个框架的页面
 * <p>指的是那些还引入了left right header 之类的页面</p>
 *
 * @author Jolene
 */
public abstract class AbstractFramePage extends AbstractPage {
    @FindBy(className = "leftpanel")
    private WebElement leftPanel;
    @FindBy(className = "menutoggle")
    private WebElement menuToggle;
    @FindBy(css = "h4[about=balance]")
    private WebElement labelForBalance;
    @FindBy(css = "h4[about=ticketCount]")
    private WebElement labelForTicketCount;

    public AbstractFramePage(WebDriver driver) {
        super(driver);
    }

    /**
     * 保证左侧界面处于可见范围
     */
    protected void makeLeftPanelVisible() {
        if (!leftPanel.isDisplayed())
            menuToggle.click();
        assertThat(leftPanel.isDisplayed())
                .as("点击菜单之后应该可以看到左侧界面")
                .isTrue();
    }

    /**
     * 看到了余额
     *
     * @param balance 期望的余额
     */
    public void seeExceptBalance(double balance) {
        makeLeftPanelVisible();
        assertThat(labelForBalance.isDisplayed())
                .as("账户余额应该可见")
                .isTrue();
        assertThat(labelForBalance.getText())
                .as("账户余额数据应该正确")
                .isEqualTo(NumberUtils.format(balance, 1, 2, Locale.CHINA));
    }

    /**
     * 看到了剩余入场券
     *
     * @param count 期望的数量
     */
    public void seeExceptTicketCount(int count) {
        makeLeftPanelVisible();
        assertThat(labelForTicketCount.isDisplayed())
                .as("剩余入场券应该可见")
                .isTrue();
        assertThat(labelForTicketCount.getText())
                .as("剩余入场券数据应该正确")
                .isEqualTo(String.valueOf(count));
    }

    /**
     * 看到新的直接下线用户
     *
     * @param count 0
     */
    public void seeExceptNewUsers(int count) {
        WebElement newUserGroup = findGroup("glyphicon-user");
        assertThat(newUserGroup)
                .isNotNull();

        assert newUserGroup != null;
        assertThat(newUserGroup.isDisplayed())
                .as("看到新下线用户logo")
                .isTrue();

        assertThat(newUserGroup.findElement(By.className("badge")).getText())
                .isEqualTo(NumberUtils.format(count, 0, Locale.CHINA));

    }

    /**
     * 从所有的&lt;div class="btn-group"/&gt>中寻找含有指定class的div
     *
     * @param className
     * @return
     */
    private WebElement findGroup(String className) {
        for (WebElement group : webDriver.findElements(By.cssSelector("div[class=btn-group]"))) {
            try {
                group.findElement(By.className(className));
                return group;
            } catch (NotFoundException ignored) {

            }
        }
        return null;
    }
}
