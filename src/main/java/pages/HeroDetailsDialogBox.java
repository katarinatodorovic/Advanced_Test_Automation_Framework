package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.DateTimeUtils;
import java.util.Date;

public class HeroDetailsDialogBox extends BasePageCLass{

    // Locators
    private final String heroDetailsDialogBoxString = "//div[@id='heroModal']";

    @FindBy(id="heroModal")
    private WebElement heroDetailsDialogBox;

    @FindBy(xpath=heroDetailsDialogBoxString +"//button[contains(@class,'btn-default')]")
    private WebElement closeButton;

    @FindBy(xpath="//h4[contains(@class,'modal-title')]")
    private WebElement heroDetailsDialogBoxTitle;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='name']//preceding::span[1]")
    private WebElement heroNameDetail;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='name']")
    private WebElement heroName;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='class']//preceding::span[1]")
    private WebElement heroClassDetail;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='class']")
    private WebElement heroClass;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='level']//preceding::span[1]")
    private WebElement heroLevelDetail;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='level']")
    private WebElement heroLevel;

    @FindBy(xpath="(//div[@class='media-body']//p//span[@class='username'])[1]//preceding::span[1]")
    private WebElement heroUserDetail;

    @FindBy(xpath="(//div[@class='media-body']//p//span[@class='username'])[1]")
    private WebElement heroUser;

    @FindBy(xpath="(//div[@class='media-body']//p//span[@class='created'])[1]//preceding::span[1]")
    private WebElement heroCreatedAtDetail;

    @FindBy(xpath="(//div[@class='media-body']//p//span[@class='created'])[1]")
    private WebElement heroCreatedAt;

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public HeroDetailsDialogBox(WebDriver driver) {
        super(driver);
    }

    /**
     * Method that checks if Hero Details dialog box is open on the Heroes page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Hero Details dialog box is visible on the Heroes page
     */
    private boolean isHeroDetailsDialogBoxOpened(int timeout){
        return isWebElementVisible(heroDetailsDialogBox, timeout);
    }

    /**
     * Method that checks if Hero Details dialog box is closed on the Heroes page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Hero Details dialog box is closed the Heroes page
     */
    private boolean isHeroDetailsDialogBoxClosed(int timeout){
        return isWebElementInvisible(heroDetailsDialogBox, timeout);
    }

    /**
     * Method that waits until Hero Details dialog box is loaded and
     * checks if Hero Details dialog box is open on the Heroes page
     *
     * @return {HeroDetailsDialogBox} - an instance of HeroDetailsDialogBox
     */
    public HeroDetailsDialogBox verifyHeroDetailsDialogBox(){
        log.debug("verifyHeroDetailsDialogBox()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isHeroDetailsDialogBoxOpened(Time.TIME_SHORTER), "Hero Details Dialog Box is not opened");
        return this;

    }

    /**
     * Method that checks when we are on Hero Details Dialog Box,
     * the page title is displayed
     *
     * @return {boolean} - if Hero Details Dialog Box title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(heroDetailsDialogBoxTitle);
    }

    /**
     * Method that checks if the Hero Details Dialog Box title is displayed on
     * Hero Details Dialog Box and gets Hero Details Dialog Box title
     *
     * @return {String} - Hero Details Dialog Box title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on Hero Details Dialog Box");
        return getTextFromWebElement(heroDetailsDialogBoxTitle);
    }

    /**
     * Method that checks if the Close button is displayed on the Hero Details dialog box
     *
     * @return {boolean} - if Close button is displayed or not
     */
    public boolean isCloseButtonDisplayed(){
        log.debug("isCloseButtonDisplayed");
        return isWebElementDisplayed(closeButton);
    }

    /**
     * Method that checks if the Close button is displayed,
     * gets the Close button title and returns text as String
     *
     * @return {String} - text that is being displayed on Close button
     */
    public String getCloseButtonTitle(){
        log.debug("getCloseButtonTitle()");
        Assert.assertTrue(isCloseButtonDisplayed(),
                "Close button is NOT displayed on the Hero Details dialog box!");
        return getValueFromWebElement(closeButton);
    }

    /**
     * Method that checks if the Close button is displayed on the Hero Details dialog box,
     * clicks on the Close button, checks if Hero Details dialog box is closed
     */
    private void clickCloseButton(){
        Assert.assertTrue(isCloseButtonDisplayed(),
                "Close Button is NOT displayed on the Hero Details Dialog Box!");
        clickOnWebElement(closeButton);
        Assert.assertTrue(isHeroDetailsDialogBoxClosed(Time.TIME_SHORTER), "Hero Details Dialog Box is not closed");
    }

    /**
     * Method that checks if the Close button is displayed on the Hero Details dialog box,
     * clicks on the Close button, checks if Hero Details dialog box is closed, and returns
     * an instance of the HeroesPage
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage clickOnCloseButtonToHeroesPage() {
        log.debug("clickOnCloseButtonToHeroesPage()");
        clickCloseButton();
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero Name Detail is displayed
     *
     * @return {boolean} - if Hero Name Detail is displayed or not
     */
    public boolean isHeroNameDetailDisplayed(){
        log.debug("isHeroNameDetailDisplayed()");
        return isWebElementDisplayed(heroNameDetail);
    }

    /**
     * Method that return the String that is displayed as a Hero Name Detail
     *
     * @return {String} - Hero Name Detail
     */
    public String getHeroNameDetailText(){
        log.debug("getHeroNameDetailText()");
        Assert.assertTrue(isHeroNameDetailDisplayed(),"Hero Name Detail Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroNameDetail);
    }

    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero Class Detail is displayed
     *
     * @return {boolean} - if Hero Class Detail is displayed or not
     */
    public boolean isHeroClassDetailDisplayed(){
        log.debug("isHeroClassDetailDisplayed()");
        return isWebElementDisplayed(heroClassDetail);
    }

    /**
     * Method that return the String that is displayed as a Hero Class Detail
     *
     * @return {String} - Hero Class Detail
     */
    public String getHeroClassDetailText(){
        log.debug("getHeroClassDetailText()");
        Assert.assertTrue(isHeroClassDetailDisplayed(),"Hero Class Detail Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroClassDetail);
    }

    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero Level Detail is displayed
     *
     * @return {boolean} - if Hero Level Detail is displayed or not
     */
    public boolean isHeroLevelDetailDisplayed(){
        log.debug("isHeroLevelDetailDisplayed()");
        return isWebElementDisplayed(heroLevelDetail);
    }

    /**
     * Method that return the String that is displayed as a Hero Level Detail
     *
     * @return {String} - Hero Level Detail
     */
    public String getHeroLevelDetailText(){
        log.debug("getHeroLevelDetailText()");
        Assert.assertTrue(isHeroLevelDetailDisplayed(),"Hero Level Detail Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroLevelDetail);
    }

    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero User Detail is displayed
     *
     * @return {boolean} - if Hero User Detail is displayed or not
     */
    public boolean isHeroUserDetailDisplayed(){
        log.debug("isHeroUserDetailDisplayed()");
        return isWebElementDisplayed(heroUserDetail);
    }

    /**
     * Method that return the String that is displayed as a Hero User Detail
     *
     * @return {String} - Hero User Detail
     */
    public String getHeroUserDetailText(){
        log.debug("getHeroUserDetailText()");
        Assert.assertTrue(isHeroUserDetailDisplayed(),"Hero User Detail Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroUserDetail);
    }

    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero Created At Detail is displayed
     *
     * @return {boolean} - if HeroCreated At Detail is displayed or not
     */
    public boolean isHeroCreatedAtDetailDisplayed(){
        log.debug("isHeroCreatedAtDetailDisplayed()");
        return isWebElementDisplayed(heroCreatedAtDetail);
    }

    /**
     * Method that return the String that is displayed as a Hero Created At Detail
     *
     * @return {String} - Hero Created At Detail
     */
    public String getHeroCreatedAtDetailText(){
        log.debug("getHeroCreatedAtDetailText()");
        Assert.assertTrue(isHeroCreatedAtDetailDisplayed(),"Hero Created At Detail Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroCreatedAtDetail);
    }


    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero Name is displayed
     *
     * @return {boolean} - if Hero Name is displayed or not
     */
    public boolean isHeroNameDisplayed(){
        log.debug("isHeroNameDisplayed()");
        return isWebElementDisplayed(heroName);
    }

    /**
     * Method that return the String that is displayed as a Hero Name
     *
     * @return {String} - Hero Name
     */
    public String getHeroNameText(){
        log.debug("getHeroNameText()");
        Assert.assertTrue(isHeroNameDisplayed(),"Hero Name Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroName);
    }

    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero Class is displayed
     *
     * @return {boolean} - if Hero Class is displayed or not
     */
    public boolean isHeroClassDisplayed(){
        log.debug("isHeroClassDisplayed()");
        return isWebElementDisplayed(heroClass);
    }

    /**
     * Method that return the String that is displayed as a Hero Class
     *
     * @return {String} - Hero Class
     */
    public String getHeroClassText(){
        log.debug("getHeroClassText()");
        Assert.assertTrue(isHeroClassDisplayed(),"Hero Class Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroClass);
    }

    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero Level is displayed
     *
     * @return {boolean} - if Hero Level is displayed or not
     */
    public boolean isHeroLevelDisplayed(){
        log.debug("isHeroLevelDisplayed()");
        return isWebElementDisplayed(heroLevel);
    }

    /**
     * Method that return the String that is displayed as a Hero Level
     *
     * @return {String} - Hero Level
     */
    public String getHeroLevelText(){
        log.debug("getHeroLevelText()");
        Assert.assertTrue(isHeroLevelDisplayed(),"Hero Level Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroLevel);
    }

    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero User is displayed
     *
     * @return {boolean} - if Hero User is displayed or not
     */
    public boolean isHeroUserDisplayed(){
        log.debug("isHeroUserDisplayed()");
        return isWebElementDisplayed(heroUser);
    }

    /**
     * Method that return the String that is displayed as a Hero User
     *
     * @return {String} - Hero User
     */
    public String getHeroUserText(){
        log.debug("getHeroUserText()");
        Assert.assertTrue(isHeroUserDisplayed(),"Hero User  Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroUser);
    }

    /**
     * Method that checks when we are on the Hero Details Dialog Box,
     * the Hero Created At is displayed
     *
     * @return {boolean} - if Hero Created At is displayed or not
     */
    public boolean isHeroCreatedAtDisplayed(){
        log.debug("isHeroCreatedAtDisplayed()");
        return isWebElementDisplayed(heroCreatedAt);
    }

    /**
     * Method that return the String that is displayed as a Hero Created At
     *
     * @return {String} - Hero Created At
     */
    public String getHeroCreatedAtText(){
        log.debug("getHeroCreatedAtText()");
        Assert.assertTrue(isHeroCreatedAtDisplayed(),"Hero Created At  Text is NOT displayed on Heroes Details Dialog Box");
        return getTextFromWebElement(heroCreatedAt);
    }

    /**
     * Method that return the String that is displayed as a Hero Created At,
     * gets browser date (because we need info about timezone from browser)
     * from the machine on which tests are executed, combine those two into
     * pattern that is passed as parameter and return date and info about
     * timezone which is not displayed on HeroDetailsDialogBox
     *
     * @return {Date} - Date in format dd.MM.yyyy. HH:mm z -> 10.10.2017. 10:45 CEST
     */
    public Date getHeroParsedCreatedAtText(){
        log.debug("getHeroParsedCreatedAtText()");
        //10.10.2017. 10:45 + Browser Time zone
        String sDateTime = getHeroCreatedAtText();
        String sBrowserTimeZone = DateTimeUtils.getBrowserTimezone(driver);
        sDateTime = sDateTime + " " + sBrowserTimeZone;
        Date date = DateTimeUtils.getParsedDateTime(sDateTime, "dd.MM.yyyy. HH:mm z");
        return date;
    }
}
