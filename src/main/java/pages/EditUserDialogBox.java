package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Class used for Edit User Dialog Box on the Users and the Heroes page
 */
public class EditUserDialogBox extends BasePageCLass{

    private final String editUserDialogBoxString = "//div[@id='editUserModal']";

    @FindBy(id="editUserModal")
    private WebElement editUserDialogBox;

    @FindBy(xpath=editUserDialogBoxString +"//button[contains(@class,'btn-default')]")
    private WebElement closeButton;

    @FindBy(id="editUserSubmitButton")
    private WebElement saveButton;

    @FindBy(xpath="//h4[contains(@class,'modal-title')]")
    private WebElement editUserDialogBoxTitle;

    @FindBy(id="editUsername")
    private WebElement editUsernameTextField;

    @FindBy(id="editFirstName")
    private WebElement editFirstNameTextField;

    @FindBy(id="editLastName")
    private WebElement editLastNameTextField;

    @FindBy(id="editAbout")
    private WebElement editAboutTextField;

    @FindBy(xpath="//form[@id='edit-user-form']//input[@id='editUsername']/preceding-sibling::label")
    private WebElement editUsernameTextFieldLabel;

    @FindBy(xpath="//form[@id='edit-user-form']//input[@id='editFirstName']/preceding-sibling::label")
    private WebElement editFirstNameTextFieldLabel;

    @FindBy(xpath="//form[@id='edit-user-form']//input[@id='editLastName']/preceding-sibling::label")
    private WebElement editLastNameTextFieldLabel;

    @FindBy(xpath="//form[@id='edit-user-form']//input[@id='editAbout']/preceding-sibling::label")
    private WebElement editAboutTextFieldLabel;


    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public EditUserDialogBox(WebDriver driver) {
        super(driver);
        log.trace("new EditUserDialogBox()");
    }

    /**
     * Method that checks if Edit User dialog box is open on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Edit User dialog box is visible on the Users page
     */
    private boolean isEditUserDialogBoxOpened(int timeout){
        return isWebElementVisible(editUserDialogBox, timeout);
    }

    /**
     * Method that checks if Edit User dialog box is closed on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Edit User dialog box is closed the Users page
     */
    private boolean isEditUserDialogBoxClosed(int timeout){
        return isWebElementInvisible(editUserDialogBox, timeout);
    }

