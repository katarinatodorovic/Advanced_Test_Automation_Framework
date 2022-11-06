package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HomePage extends CommonLoggedInPage{
    private final String HOME_PAGE_URL = getPageUrl(PageUrlPaths.HOME_PAGE);

    // Home page locators
    private final By pageTitleLocator = By.xpath("//div[@class='my-jumbotron']/h1");

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public HomePage(WebDriver driver) {
        super(driver);
        log.trace("new HomePage()");
    }

    /**
     * Method that returns an instance of the HomePage if bVerify is true
     * then it verifies if we are on the HomePage, and wait for loading of
     * the HomePage to be completed, if it is false then it returns an
     * instance of the HomePage. This method can be used for negative
     * scenario also - e.g. can we open the HomePage even if we are logged
     * out or only when we are logged in
     *
     * @param bVerify {boolean} - if you want to verify that we are on the HomePage or not
     *
     * @return {HomePage} - an instance of the HomePage
     */
    public HomePage open(boolean bVerify){
        openUrl(HOME_PAGE_URL);
        log.debug(String.format("Open HomePage(): %s",HOME_PAGE_URL));
        if(bVerify){
            verifyHomePage();
        }
        return this;
    }

    /**
     * Method that opens the HomePage and can be done with or without
     * verification of the HomePage
     *
     * @return {HomePage} -  an instance of the HomePage
     */
    public HomePage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the HomePage,
     * wait for loading of the HomePage to be completed and
     * return an instance of the HomePage
     *
     * @return {HomePage} - an instance of the HomePage
     */
    public HomePage verifyHomePage(){

        log.debug("verifyHomePage");
        waitForUrlChangeToExactUrl(HOME_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    /**
     * Method that checks when we are on Home page,
     * the Home page title is displayed
     *
     * @return {boolean} - if Home page title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(pageTitleLocator);
    }

    /**
     * Method that checks if the Home page title is displayed on
     * Home page and gets Home page title
     *
     * @return {String} - Home page title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on Home page");
        WebElement pageTitle = getWebElement(pageTitleLocator);
        return getTextFromWebElement(pageTitle);
    }
}
