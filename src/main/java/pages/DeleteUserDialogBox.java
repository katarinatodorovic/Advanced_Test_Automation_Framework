package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Class used for Delete User Dialog Box on the Users and the Heroes page
 */
public class DeleteUserDialogBox extends BasePageCLass{

    private final String deleteUserDialogBoxString = "//div[@id='deleteUserModal']";

    @FindBy(id="deleteUserModal")
    private WebElement deleteUserDialogBox;

    @FindBy(xpath=deleteUserDialogBoxString +"//button[contains(@class,'btn-default')]")
    private WebElement cancelButton;

    @FindBy(xpath=deleteUserDialogBoxString +"//button[contains(@class,'btn-danger')]")
    private WebElement deleteButton;

    @FindBy(xpath="//h4[contains(@class,'modal-title')]")
    private WebElement deleteUserDialogBoxTitle;

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public DeleteUserDialogBox(WebDriver driver) {
        super(driver);
        log.trace("new DeleteUserDialogBox()");
    }
    /**
     * Method that checks if Delete User dialog box is open on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Delete User dialog box is visible on the Users page
     */
    private boolean isDeleteUserDialogBoxOpened(int timeout){
        return isWebElementVisible(deleteUserDialogBox, timeout);
    }

    /**
     * Method that checks if Delete User dialog box is closed on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Delete User dialog box is closed the Users page
     */
    private boolean isDeleteUserDialogBoxClosed(int timeout){
        return isWebElementInvisible(deleteUserDialogBox, timeout);
    }

    /**
     * Method that waits until Delete User dialog box is loaded and
     * checks if Delete User dialog box is open on the Users page
     *
     * @return {DeleteUserDialogBox} - an instance of DeleteUserDialogBox
     */
    public DeleteUserDialogBox verifyDeleteUserDialogBox(){
        log.debug("verifyDeleteUserDialogBox()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isDeleteUserDialogBoxOpened(Time.TIME_SHORTER), "Delete User Dialog Box is not opened");
        return this;

    }

    /**
     * Method that checks when we are on Delete User Dialog Box,
     * the page title is displayed
     *
     * @return {boolean} - if Delete User Dialog Box title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(deleteUserDialogBoxTitle);
    }

    /**
     * Method that checks if Delete User Dialog Box title is displayed on
     * Delete User Dialog Box and gets Delete User Dialog Box title
     *
     * @return {String} - Delete User Dialog Box title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on Delete User Dialog Box");
        return getTextFromWebElement(deleteUserDialogBoxTitle);
    }

    /**
     * Method that checks if the Cancel button is displayed on Delete User dialog box
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
                "Cancel button is NOT displayed on the Delete User dialog box!");
        return getValueFromWebElement(cancelButton);
    }

    /**
     * Method that checks if the Cancel button is displayed on Delete User dialog box,
     * clicks on the Cancel button, checks if User Details dialog box is closed
     */
    private void clickCancelButton(){
        Assert.assertTrue(isCancelButtonDisplayed(),
                "cancel Button is NOT displayed on the Delete User Dialog Box!");
        clickOnWebElement(cancelButton);
        Assert.assertTrue(isDeleteUserDialogBoxClosed(Time.TIME_SHORTER), "Delete User Dialog Box is not closed");
    }

    /**
     * Method that checks if the Cancel button is displayed on Delete User dialog box,
     * clicks on the Cancel button, checks if Delete User dialog box is closed, and returns
     * an instance of the UsersPage
     *
     * @return {UsersPage} - an instance of the DetailsPage
     */
    public UsersPage clickOnCancelButtonToUsersPage() {
        log.debug("clickOnCancelButtonToUsersPage()");
        clickCancelButton();
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    /**
     * Method that checks if the Cancel button is displayed on Delete User dialog box,
     * clicks on the Cancel button, checks if Delete User dialog box is closed, and returns
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
     * Method that checks if the Delete button is displayed on Delete User dialog box
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
                "Delete button is NOT displayed on the Delete User dialog box!");
        return getValueFromWebElement(deleteButton);
    }

    /**
     * Method that checks if the Delete button is displayed on Delete User dialog box,
     * clicks on the Delete button, checks if User Details dialog box is closed
     */
    private void clickDeleteButton(){
        Assert.assertTrue(isDeleteButtonDisplayed(),
                "Delete Button is NOT displayed on the Delete User Dialog Box!");
        clickOnWebElement(deleteButton);
        Assert.assertTrue(isDeleteUserDialogBoxClosed(Time.TIME_SHORTER), "Delete User Dialog Box is not closed");
    }

    /**
     * Method that checks if the Delete button is displayed on Delete User dialog box,
     * clicks on the Delete button, checks if Delete User dialog box is closed, and returns
     * an instance of the UsersPage
     *
     * @return {UsersPage} - an instance of the DetailsPage
     */
    public UsersPage clickOnDeleteButtonToUsersPage() {
        log.debug("clickOnDeleteButtonToUsersPage()");
        clickDeleteButton();
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    /**
     * Method that checks if the Delete button is displayed on Delete User dialog box,
     * clicks on the Delete button, checks if Delete User dialog box is closed, and returns
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
