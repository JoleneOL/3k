package org.jolene.threek.web.controller.admin.pages;

import org.jolene.threek.SystemConfig;
import org.jolene.threek.support.InterestReward;
import org.jolene.threek.web.pages.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
public class ConfigPage extends AbstractPage {

    @FindBy(css = "form")
    private WebElement form;
    @FindBy(css = "input[name='title']")
    private WebElement title;
    @FindBy(css = "input[name='url']")
    private WebElement url;
    @FindBy(css = "textarea[name='welcomeMessage']")
    private WebElement welcomeMessage;
    @FindBy(css = "textarea[name='regWelcomeMessage']")
    private WebElement regWelcomeMessage;
    @FindBy(css = "input[name=chkOnlyInvite]")
    private WebElement chkOnlyInvite;
    @FindBy(css = "input[name='tags']")
    private WebElement tags;
    @FindBy(css = "#tags_tagsinput")
    private WebElement tagsInputDiv;
    @FindBy(css = "#tags_tag")
    private WebElement tagsInput;
    @FindBy(css = "textarea[name='userHelpMessage']")
    private WebElement userHelpMessage;
    @FindBy(css = "textarea[name='indexTopNotice']")
    private WebElement indexTopNotice;
    @FindBy(css = "textarea[name='indexBottomNotice']")
    private WebElement indexBottomNotice;
    @FindBy(css = "input[name='stock']")
    private WebElement stock;
    @FindBy(css = "input[name='maxOrders']")
    private WebElement maxOrders;
    @FindBy(css = "input[name='maxLots']")
    private WebElement maxLots;
    @FindBy(css = "input[name='queueDays']")
    private WebElement queueDays;
    @FindBy(css = "input[name='rate']")
    private WebElement rate;
    @FindBy(css = "input[name='maxOperateHours']")
    private WebElement maxOperateHours;
    @FindBy(css = "input[name='inspectStartDayOfMonth']")
    private WebElement inspectStartDayOfMonth;
    @FindBy(css = "input[name='inspectEndDayOfMonth']")
    private WebElement inspectEndDayOfMonth;
    @FindBy(css = "input[name='directRewardRate']")
    private WebElement directRewardRate;
    @FindBy(css = "input[name='directRewardRate2']")
    private WebElement directRewardRate2;
    @FindBy(css = "input[name='interestRewards']")
    private WebElement interestRewards;
    @FindBy(css = "input[name='leaderStopRecommends']")
    private WebElement leaderStopRecommends;
    @FindBy(css = "input[name='leaderStopMembers']")
    private WebElement leaderStopMembers;
    @FindBy(css = "input[name='leaderStopInvests']")
    private WebElement leaderStopInvests;
    @FindBy(css = "input[name='leaderRate']")
    private WebElement leaderRate;

    //    private WebElement
    @FindBy(css = "button[class~=btn-primary]")
    private WebElement submitButton;

    public ConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void validatePage() {
        assertThat(webDriver.getCurrentUrl())
                .contains("/config");
    }

    /**
     * 页面初始化完成以后验证表单
     *
     * @param systemConfig
     */
    public void validateForm(SystemConfig systemConfig) {
        //验证rewardSerialization
        String rewardSerialization = "";
        for (Map.Entry<Integer, InterestReward> entry : systemConfig.getInterestRewards().entrySet()) {
            double interestRate = entry.getValue().getRate();
            int interestReach = entry.getValue().getReach();
            int interestMax = entry.getValue().getMax();
            rewardSerialization += interestRate + "," + interestReach + "," + interestMax + "|";
        }
        rewardSerialization = rewardSerialization.substring(0, rewardSerialization.length() - 1);
        String fromRewardSerialization = form.findElement(By.cssSelector("input[name=rewardSerialization]")).getAttribute("value");
        assertThat(fromRewardSerialization)
                .isEqualTo(rewardSerialization);
    }

