package org.jolene.threek.web.controller.pages;

import org.jolene.threek.web.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Jolene
 */
public class IndexPage extends AbstractPage {
    public IndexPage(WebDriver driver) {
        super(driver);
    }

    public static IndexPage at(WebDriver driver) {
//        assertThat(driver.getCurrentUrl())
//                .contains("/login");
        return PageFactory.initElements(driver, IndexPage.class);
    }
}