    /**
     * Method that waits until Edit User dialog box is loaded and
     * checks if Edit User dialog box is open on the Users page
     *
     * @return {EditUserDialogBox} - an instance of EditUserDialogBox
     */
    public EditUserDialogBox verifyEditUserDialogBox(){
        log.debug("verifyEditUserDialogBox()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isEditUserDialogBoxOpened(Time.TIME_SHORTER), "Edit User Dialog Box is not opened");
        return this;

    }

    /**
     * Method that checks when we are on Edit User Dialog Box,
     * the page title is displayed
     *
     * @return {boolean} - if Edit User Dialog Box title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(editUserDialogBoxTitle);
    }

    /**
     * Method that checks if the Edit User Dialog Box title is displayed on
     * Edit User Dialog Box and gets Edit User Dialog Box title
     *
     * @return {String} - Edit User Dialog Box title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on Edit User Dialog Box");
        return getTextFromWebElement(editUserDialogBoxTitle);
    }

    /**
     * Method that checks if the Close button is displayed on the Edit User dialog box
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
                "Close button is NOT displayed on the Edit User dialog box!");
        return getValueFromWebElement(closeButton);
    }

    /**
     * Method that checks if the Close button is displayed on the Edit User dialog box,
     * clicks on the Close button, checks if Edit User dialog box is closed
     */
    private void clickCloseButton(){
        Assert.assertTrue(isCloseButtonDisplayed(),
                "Close Button is NOT displayed on the Edit User Dialog Box!");
        clickOnWebElement(closeButton);
        Assert.assertTrue(isEditUserDialogBoxClosed(Time.TIME_SHORTER), "Edit User Dialog Box is not closed");
    }

    /**
     * Method that checks if the Close button is displayed on the Edit User dialog box,
     * clicks on the Close button, checks if Edit User dialog box is closed, and returns
     * an instance of the UsersPage
     *
     * @return {UsersPage} - an instance of the UsersPage
     */
    public UsersPage clickOnCloseButtonToUsersPage() {
        log.debug("clickOnCloseButtonToUsersPage()");
        clickCloseButton();
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    /**
     * Method that checks if the Close button is displayed on the Edit User dialog box,
     * clicks on the Close button, checks if Edit User dialog box is closed, and returns
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
     * Method that checks if the Save button is displayed on the Edit User dialog box
     *
     * @return {boolean} - if Save button is displayed or not
     */
    public boolean isSaveButtonDisplayed(){
        log.debug("isSaveButtonDisplayed");
        return isWebElementDisplayed(saveButton);
    }

    /**
     * Method that checks if the Save button is displayed,
     * gets the Save button title and returns text as String
     *
     * @return {String} - text that is being displayed on Save button
     */
    public String getSaveButtonTitle(){
        log.debug("getSaveButtonTitle()");
        Assert.assertTrue(isSaveButtonDisplayed(),
                "Save button is NOT displayed on the Edit User dialog box!");
        return getValueFromWebElement(saveButton);
    }

    /**
     * Method that checks if the Save button is displayed on the Edit User dialog box,
     * clicks on the Save button, checks if Edit User dialog box is closed
     */
    private void clickSaveButton(){
        Assert.assertTrue(isSaveButtonDisplayed(),
                "Save button is NOT displayed on the Edit User Dialog Box!");
        clickOnWebElement(saveButton);
        Assert.assertTrue(isEditUserDialogBoxClosed(Time.TIME_SHORTER), "Edit User Dialog Box is not closed");
    }

    /**
     * Method that checks if the Save button is displayed on the Edit User dialog box,
     * clicks on the Save button, checks if Edit User dialog box is closed, and returns
     * an instance of the UsersPage
     *
     * @return {UsersPage} - an instance of the UsersPage
     */
    public UsersPage clickOnSaveButtonToUsersPage() {
        log.debug("clickOnSaveButtonToUsersPage()");
        clickSaveButton();
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    /**
     * Method that checks if the Save button is displayed on the Edit User dialog box,
     * clicks on the Save button, checks if Edit User dialog box is closed, and returns
     * an instance of the HeroesPage
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage clickOnSaveButtonToHeroesPage() {
        log.debug("clickOnSaveButtonToHeroesPage()");
        clickSaveButton();
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    /**
     * Method that checks if the Edit Username text field is displayed on
     * Edit User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Edit Username text field is displayed or not
     */
    public boolean isEditUsernameTextFieldDisplayed(){
        log.debug("isEditUsernameTextFieldDisplayed");
        return isWebElementDisplayed(editUsernameTextField);
    }

    /**
     * Method that checks if the Edit Username text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Edited Username in the Edit Username field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sEditUsername {String} - the Edited Username that we want to type
     *
     * @return {EditUserDialogBox} - an instance of the EditUserDialogBox
     */
    public EditUserDialogBox typeEditedUsername(String sEditUsername){
        log.debug(String.format("typeEditedUsername (%s)", sEditUsername));
        Assert.assertTrue(isEditUsernameTextFieldDisplayed(),
                "Edit Username Text Field is NOT displayed on the Edit User Dialog Box!");
        editUsernameTextField.getText();
        clearAndTypeTextToWebElement(editUsernameTextField, sEditUsername);
        return this;
    }

    /**
     * Method that checks if the Edit Username text field is displayed and returns
     * the text entered as the Edited Username in the Edit Username text field
     *
     * @return {String} - the Edit Username typed in the text field
     */
    public String getEditedUsername(){
        log.debug("getEditedUsername()");
        Assert.assertTrue(isEditUsernameTextFieldDisplayed(),
                "Edit Username Text Field is NOT displayed on the Edit User Dialog Box!");
        return getValueFromWebElement(editUsernameTextField);
    }

    /**
     * Method that checks when we are on Edit User Dialog Box,
     * the Edit Username Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Edit Username Text Field or not
     */
    public boolean isEditUsernameTextFieldLabelVisible(){
        log.debug("isEditUsernameTextFieldLabelVisible");
        return isWebElementVisible(editUsernameTextFieldLabel);
    }

    /**
     * Method that checks if Edit Username Text Field Label is visible on
     * Edit User Dialog Box and gets Edit Username Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getEditUsernameTextFieldLabel(){
        log.debug("getEditUsernameTextFieldLabel()");
        Assert.assertTrue(isEditUsernameTextFieldLabelVisible(),"Edit Username Label is NOT visible on Edit User Dialog Box");
        return getTextFromWebElement(editUsernameTextFieldLabel);
    }

    /**
     * Method that checks if the Edit First Name text field is displayed on
     * Edit User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Edit First Name text field is displayed or not
     */
    public boolean isEditFirstNameTextFieldDisplayed(){
        log.debug("isEditFirstNameFieldDisplayed");
        return isWebElementDisplayed(editFirstNameTextField);
    }

    /**
     * Method that checks if the Edit First Name text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Edited First Name in the Edit First Name field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sEditFirstName {String} - the Edited First Name that we want to type
     *
     * @return {EditUserDialogBox} - an instance of the EditUserDialogBox
     */
    public EditUserDialogBox typeEditedFirstName(String sEditFirstName){
        log.debug(String.format("typeFirstName (%s)", sEditFirstName));
        Assert.assertTrue(isEditFirstNameTextFieldDisplayed(),
                "First Name Text Field is NOT displayed on the Edit User Dialog Box!");
        editFirstNameTextField.getText();
        clearAndTypeTextToWebElement(editFirstNameTextField, sEditFirstName);
        return this;
    }

    /**
     * Method that checks if the Edit First Name text field is displayed and returns
     * the text entered as the Username in the Username text field
     *
     * @return {String} - the Username typed in the text field
     */
    public String getEditedFirstName(){
        log.debug("getEditedFirstName()");
        Assert.assertTrue(isEditFirstNameTextFieldDisplayed(),
                "Edit First Name Text Field is NOT displayed on the Edit User Dialog Box!");
        return getValueFromWebElement(editFirstNameTextField);
    }

    /**
     * Method that checks when we are on Edit User Dialog Box,
     * the Edit First Name Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Edit First Name Text Field or not
     */
    public boolean isEditFirstNameTextFieldLabelVisible(){
        log.debug("isEditFirstNameTextFieldLabelVisible");
        return isWebElementVisible(editFirstNameTextFieldLabel);
    }

    /**
     * Method that checks if Edit First Name Text Field Label is visible on
     * Edit User Dialog Box and gets the Edit First Name Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getEditFirstNameTextFieldLabel(){
        log.debug("getEditFirstNameTextFieldLabel()");
        Assert.assertTrue(isEditFirstNameTextFieldLabelVisible(),"Edit First Name Label is NOT visible on Edit User Dialog Box");
        return getTextFromWebElement(editFirstNameTextFieldLabel);
    }

    /**
     * Method that checks if the Edit Last Name text field is displayed on
     * Edit User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Edit Last Name text field is displayed or not
     */
    public boolean isEditLastNameTextFieldDisplayed(){
        log.debug("isEditLastNameFieldDisplayed");
        return isWebElementDisplayed(editLastNameTextField);
    }

    /**
     * Method that checks if the Edit Last Name text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Edited Last Name in the Last Name field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sEditLastName {String} - the Edited Last Name that we want to type
     *
     * @return {EditUserDialogBox} - an instance of the EditUserDialogBox
     */
    public EditUserDialogBox typeEditedLastName(String sEditLastName){
        log.debug(String.format("typeEditedLastName (%s)", sEditLastName));
        Assert.assertTrue(isEditLastNameTextFieldDisplayed(),
                "Edit Last Name Text Field is NOT displayed on the Edit User Dialog Box!");
        editLastNameTextField.getText();
        clearAndTypeTextToWebElement(editLastNameTextField, sEditLastName);
        return this;
    }

    /**
     * Method that checks if the Edit Last Name text field is displayed and returns
     * the text entered as the Edited Last Name in the Edit Last Name text field
     *
     * @return {String} - the Edited Last Name typed in the text field
     */
    public String getEditLastName(){
        log.debug("getEditLastName()");
        Assert.assertTrue(isEditLastNameTextFieldDisplayed(),
                "Edit Last Name Text Field is NOT displayed on the Edit User Dialog Box!");
        return getValueFromWebElement(editLastNameTextField);
    }

    /**
     * Method that checks when we are on Edit User Dialog Box,
     * the Edit Last Name Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on the Edit Last Name Text Field or not
     */
    public boolean isEditLastNameTextFieldLabelVisible(){
        log.debug("isEditLastNameTextFieldLabelVisible");
        return isWebElementVisible(editLastNameTextFieldLabel);
    }

    /**
     * Method that checks if Edit Last Name Text Field Label is visible on
     * Edit User Dialog Box and gets Edit Last Name Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getEditLastNameTextFieldLabel(){
        log.debug("getEditLastNameTextFieldLabel()");
        Assert.assertTrue(isEditLastNameTextFieldLabelVisible(),"Edit Last Name Label is NOT visible on Edit User Dialog Box");
        return getTextFromWebElement(editLastNameTextFieldLabel);
    }

    /**
     * Method that checks if the Edit About text field is displayed on
     * Edit User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Edit About text field is displayed or not
     */
    public boolean isEditAboutTextFieldDisplayed(){
        log.debug("isEditAboutFieldDisplayed");
        return isWebElementDisplayed(editAboutTextField);
    }

    /**
     * Method that checks if the Edit About text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Edited About in the Edit About field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sEditAbout {String} - the Edited About that we want to type
     *
     * @return {EditUserDialogBox} - an instance of the EditUserDialogBox
     */
    public EditUserDialogBox typeEditedAbout(String sEditAbout){
        log.debug(String.format("typeEditedAbout (%s)", sEditAbout));
        Assert.assertTrue(isEditAboutTextFieldDisplayed(),
                "Edit About Text Field is NOT displayed on the Edit User Dialog Box!");
        editAboutTextField.getText();
        clearAndTypeTextToWebElement(editAboutTextField, sEditAbout);
        return this;
    }

    /**
     * Method that checks if the Edit About text field is displayed and returns
     * the text entered as the Edited About in the Edit About text field
     *
     * @return {String} - the Edited About typed in the text field
     */
    public String getEditAbout(){
        log.debug("getEditAbout()");
        Assert.assertTrue(isEditAboutTextFieldDisplayed(),
                "Edit About Text Field is NOT displayed on the Edit User Dialog Box!");
        return getValueFromWebElement(editAboutTextField);
    }

    /**
     * Method that checks when we are on Edit User Dialog Box,
     * the Edit About Text Field Label is visible
     *
     * @return {boolean} - if Edit Label is visible on About Text Field or not
     */
    public boolean isEditAboutTextFieldLabelVisible(){
        log.debug("isEditAboutTextFieldLabelVisible");
        return isWebElementVisible(editAboutTextFieldLabel);
    }

    /**
     * Method that checks if the Edit About Text Field Label is visible on
     * Edit User Dialog Box and gets Edit About Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getEditAboutTextFieldLabel(){
        log.debug("getEditAboutTextFieldLabel()");
        Assert.assertTrue(isEditAboutTextFieldLabelVisible(),"Edit About Label is NOT visible on Edit User Dialog Box");
        return getTextFromWebElement(editAboutTextFieldLabel);
    }
}
