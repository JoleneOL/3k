package org.jolene.threek.web.controller.pages;

import org.jolene.threek.web.model.ProvideInfo;
import org.jolene.threek.web.pages.AbstractFramePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jolene
 */
public class IndexPage extends AbstractFramePage {


    @FindBy(css = "button[name=provider]")
    private WebElement providerButton;
    @FindBy(css = "button[name=accepter]")
    private WebElement accepterButton;

    private WebElement acceptModal;
    private WebElement provideModal;

    public IndexPage(WebDriver driver) {
        super(driver);
    }

    public static IndexPage at(WebDriver driver) {
//        assertThat(driver.getCurrentUrl())
//                .contains("/login");
        return PageFactory.initElements(driver, IndexPage.class);
    }


    public void noModals() {
        assertThat(acceptModal.isDisplayed())
                .as("接受帮助弹出框不可见")
                .isFalse();

        assertThat(provideModal.isDisplayed())
                .as("提供帮助弹出框不可见")
                .isFalse();
    }

    @SuppressWarnings("Duplicates")
    public void clickProviderForModal() throws InterruptedException {
        providerButton.click();

        // UI上为了美感 增加了延时,所以我们的操作员 也需要等待1秒再查看结果
        Thread.sleep(1000L);

        assertThat(provideModal.isDisplayed())
                .as("提供帮助弹出框可见")
                .isTrue();

        // 再点击关闭
        provideModal.findElement(By.cssSelector("button[data-dismiss=modal]")).click();

        Thread.sleep(1000L);

        assertThat(provideModal.isDisplayed())
                .as("提供帮助弹出框不可见")
                .isFalse();
    }

    @SuppressWarnings("Duplicates") // 这里的代码允许重复.
    public void clickAccepterForModal() throws InterruptedException {
        accepterButton.click();

        // UI上为了美感 增加了延时,所以我们的操作员 也需要等待1秒再查看结果
        Thread.sleep(1000L);

        assertThat(acceptModal.isDisplayed())
                .as("接受帮助弹出框可见")
                .isTrue();

        // 再点击关闭
        acceptModal.findElement(By.cssSelector("button[data-dismiss=modal]")).click();

        Thread.sleep(1000L);

        assertThat(acceptModal.isDisplayed())
                .as("接受帮助弹出框不可见")
                .isFalse();
    }

    /**
     * TODO 提交一个手数过多的订单
     *
     * @param info
     */
    public void provideTooManyLots(ProvideInfo info) {

    }

    /**
     * TODO 提交一个合法的订单
     *
     * @param info
     */
    public void provideSuccess(ProvideInfo info) {

    }

    /**
     * TODO 提交了订单 但是没有入场券
     *
     * @param info
     */
    public void provideWithoutTicket(ProvideInfo info) {

    }
}
