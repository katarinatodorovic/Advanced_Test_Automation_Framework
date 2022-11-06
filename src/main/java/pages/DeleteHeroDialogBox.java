package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class DeleteHeroDialogBox extends BasePageCLass{

    //Locators
    private final String deleteHeroDialogBoxString = "//div[@id='deleteHeroModal']";
    @FindBy(id="deleteHeroModal")
    private WebElement deleteHeroDialogBox;
    @FindBy(xpath=deleteHeroDialogBoxString + "//h4[contains(@class, 'modal-title')]")
    private WebElement deleteHeroDialogBoxTitle;
    @FindBy(xpath=deleteHeroDialogBoxString + "//button[contains(@class, 'btn-default')]")
    private WebElement cancelButton;
    @FindBy(xpath=deleteHeroDialogBoxString + "//button[contains(@class, 'btn-danger')]")
    private WebElement deleteButton;
    @FindBy(xpath = deleteHeroDialogBoxString + "//div[@class='modal-body']/p")
    private WebElement deleteHeroMessage;

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public DeleteHeroDialogBox(WebDriver driver) {
        super(driver);
        log.trace("new DeleteHeroDialogBox()");
    }
    /**
     * Method that checks if Delete Hero dialog box is open on the Heroes page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Delete Hero dialog box is visible on the Heroes page
     */
    private boolean isDeleteHeroDialogBoxOpened(int timeout){
        return isWebElementVisible(deleteHeroDialogBox, timeout);
    }

    /**
     * Method that checks if Delete Hero dialog box is closed on the Heroes page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Delete Hero dialog box is closed the Heroes page
     */
    private boolean isDeleteHeroDialogBoxClosed(int timeout){
        return isWebElementInvisible(deleteHeroDialogBox, timeout);
    }

    /**
     * Method that waits until Delete Hero dialog box is loaded and
     * checks if Delete Hero dialog box is open on the Heroes page
     *
     * @return {DeleteHeroDialogBox} - an instance of DeleteHeroDialogBox
     */
    public DeleteHeroDialogBox verifyDeleteHeroDialogBox(){
        log.debug("verifyDeleteHeroDialogBox()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isDeleteHeroDialogBoxOpened(Time.TIME_SHORTER), "Delete Hero Dialog Box is not opened");
        return this;
    }

    /**
     * Method that checks when we are on Delete Hero dialog box,
     * the page title is displayed
     *
     * @return {boolean} - if Delete Hero dialog box title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(deleteHeroDialogBoxTitle);
    }

    /**
     * Method that checks if Delete Hero dialog box title is displayed on
     * Delete Hero dialog box and gets Delete Hero dialog box title
     *
     * @return {String} - Delete Hero dialog box title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on Delete Hero dialog box");
        return getTextFromWebElement(deleteHeroDialogBoxTitle);
    }

    /**
     * Method that checks if Delete Hero Message is displayed on Delete Hero dialog box
     *
     * @return {boolean} - if Delete Hero Message is displayed or not
     */
    public boolean isDeleteHeroMessageDisplayed(){
        log.debug("isDeleteHeroMessageDisplayed()");
        return isWebElementDisplayed(deleteHeroMessage);
    }

    /**
     * Method that gets Delete Hero Message from Delete Hero dialog box
     *
     * @return {String} - Delete Hero Message
     */
    public String getDeleteHeroMessage(){
        log.debug("getDeleteHeroMessage()");
        Assert.assertTrue(isDeleteHeroMessageDisplayed(),"Delete Hero Message is NOT displayed on Delete Hero dialog box");
        return getTextFromWebElement(deleteHeroMessage);
    }

    /**
     * Method that checks if the Cancel button is displayed on Delete Hero dialog box
     *
     * @return {boolean} - if Cancel button is displayed or not
     */
    public boolean isCancelButtonDisplayed(){
        log.debug("isCancelButtonDisplayed()");
        return isWebElementDisplayed(cancelButton);
    }

    /**
     * Method that checks if the Cancel button is displayed,
     * gets the Cancel button title and returns text as String
     *
     * @return {String} - text that is being displayed on Cancel button
     */
    public String getCancelButtonTitle(){
        log.debug("getCancelButtonTitle()");
        Assert.assertTrue(isCancelButtonDisplayed(),
                "Cancel button is NOT displayed on the Delete Hero dialog box!");
        return getValueFromWebElement(cancelButton);
    }

    /**
     * Method that checks if the Cancel button is displayed on Delete Hero dialog box,
     * clicks on the Cancel button, checks if User Details dialog box is closed
     */
    private void clickCancelButton(){
        Assert.assertTrue(isCancelButtonDisplayed(),
                "cancel Button is NOT displayed on the Delete Hero Dialog Box!");
        clickOnWebElement(cancelButton);
        Assert.assertTrue(isDeleteHeroDialogBoxClosed(Time.TIME_SHORTER), "Delete Hero Dialog Box is not closed");
    }

    /**
     * Method that checks if the Cancel button is displayed on Delete Hero dialog box,
     * clicks on the Cancel button, checks if Delete Hero dialog box is closed, and returns
     * an instance of the HeroesPage
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage clickOnCancelButtonToHeroesPage() {
        log.debug("clickOnCancelButtonToHeroesPage()");
        clickCancelButton();
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    /**
     * Method that checks if the Delete button is displayed on Delete Hero dialog box
     *
     * @return {boolean} - if Delete button is displayed or not
     */
    public boolean isDeleteButtonDisplayed(){
        log.debug("isDeleteButtonDisplayed()");
        return isWebElementDisplayed(deleteButton);
    }

    /**
     * Method that checks if the Delete button is displayed,
     * gets the Delete button title and returns text as String
     *
     * @return {String} - text that is being displayed on Delete button
     */
    public String getDeleteButtonTitle(){
        log.debug("getDeleteButtonTitle()");
        Assert.assertTrue(isDeleteButtonDisplayed(),
                "Delete button is NOT displayed on the Delete Hero dialog box!");
        return getValueFromWebElement(deleteButton);
    }

    /**
     * Method that checks if the Delete button is displayed on Delete Hero dialog box,
     * clicks on the Delete button, checks if User Details dialog box is closed
     */
    private void clickDeleteButton(){
        Assert.assertTrue(isDeleteButtonDisplayed(),
                "Delete Button is NOT displayed on the Delete Hero Dialog Box!");
        clickOnWebElement(deleteButton);
        Assert.assertTrue(isDeleteHeroDialogBoxClosed(Time.TIME_SHORTER), "Delete Hero Dialog Box is not closed");
    }

    /**
     * Method that checks if the Delete button is displayed on Delete Hero dialog box,
     * clicks on the Delete button, checks if Delete Hero dialog box is closed, and returns
     * an instance of the HeroesPage
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage clickOnDeleteButtonToHeroesPage() {
        log.debug("clickOnDeleteButtonToHeroesPage()");
        clickDeleteButton();
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }
}
