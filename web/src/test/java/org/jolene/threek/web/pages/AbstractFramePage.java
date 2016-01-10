package org.jolene.threek.web.pages;

import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.jolene.threek.entity.Email;
import org.jolene.threek.entity.User;
import org.jolene.threek.web.dialect.process.EmailHrefProcessor;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.thymeleaf.util.NumberUtils;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

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
     * 页面header中出现的展开式信息通知栏测试
     *
     * @param newNotices            需要看到的通知集合
     * @param className             该通知所出现按钮的class
     * @param groupDisplayAssertion 校验该div是否存在的校验器消耗者
     * @param h5TextAssertion       校验该div包括的h5元素的校验器消耗者
     * @param showAllLinkAssertion  最终总是会有一个显示所有的a 这个就是它的校验者
     * @param eachLiAssertion       每一个li元素最终都可以解释为一个通知 兼任校验的工作
     * @param <T>                   通知类型 比如用户,邮件,订单
     */
    protected <T> void seeDropdownGroupInHeader(Collection<T> newNotices, String className
            , Consumer<AbstractBooleanAssert> groupDisplayAssertion
            , Consumer<AbstractCharSequenceAssert<?, String>> h5TextAssertion, Consumer<WebElement> showAllLinkAssertion
            , Function<WebElement, T> eachLiAssertion) {

        WebElement newNoticeGroup = findGroup(className);
        assertThat(newNoticeGroup)
                .isNotNull();

        assert newNoticeGroup != null;

        AbstractBooleanAssert booleanAssert = assertThat(newNoticeGroup.isDisplayed());
        groupDisplayAssertion.accept(booleanAssert);
        booleanAssert.isTrue();

        assertThat(newNoticeGroup.findElement(By.className("badge")).getText())
                .isEqualTo(NumberUtils.format(newNotices.size(), 0, Locale.CHINA));

        WebElement h5 = newNoticeGroup.findElement(By.tagName("h5"));
        WebElement toggle = newNoticeGroup.findElement(By.tagName("button"));

        if (!h5.isDisplayed()) {
            toggle.click();
        }
        assertThat(h5.isDisplayed())
                .isTrue();
        h5TextAssertion.accept(assertThat(h5.getText()));
        //element element	div p	Selects all <p> elements inside <div> elements
        // 获取 div 里面所有的p
        //element>element	div > p	Selects all <p> elements where the parent is a <div> element
        // 获取 div 里面所有的直接子代p
        //element+element	div + p	Selects all <p> elements that are placed immediately after <div> elements
        // 获取 div 贴着的p
        //element1~element2	p ~ ul	Selects every <ul> element that are preceded by a <p> element
        // 获取 p  以后所有的ul
        List<WebElement> userUIs = newNoticeGroup.findElements(By.cssSelector("ul > li"));

        // 最后一个是所有用户的连接
        WebElement allUsersLinkLI = userUIs.remove(userUIs.size() - 1);
        // 验证链接
        // showAllLinkAssertion 暂时不用~

        Collection<T> cloneNotices = new ArrayList<>(newNotices);
        for (WebElement userUI : userUIs) {
            // div a img
            T notice = eachLiAssertion.apply(userUI);
            cloneNotices.remove(notice);
        }
        assertThat(cloneNotices.isEmpty())
                .as("所有通知都已经找到了")
                .isTrue();

    }

    /**
     * 看到新的直接下线用户
     *
     * @param newUsers 新的下线用户
     */
    public void seeExceptNewUsers(Collection<User> newUsers) throws IOException {
        seeDropdownGroupInHeader(newUsers, "glyphicon-user", assertion -> assertion.as("看到新下线用户logo")
                , assertion -> assertion.isEqualTo("" + newUsers.size() + "个新的直接下线"), null, li -> {
                    WebElement logo = li.findElement(By.tagName("img"));
                    WebElement link = li.findElement(By.cssSelector("h5 > a"));
                    User nowUser = newUsers.stream().filter(user -> user.getHumanReadName().equals(link.getText())).findAny().get();
                    try {
                        assertThat(logo.getAttribute("src"))
                                .isEqualTo(resourceService.getResource(nowUser.getLogoPath()).getURI().toString());
                    } catch (IOException ignored) {

                    }
                    return nowUser;
                });
    }

    /**
     * 看到新的未读邮件
     *
     * @param newEmails 未读邮件组
     */
    public void seeExceptNewEmails(Collection<Email> newEmails) {
        seeDropdownGroupInHeader(newEmails, "glyphicon-envelope", assertion -> assertion.as("看到未读邮件logo")
                , assertion -> assertion.isEqualTo("" + newEmails.size() + "个新邮件"), null, li -> {
                    WebElement logo = li.findElement(By.tagName("img"));
                    WebElement link = li.findElement(By.tagName("a"));
                    WebElement name = li.findElement(By.className("name"));
                    WebElement msg = li.findElement(By.className("msg"));

                    // 通过连接获取邮件实体
                    Long id = EmailHrefProcessor.emailIdFromHref(link.getAttribute("href"));
                    Email email = newEmails.stream().filter(email1 -> id.equals(email1.getId()))
                            .findAny().get();
                    try {
                        assertThat(logo.getAttribute("src"))
                                .as("发件人头像")
                                .isEqualTo(resourceService.getResource(email.getContent().getBelong().getLogoPath()).getURI().toString());
                    } catch (IOException ignored) {

                    }
                    assertThat(name.getText())
                            .as("发件人名称")
                            .isEqualTo(StringUtils.abbreviate(email.getContent().getBelong().getHumanReadName(), 16));

                    assertThat(msg.getText())
                            .as("")
                            .isEqualTo(StringUtils.abbreviate(email.getContent().getTitle(), 22));

                    return email;
                });
    }
}