    public void submit(SystemConfig systemConfig) {
        title.clear();
        title.sendKeys(systemConfig.getTitle());
        url.clear();
        url.sendKeys(systemConfig.getUrl());
        welcomeMessage.clear();
        welcomeMessage.sendKeys(systemConfig.getWelcomeMessage());
        regWelcomeMessage.clear();
        regWelcomeMessage.sendKeys(systemConfig.getRegWelcomeMessage());

        //设置为选中状态
        if (StringUtils.isEmpty(chkOnlyInvite.getAttribute("checked"))) {
            chkOnlyInvite.click();
        }
        if (!systemConfig.isOnlyInvite()) {
            chkOnlyInvite.click();
        }

        String[] welcomeFeatures = systemConfig.getWelcomeFeatures();
        String welcomeFeatureStr = "";
        tags.clear();
        //执行点击事件删除原有的tag
        List<WebElement> preTags = tagsInputDiv.findElements(By.tagName("a"));
        if (preTags.size() > 0) {
            preTags.get(0).click();
        }

        for (String welcomeFeature : welcomeFeatures) {
            tagsInput.clear();
            tagsInput.sendKeys(welcomeFeature);
            welcomeFeatureStr += welcomeFeature + ",";
            indexTopNotice.click();
        }

        List<WebElement> tagsSpan = tagsInputDiv.findElements(By.className("tag"));
        assertThat(welcomeFeatures.length)
                .isEqualTo(tagsSpan.size());
        assertThat(welcomeFeatures[0])
                .isEqualTo(tagsSpan.get(0).findElement(By.tagName("span")).getText().trim());
        assertThat(tags.getAttribute("value").trim())
                .isEqualTo(welcomeFeatureStr.substring(0, welcomeFeatureStr.length() - 1));

        WebConnectionHtmlUnitDriver webConnectionHtmlUnitDriver = (WebConnectionHtmlUnitDriver) webDriver;
        webConnectionHtmlUnitDriver.executeScript("configHandler.richEdit('#userHelpMessage_panel','"
                + systemConfig.getUserHelpMessage() + "')");
//        userHelpMessage.clear();
//        userHelpMessage.sendKeys("");

        WebElement indexTopNotice_panel = form.findElement(By.id("indexTopNotice_panel"));
//        indexTopNotice_panel.findElement()
        indexTopNotice.clear();
        indexTopNotice.sendKeys(systemConfig.getIndexTopNotice());
        indexBottomNotice.clear();
        indexBottomNotice.sendKeys(systemConfig.getIndexBottomNotice());
        stock.clear();
        stock.sendKeys(String.valueOf(systemConfig.getStock()));
        maxOrders.clear();
        maxOrders.sendKeys(String.valueOf(systemConfig.getMaxOrders()));
        maxLots.clear();
        maxLots.sendKeys(String.valueOf(systemConfig.getMaxLots()));
        queueDays.clear();
        queueDays.sendKeys(String.valueOf(systemConfig.getQueueDays()));
        rate.clear();
        rate.sendKeys(String.valueOf(systemConfig.getRate()));
        maxOperateHours.clear();
        maxOperateHours.sendKeys(String.valueOf(systemConfig.getMaxOperateHours()));
        inspectStartDayOfMonth.clear();
        inspectStartDayOfMonth.sendKeys(String.valueOf(systemConfig.getInspectStartDayOfMonth()));
        inspectEndDayOfMonth.clear();
        inspectEndDayOfMonth.sendKeys(String.valueOf(systemConfig.getInspectEndDayOfMonth()));

        Map<Integer, InterestReward> interestRewardMap = systemConfig.getInterestRewards();

        interestRewardMap.forEach((sg, interestReward) -> {
            WebElement interestRate = form.findElement(By.cssSelector("input[name='interestRate" + sg + "']"));
            interestRate.clear();
            interestRate.sendKeys(String.valueOf(interestReward.getRate()));
            WebElement interestReach = form.findElement(By.cssSelector("input[name='interestReach" + sg + "']"));
            interestReach.clear();
            interestReach.sendKeys(String.valueOf(interestReward.getReach()));
            WebElement interestMax = form.findElement(By.cssSelector("input[name='interestMax" + sg + "']"));
            interestMax.clear();
            interestMax.sendKeys(String.valueOf(interestReward.getMax()));
        });

        leaderStopRecommends.clear();
        leaderStopRecommends.sendKeys(String.valueOf(systemConfig.getLeaderStopRecommends()));
        leaderStopMembers.clear();
        leaderStopMembers.sendKeys(String.valueOf(systemConfig.getLeaderStopMembers()));
        leaderStopInvests.clear();
        leaderStopInvests.sendKeys(String.valueOf(systemConfig.getLeaderStopInvests()));
        leaderRate.clear();
        leaderRate.sendKeys(String.valueOf(systemConfig.getLeaderRate()));

        submitButton.click();


        List<WebElement> errors = webDriver.findElements(By.className("error"));

        assertThat(errors)
                .isEmpty();
        // FindsByLinkText
        System.out.println(webDriver.getPageSource());

//        PageFactory.initElements(webDriver, this);
//        validateForm(systemConfig);
//        WebElement msg = webDriver.findElement(By.cssSelector("#gritter-notice-wrapper p"));
//        assertThat(msg.getText())
//                .isEqualTo("保存成功");
//        webDriver.navigate().refresh();
//        PageFactory.initElements(webDriver, this);
//        try {
//            webDriver.findElement(By.cssSelector("#gritter-notice-wrapper"));
//            assertThat(true).isTrue();
//        } catch (NoSuchElementException ignored) {
//        }

//        return PageFactory.initElements(webDriver, ConfigPage.class);
    }
}
