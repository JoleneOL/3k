package org.jolene.threek.web.pages;

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

    private void makeLeftPanelVisible() {
        if (!leftPanel.isDisplayed())
            menuToggle.click();
        assertThat(leftPanel.isDisplayed())
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
                .isTrue();
        assertThat(labelForBalance.getText())
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
                .isTrue();
        assertThat(labelForTicketCount.getText())
                .isEqualTo(String.valueOf(count));
    }
}
