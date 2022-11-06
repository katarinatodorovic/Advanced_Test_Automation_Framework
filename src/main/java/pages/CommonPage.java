package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public abstract class CommonPage extends BasePageCLass{

    protected final By pageTitleLocator = By.xpath("//div[contains(@class, 'panel-title')]");

    /**
     * A constructor for a WebDriver which is used by other
     * (children) classes
     *
     * @param driver {WebDriver} - WebDriver
     */
    public CommonPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method that checks when we are on some page from navigation bar,
     * the corresponding page title is displayed
     *
     * @return {boolean} - if page title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(pageTitleLocator);
    }

    /**
     * Method that checks if the page title is displayed on
     * page and gets page title
     *
     * @return {String} - page title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed");
        WebElement pageTitle = getWebElement(pageTitleLocator);
        return getTextFromWebElement(pageTitle);
    }
}
