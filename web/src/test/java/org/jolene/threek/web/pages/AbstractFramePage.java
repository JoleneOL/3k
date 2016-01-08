package org.jolene.threek.web.pages;

import org.jolene.threek.entity.User;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.thymeleaf.util.NumberUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
     * 从所有的&lt;div class="btn-group"/&gt>中寻找含有指定class的div
     *
     * @param className 指定class
     * @return 相关div
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

    /**
     * 看到新的直接下线用户
     *
     * @param newUsers 新的下线用户
     */
    public void seeExceptNewUsers(Collection<User> newUsers) throws IOException {
        WebElement newUserGroup = findGroup("glyphicon-user");
        assertThat(newUserGroup)
                .isNotNull();

        assert newUserGroup != null;
        assertThat(newUserGroup.isDisplayed())
                .as("看到新下线用户logo")
                .isTrue();

        assertThat(newUserGroup.findElement(By.className("badge")).getText())
                .isEqualTo(NumberUtils.format(newUsers.size(), 0, Locale.CHINA));

        WebElement h5 = newUserGroup.findElement(By.tagName("h5"));
        WebElement toggle = newUserGroup.findElement(By.tagName("button"));

        if (!h5.isDisplayed()) {
            toggle.click();
        }
        assertThat(h5.isDisplayed())
                .isTrue();
        assertThat(h5.getText())
                .isEqualTo("" + newUsers.size() + "个新的直接下线");

        //element element	div p	Selects all <p> elements inside <div> elements
        // 获取 div 里面所有的p
        //element>element	div > p	Selects all <p> elements where the parent is a <div> element
        // 获取 div 里面所有的直接子代p
        //element+element	div + p	Selects all <p> elements that are placed immediately after <div> elements
        // 获取 div 贴着的p
        //element1~element2	p ~ ul	Selects every <ul> element that are preceded by a <p> element
        // 获取 p  以后所有的ul
        List<WebElement> userUIs = newUserGroup.findElements(By.cssSelector("ul > li"));

        // 最后一个是所有用户的连接
        WebElement allUsersLinkLI = userUIs.remove(userUIs.size() - 1);
        // 验证链接

        Collection<User> cloneUsers = new ArrayList<>(newUsers);
        for (WebElement userUI : userUIs) {
            // div a img
            WebElement logo = userUI.findElement(By.tagName("img"));
            WebElement link = userUI.findElement(By.cssSelector("h5 > a"));
            User nowUser = cloneUsers.stream().filter(user -> user.getHumanReadName().equals(link.getText())).findAny().get();
            cloneUsers.remove(nowUser);

            assertThat(logo.getAttribute("src"))
                    .isEqualTo(resourceService.getResource(nowUser.getLogoPath()).getURI().toString());

            // 验证所有链接
        }
        assertThat(cloneUsers.isEmpty())
                .as("所有用户都已经找到了")
                .isTrue();
    }
}
