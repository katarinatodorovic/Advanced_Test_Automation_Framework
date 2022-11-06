package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.DateTimeUtils;
import java.util.Date;

/**
 * Class used for User Details Dialog Box on the Users and the Heroes page
 */
public class UserDetailsDialogBox extends BasePageCLass{

    // Locators
    private final String userDetailsDialogBoxString = "//div[@id='userModal']";

    @FindBy(id="userModal")
    private WebElement userDetailsDialogBox;

    @FindBy(xpath=userDetailsDialogBoxString +"//button[contains(@class,'btn-default')]")
    private WebElement closeButton;

    @FindBy(xpath="//h4[contains(@class,'modal-title')]")
    private WebElement userDetailsDialogBoxTitle;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='firstName']//preceding::span[3]")
    private WebElement userUsernameDetail;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='firstName']//preceding::span[2]")
    private WebElement userUsername;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='firstName']//preceding::span[1]")
    private WebElement userFirstNameDetail;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='firstName']")
    private WebElement userFirstName;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='lastName']//preceding::span[1]")
    private WebElement userLastNameDetail;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='lastName']")
    private WebElement userLastName;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='lastName']//following::span[1]")
    private WebElement userCreatedAtDetail;

    @FindBy(xpath="//div[@class='media-body']//p//span[@class='lastName']//following::span[2]")
    private WebElement userCreatedAt;

    @FindBy(xpath="//div[@class='media-body']//span[@class='about']//preceding::span[1]")
    private WebElement userAboutDetail;

    @FindBy(xpath="//div[@class='media-body']//span[@class='about']")
    private WebElement userAbout;


    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public UserDetailsDialogBox(WebDriver driver) {
        super(driver);
        log.trace("new UserDetailsDialogBox()");
    }
    /**
     * Method that checks if User Details dialog box is open on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if User Details dialog box is visible on the Users page
     */
    private boolean isUserDetailsDialogBoxOpened(int timeout){
        return isWebElementVisible(userDetailsDialogBox, timeout);
    }

    /**
     * Method that checks if User Details dialog box is closed on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if User Details dialog box is closed the Users page
     */
    private boolean isUserDetailsDialogBoxClosed(int timeout){
        return isWebElementInvisible(userDetailsDialogBox, timeout);
    }

    /**
     * Method that waits until User Details dialog box is loaded and
     * checks if User Details dialog box is open on the Users page
     *
     * @return {UserDetailsDialogBox} - an instance of UserDetailsDialogBox
     */
    public UserDetailsDialogBox verifyUserDetailsDialogBox(){
        log.debug("verifyUserDetailsDialogBox()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isUserDetailsDialogBoxOpened(Time.TIME_SHORTER), "User Details Dialog Box is not opened");
        return this;
    }

    /**
     * Method that checks when we are on User Details Dialog Box,
     * the page title is displayed
     *
     * @return {boolean} - if User Details Dialog Box title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(userDetailsDialogBoxTitle);
    }

    /**
     * Method that checks if the User Details Dialog Box title is displayed on
     * User Details Dialog Box and gets User Details Dialog Box title
     *
     * @return {String} - User Details Dialog Box title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userDetailsDialogBoxTitle);
    }

    /**
     * Method that checks if the Close button is displayed on the User Details dialog box
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
                "Close button is NOT displayed on the User Details dialog box!");
        return getValueFromWebElement(closeButton);
    }

    /**
     * Method that checks if the Close button is displayed on the User Details dialog box,
     * clicks on the Close button, checks if User Details dialog box is closed
     */
    private void clickCloseButton(){
        Assert.assertTrue(isCloseButtonDisplayed(),
                "Close Button is NOT displayed on the User Details Dialog Box!");
        clickOnWebElement(closeButton);
        Assert.assertTrue(isUserDetailsDialogBoxClosed(Time.TIME_SHORTER), "User Details Dialog Box is not closed");
    }

    /**
     * Method that checks if the Close button is displayed on the User Details dialog box,
     * clicks on the Close button, checks if User Details dialog box is closed, and returns
     * an instance of the UsersPage
     *
     * @return {UsersPage} - an instance of the DetailsPage
     */
    public UsersPage clickOnCloseButtonToUsersPage() {
        log.debug("clickOnCloseButtonToUsersPage()");
        clickCloseButton();
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    /**
     * Method that checks if the Close button is displayed on the User Details dialog box,
     * clicks on the Close button, checks if User Details dialog box is closed, and returns
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
     * Method that checks when we are on the User Details Dialog Box,
     * the User Username Detail is displayed
     *
     * @return {boolean} - if User Username Detail is displayed or not
     */
    public boolean isUserUsernameDetailDisplayed(){
        log.debug("isUserUsernameDetailDisplayed()");
        return isWebElementDisplayed(userUsernameDetail);
    }

    /**
     * Method that return the String that is displayed as a User Username Detail
     *
     * @return {String} - User Username Detail
     */
    public String getUserUsernameDetailText(){
        log.debug("getUserUsernameDetailText()");
        Assert.assertTrue(isUserUsernameDetailDisplayed(),"User Username Detail Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userUsernameDetail);
    }

    /**
     * Method that checks when we are on the User Details Dialog Box,
     * the User First Name Detail is displayed
     *
     * @return {boolean} - if User First Name Detail is displayed or not
     */
    public boolean isUserFirstNameDetailDisplayed(){
        log.debug("isUserFirstNameDetailDisplayed()");
        return isWebElementDisplayed(userFirstNameDetail);
    }

    /**
     * Method that return the String that is displayed as a User First Name Detail
     *
     * @return {String} - User First Name Detail
     */
    public String getUserFirstNameDetailText(){
        log.debug("getUserFirstNameDetailText()");
        Assert.assertTrue(isUserFirstNameDetailDisplayed(),"User First Name Detail Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userFirstNameDetail);
    }

    /**
     * Method that checks when we are on the User Details Dialog Box,
     * the User Last Name Detail is displayed
     *
     * @return {boolean} - if User Last Name Detail is displayed or not
     */
    public boolean isUserLastNameDetailDisplayed(){
        log.debug("isUserLastNameDetailDisplayed()");
        return isWebElementDisplayed(userLastNameDetail);
    }

    /**
     * Method that return the String that is displayed as a User Last Name Detail
     *
     * @return {String} - User Last Name Detail
     */
    public String getUserLastNameDetailText(){
        log.debug("getUserLastNameDetailText()");
        Assert.assertTrue(isUserLastNameDetailDisplayed(),"User Last Name Detail Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userLastNameDetail);
    }

    /**
     * Method that checks when we are on the User Details Dialog Box,
     * the User Created At Detail is displayed
     *
     * @return {boolean} - if User Created At Detail is displayed or not
     */
    public boolean isUserCreatedAtDetailDisplayed(){
        log.debug("isUserCreatedAtDetailDisplayed()");
        return isWebElementDisplayed(userCreatedAtDetail);
    }

    /**
     * Method that return the String that is displayed as a User Created At Detail
     *
     * @return {String} - User Created At Detail
     */
    public String getUserCreatedAtDetailText(){
        log.debug("getUserCreatedAtDetailText()");
        Assert.assertTrue(isUserCreatedAtDetailDisplayed(),"User Created At Detail Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userCreatedAtDetail);
    }

    /**
     * Method that checks when we are on the User Details Dialog Box,
     * the User About Detail is displayed
     *
     * @return {boolean} - if User About Detail is displayed or not
     */
    public boolean isUserAboutDetailDisplayed(){
        log.debug("isUserAboutDetailDisplayed()");
        return isWebElementDisplayed(userAboutDetail);
    }

    /**
     * Method that return the String that is displayed as a User About Detail
     *
     * @return {String} - User About Detail
     */
    public String getUserAboutDetailText(){
        log.debug("getUserAboutDetailText()");
        Assert.assertTrue(isUserAboutDetailDisplayed(),"User About Detail Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userAboutDetail);
    }

    /**
     * Method that checks when we are on the User Details Dialog Box,
     * the User Username is displayed
     *
     * @return {boolean} - if User Username is displayed or not
     */
    public boolean isUserUsernameDisplayed(){
        log.debug("isUserNameDisplayed()");
        return isWebElementDisplayed(userUsername);
    }

    /**
     * Method that return the String that is displayed as a User Username
     *
     * @return {String} - User Username
     */
    public String getUserUsernameText(){
        log.debug("getUserUsernameText()");
        Assert.assertTrue(isUserUsernameDisplayed(),"User Username Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userUsername);
    }

    /**
     * Method that checks when we are on the User Details Dialog Box,
     * the User First Name is displayed
     *
     * @return {boolean} - if User First Name is displayed or not
     */
    public boolean isUserFirstNameDisplayed(){
        log.debug("isUserFirstNameDisplayed()");
        return isWebElementDisplayed(userFirstName);
    }

    /**
     * Method that return the String that is displayed as a User First Name
     *
     * @return {String} - User First Name
     */
    public String getUserFirstNameText(){
        log.debug("getUserFirstNameText()");
        Assert.assertTrue(isUserFirstNameDisplayed(),"User First Name Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userFirstName);
    }

    /**
     * Method that checks when we are on the User Details Dialog Box,
     * the User Last Name is displayed
     *
     * @return {boolean} - if User Last Name is displayed or not
     */
    public boolean isUserLastNameDisplayed(){
        log.debug("isUserLastNameDisplayed()");
        return isWebElementDisplayed(userLastName);
    }

    /**
     * Method that return the String that is displayed as a User Last Name
     *
     * @return {String} - User Last Name
     */
    public String getUserLastNameText(){
        log.debug("getUserLastNameText()");
        Assert.assertTrue(isUserLastNameDisplayed(),"User Last Name Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userLastName);
    }

    /**
     * Method that checks when we are on the User Details Dialog Box,
     * the User Created At is displayed
     *
     * @return {boolean} - if User Created At is displayed or not
     */
    public boolean isUserCreatedAtDisplayed(){
        log.debug("isUserCreatedAtDisplayed()");
        return isWebElementDisplayed(userCreatedAt);
    }

    /**
     * Method that return the String that is displayed as a User Created At
     *
     * @return {String} - User Created At
     */
    public String getUserCreatedAtText(){
        log.debug("getUserCreatedAtText()");
        Assert.assertTrue(isUserCreatedAtDisplayed(),"User Created At  Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userCreatedAt);
    }

    /**
     * Method that return the String that is displayed as a User Created At,
     * gets browser date (because we need info about timezone from browser)
     * from the machine on which tests are executed, combine those two into
     * pattern that is passed as parameter and return date and info about
     * timezone which is not displayed on HeroDetailsDialogBox
     *
     * @return {Date} - Date in format dd.MM.yyyy. HH:mm z -> 10.10.2017. 10:45 CEST
     */
    public Date getUserParsedCreatedAtText(){
        log.debug("getUserParsedCreatedAtText()");
        //10.10.2017. 10:45 + Browser Time zone
        String sDateTime = getUserCreatedAtText();
        String sBrowserTimeZone = DateTimeUtils.getBrowserTimezone(driver);
        sDateTime = sDateTime + " " + sBrowserTimeZone;
        Date date = DateTimeUtils.getParsedDateTime(sDateTime, "dd.MM.yyyy. HH:mm z");
        return date;
    }

    /**
     * Method that checks when we are on the User Details Dialog Box,
     * the User About is displayed
     *
     * @return {boolean} - if User About is displayed or not
     */
    public boolean isUserAboutDisplayed(){
        log.debug("isUserAboutDisplayed()");
        return isWebElementDisplayed(userAbout);
    }

    /**
     * Method that return the String that is displayed as a User About
     *
     * @return {String} - User About
     */
    public String getUserAboutText(){
        log.debug("getUserAboutText()");
        Assert.assertTrue(isUserAboutDisplayed(),"User About Text is NOT displayed on User Details Dialog Box");
        return getTextFromWebElement(userAbout);
    }
}
