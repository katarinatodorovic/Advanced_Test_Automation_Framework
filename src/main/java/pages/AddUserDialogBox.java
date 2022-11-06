package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AddUserDialogBox extends BasePageCLass{

    // Page Locators
    private final String addUserDialogBoxString = "//div[@id='addUserModal']";


    // Page Factory Locators
    @FindBy(id="addUserModal")
    private WebElement addUserDialogBox;
    @FindBy(xpath="//h4[@class='modal-title']")
    private WebElement addUserDialogBoxTitle;

    @FindBy(id="username")
    private WebElement usernameTextField;

    @FindBy(id="firstName")
    private WebElement firstNameTextField;

    @FindBy(id="lastName")
    private WebElement lastNameTextField;

    @FindBy(id="email")
    private WebElement emailTextField;

    @FindBy(id="about")
    private WebElement aboutTextField;

    @FindBy(id="secretQuestion")
    private WebElement secretQuestionTextField;

    @FindBy(id="secretAnswer")
    private WebElement secretAnswerTextField;

    @FindBy(id="password")
    private WebElement passwordTextField;

    @FindBy(id="repassword")
    private WebElement confirmPasswordTextField;

    @FindBy(id="submitButton")
    private WebElement signUpButton;

    @FindBy(xpath=addUserDialogBoxString + "//button[contains(@class,'btn-default')]")
    private WebElement cancelButton;

    @FindBy(id="submitButton")
    private WebElement saveButton;

    @FindBy(xpath="//div[@class='form-group']//input[@id='username']/preceding-sibling::label")
    private WebElement usernameTextFieldLabel;
    @FindBy(xpath="//div[@class='form-group']//input[@id='firstName']/preceding-sibling::label")
    private WebElement firstNameTextFieldLabel;
    @FindBy(xpath="//div[@class='form-group']//input[@id='lastName']/preceding-sibling::label")
    private WebElement lastNameTextFieldLabel;
    @FindBy(xpath="//div[@class='form-group']//input[@id='email']/preceding-sibling::label")
    private WebElement emailTextFieldLabel;
    @FindBy(xpath="//div[@class='form-group']//input[@id='about']/preceding-sibling::label")
    private WebElement aboutTextFieldLabel;
    @FindBy(xpath="//div[@class='form-group']//input[@id='secretQuestion']/preceding-sibling::label")
    private WebElement secretQuestionTextFieldLabel;
    @FindBy(xpath="//div[@class='form-group']//input[@id='secretAnswer']/preceding-sibling::label")
    private WebElement secretAnswerTextFieldLabel;
    @FindBy(xpath="//div[@class='form-group']//input[@id='password']/preceding-sibling::label")
    private WebElement passwordTextFieldLabel;
    @FindBy(xpath="//div[@class='form-group']//input[@id='repassword']/preceding-sibling::label")
    private WebElement confirmPasswordTextFieldLabel;


    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public AddUserDialogBox(WebDriver driver) {
        super(driver);
        log.trace("new AddUserDialogBox()");
    }

    /**
     * Method that checks if Add User dialog box is open on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Add User dialog box is visible on the Users page
     */
    private boolean isAddUserDialogBoxOpened(int timeout){
        return isWebElementVisible(addUserDialogBox, timeout);
    }

    /**
     * Method that checks if Add User dialog box is closed on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Add User dialog box is closed the Users page
     */
    private boolean isAddUserDialogBoxClosed(int timeout){
        return isWebElementInvisible(addUserDialogBox, timeout);
    }

    /**
     * Method that wait until Add User dialog box is loaded and
     * checks if Add User dialog box is open on the Users page
     *
     * @return {AddUserDialogBox} - an instance of AddUserDialogBox
     */
    public AddUserDialogBox verifyAddUserDialogBox(){
        log.debug("verifyAddUserDialogBox()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isAddUserDialogBoxOpened(Time.TIME_SHORTER), "Add User Dialog Box is not opened");
        return this;

    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the page title is displayed
     *
     * @return {boolean} - if Add User Dialog Box title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(addUserDialogBoxTitle);
    }

    /**
     * Method that checks if the Add User Dialog Box title is displayed on
     * Add User Dialog Box and gets Add User Dialog Box title
     *
     * @return {String} - Add User Dialog Box title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on Add User Dialog Box");
        return getTextFromWebElement(addUserDialogBoxTitle);
    }

    /**
     * Method that checks if the Cancel button is displayed on the Add User dialog box
     *
     * @return {boolean} - if Cancel button is displayed or not
     */
    public boolean isCancelButtonDisplayed(){
        log.debug("isCancelButtonDisplayed");
        return isWebElementDisplayed(cancelButton);
    }

    /**
     * Method that checks if the Cancel button is displayed on the Add User dialog box,
     * clicks on the Cancel button, checks if Add User dialog box is closed, and returns
     * an instance of the Users page
     *
     * @return {UserPage} - an instance of the UsersPage
     */
    public UsersPage clickOnCancelButton() {
        log.debug("clickOnCancelButton()");
        Assert.assertTrue(isCancelButtonDisplayed(),
                "Cancel Button is NOT displayed on the Add User Dialog Box!");
        clickOnWebElement(cancelButton);
        Assert.assertTrue(isAddUserDialogBoxClosed(Time.TIME_SHORTER), "Add User Dialog Box is not closed");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    /**
     * Method that checks if the Save button is displayed on the Add User dialog box
     *
     * @return {boolean} - if Save button is displayed or not
     */
    public boolean isSaveButtonDisplayed(){
        log.debug("isSaveButtonDisplayed");
        return isWebElementDisplayed(saveButton);
    }

    /**
     * Method that checks if the Save button is displayed on the Add User dialog box,
     * clicks on the Save button, checks if Add User dialog box is closed, and returns
     * an instance of the Users page
     *
     * @return {UserPage} - an instance of the UsersPage
     */
    public UsersPage clickOnSaveButton() {
        log.debug("clickOnSaveButton()");
        Assert.assertTrue(isSaveButtonDisplayed(),
                "Save Button is NOT displayed on the Add User Dialog Box!");
        clickOnWebElement(saveButton);
        Assert.assertTrue(isAddUserDialogBoxClosed(Time.TIME_SHORTER), "Add User Dialog Box is not closed");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    /**
     * Method that checks if the Username text field is displayed on
     * Add User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Username text field is displayed or not
     */
    public boolean isUsernameTextFieldDisplayed(){
        log.debug("isUsernameTextFieldDisplayed");
        return isWebElementDisplayed(usernameTextField);
    }

    /**
     * Method that checks if the Username text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Username in the Username field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sUsername {String} - the Username that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox typeUsername(String sUsername){
        log.debug(String.format("typeUsername (%s)", sUsername));
        Assert.assertTrue(isUsernameTextFieldDisplayed(),
                "Username Text Field is NOT displayed on the Add User Dialog Box!");
        usernameTextField.getText();
        clearAndTypeTextToWebElement(usernameTextField, sUsername);
        return this;
    }

    /**
     * Method that checks if the Username text field is displayed and returns
     * the text entered as the Username in the Username text field
     *
     * @return {String} - the Username typed in the text field
     */
    public String getUsername(){
        log.debug("getUsername()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(),
                "Username Text Field is NOT displayed on the Add User Dialog Box!");
        return getValueFromWebElement(usernameTextField);
    }

    /**
     * Method that checks if the First Name text field is displayed on
     * Add User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the First Name text field is displayed or not
     */
    public boolean isFirstNameTextFieldDisplayed(){
        log.debug("isFirstNameFieldDisplayed");
        return isWebElementDisplayed(firstNameTextField);
    }

    /**
     * Method that checks if the First Name text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the First Name in the First Name field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sFirstName {String} - the First Name that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox typeFirstName(String sFirstName){
        log.debug(String.format("typeFirstName (%s)", sFirstName));
        Assert.assertTrue(isFirstNameTextFieldDisplayed(),
                "First Name Text Field is NOT displayed on the Add User Dialog Box!");
        firstNameTextField.getText();
        clearAndTypeTextToWebElement(firstNameTextField, sFirstName);
        return this;
    }

    /**
     * Method that checks if the First Name text field is displayed and returns
     * the text entered as the First Name in the Username text field
     *
     * @return {String} - the First Name typed in the text field
     */
    public String getFirstName(){
        log.debug("getFirstName()");
        Assert.assertTrue(isFirstNameTextFieldDisplayed(),
                "First Name Text Field is NOT displayed on the Add User Dialog Box!");
        return getValueFromWebElement(firstNameTextField);
    }

    /**
     * Method that checks if the Last Name text field is displayed on
     * Add User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Last Name text field is displayed or not
     */
    public boolean isLastNameTextFieldDisplayed(){
        log.debug("isLastNameFieldDisplayed");
        return isWebElementDisplayed(lastNameTextField);
    }

    /**
     * Method that checks if the Last Name text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Last Name in the Last Name field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sLastName {String} - the Last Name that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox typeLastName(String sLastName){
        log.debug(String.format("typeLastName (%s)", sLastName));
        Assert.assertTrue(isLastNameTextFieldDisplayed(),
                "Last Name Text Field is NOT displayed on the Add User Dialog Box!");
        lastNameTextField.getText();
        clearAndTypeTextToWebElement(lastNameTextField, sLastName);
        return this;
    }

    /**
     * Method that checks if the Last Name text field is displayed and returns
     * the text entered as the Last Name in the Last Name text field
     *
     * @return {String} - the Last Name typed in the text field
     */
    public String getLastName(){
        log.debug("getLastName()");
        Assert.assertTrue(isLastNameTextFieldDisplayed(),
                "Last Name Text Field is NOT displayed on the Add User Dialog Box!");
        return getValueFromWebElement(lastNameTextField);
    }

    /**
     * Method that checks if the Email text field is displayed on
     * Add User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Email text field is displayed or not
     */
    public boolean isEmailTextFieldDisplayed(){
        log.debug("isEmailFieldDisplayed");
        return isWebElementDisplayed(emailTextField);
    }

    /**
     * Method that checks if the Email text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Email in the Email field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sEmail {String} - the Email that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox typeEmailName(String sEmail){
        log.debug(String.format("typeEmail (%s)", sEmail));
        Assert.assertTrue(isEmailTextFieldDisplayed(),
                "Email Text Field is NOT displayed on the Add User Dialog Box!");
        emailTextField.getText();
        clearAndTypeTextToWebElement(emailTextField, sEmail);
        return this;
    }

    /**
     * Method that checks if the Email text field is displayed and returns
     * the text entered as the Email in the Email text field
     *
     * @return {String} - the Email typed in the text field
     */
    public String getEmail(){
        log.debug("getEmail()");
        Assert.assertTrue(isEmailTextFieldDisplayed(),
                "Email Text Field is NOT displayed on the Add User Dialog Box!");
        return getValueFromWebElement(emailTextField);
    }

    /**
     * Method that checks if the About text field is displayed on
     * Add User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the About text field is displayed or not
     */
    public boolean isAboutTextFieldDisplayed(){
        log.debug("isAboutFieldDisplayed");
        return isWebElementDisplayed(aboutTextField);
    }

    /**
     * Method that checks if the About text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the About in the About field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sAbout {String} - the About that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox typeAbout(String sAbout){
        log.debug(String.format("typeAbout (%s)", sAbout));
        Assert.assertTrue(isAboutTextFieldDisplayed(),
                "About Text Field is NOT displayed on the Add User Dialog Box!");
        aboutTextField.getText();
        clearAndTypeTextToWebElement(aboutTextField, sAbout);
        return this;
    }

    /**
     * Method that checks if the About text field is displayed and returns
     * the text entered as the About in the About text field
     *
     * @return {String} - the About typed in the text field
     */
    public String getAbout(){
        log.debug("getAbout()");
        Assert.assertTrue(isAboutTextFieldDisplayed(),
                "About Text Field is NOT displayed on the Add User Dialog Box!");
        return getValueFromWebElement(aboutTextField);
    }

    /**
     * Method that checks if the Secret Question text field is displayed on
     * Add User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Secret Question text field is displayed or not
     */
    public boolean isSecretQuestionTextFieldDisplayed(){
        log.debug("isSecretQuestionFieldDisplayed");
        return isWebElementDisplayed(secretQuestionTextField);
    }

    /**
     * Method that checks if the SecretQuestion text field is displayed.
     * If it is not true Assert will fail the test here with message,
     * if it is true then it will clear the text box and enter the Secret Question
     * in the Secret Question field
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sSecretQuestion {String} - the Secret Question that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox typeSecretQuestion(String sSecretQuestion){
        log.debug(String.format("typeSecretQuestion (%s)", sSecretQuestion));
        Assert.assertTrue(isSecretQuestionTextFieldDisplayed(),
                "Secret Question Text Field is NOT displayed on the Add User Dialog Box!");
        secretQuestionTextField.getText();
        clearAndTypeTextToWebElement(secretQuestionTextField, sSecretQuestion);
        return this;
    }

    /**
     * Method that checks if the Secret Question text field is displayed and returns
     * the text entered as the Secret Question in the Secret Question text field
     *
     * @return {String} - the Secret Question typed in the text field
     */
    public String getSecretQuestion(){
        log.debug("getSecretQuestion()");
        Assert.assertTrue(isSecretQuestionTextFieldDisplayed(),
                "Secret Question Text Field is NOT displayed on the Add User Dialog Box!");
        return getValueFromWebElement(secretQuestionTextField);
    }

    /**
     * Method that checks if the Secret Answer text field is displayed on
     * Add User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Secret Answer text field is displayed or not
     */
    public boolean isSecretAnswerTextFieldDisplayed(){
        log.debug("isSecretAnswerFieldDisplayed");
        return isWebElementDisplayed(secretAnswerTextField);
    }

    /**
     * Method that checks if the Secret Answer text field is displayed.
     * If it is not true Assert will fail the test here with message,
     * if it is true then it will clear the text box and enter the Secret Answer
     * in the Secret Answer field
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sSecretAnswer {String} - the Secret Answer that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox typeSecretAnswer(String sSecretAnswer){
        log.debug(String.format("typeSecretAnswer (%s)", sSecretAnswer));
        Assert.assertTrue(isSecretAnswerTextFieldDisplayed(),
                "Secret Answer Text Field is NOT displayed on the Add User Dialog Box!");
        secretAnswerTextField.getText();
        clearAndTypeTextToWebElement(secretAnswerTextField, sSecretAnswer);
        return this;
    }

    /**
     * Method that checks if the Secret Answer text field is displayed and returns
     * the text entered as the Secret Answer in the Secret Answer text field
     *
     * @return {String} - the Secret Answer typed in the text field
     */
    public String getSecretAnswer(){
        log.debug("getSecretAnswer()");
        Assert.assertTrue(isSecretAnswerTextFieldDisplayed(),
                "Secret Answer Text Field is NOT displayed on the Add User Dialog Box!");
        return getValueFromWebElement(secretAnswerTextField);
    }

    /**
     * Method that checks if the Password text field is displayed on
     * Add User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Password text field is displayed or not
     */
    public boolean isPasswordTextFieldDisplayed(){
        log.debug("isPasswordTextFieldDisplayed");
        return isWebElementDisplayed(passwordTextField);
    }

    /**
     * Method that checks if the Password text field is displayed.
     * If it is not true Assert will fail the test here with message,
     * if it is true then it will clear the text box and enter the Password
     * in the Password field
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sPassword {String} - the Password that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox typePassword(String sPassword){
        log.debug(String.format("typePassword (%s)", sPassword));
        Assert.assertTrue(isPasswordTextFieldDisplayed(),
                "Password Text Field is NOT displayed on the Add User Dialog Box!");
        passwordTextField.getText();
        clearAndTypeTextToWebElement(passwordTextField, sPassword);
        return this;
    }

    /**
     * Method that checks if the Password text field is displayed and returns
     * the text entered as the Password in the Password text field
     *
     * @return {String} - the Password typed in the text field
     */
    public String getPassword(){
        log.debug("getPassword()");
        Assert.assertTrue(isPasswordTextFieldDisplayed(),
                "Password Text Field is NOT displayed on the Add User Dialog Box!");
        return getValueFromWebElement(passwordTextField);
    }

    /**
     * Method that checks if the Confirm Password text field is displayed on
     * Add User Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Confirm Password text field is displayed or not
     */
    public boolean isConfirmPasswordTextFieldDisplayed(){
        log.debug("isConfirmPasswordTextFieldDisplayed");
        return isWebElementDisplayed(confirmPasswordTextField);
    }

    /**
     * Method that checks if the Confirm Password text field is displayed.
     * If it is not true Assert will fail the test here with message,
     * if it is true then it will clear the text box and enter the Confirm Password
     * in the Confirm Password field
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sConfirmPassword {String} - the Confirm Password that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox typeConfirmPassword(String sConfirmPassword){
        log.debug(String.format("typeConfirmPassword (%s)", sConfirmPassword));
        Assert.assertTrue(isConfirmPasswordTextFieldDisplayed(),
                "Confirm Password Text Field is NOT displayed on the Add User Dialog Box!");
        confirmPasswordTextField.getText();
        clearAndTypeTextToWebElement(confirmPasswordTextField, sConfirmPassword);
        return this;
    }

    /**
     * Method that checks if the Confirm Password text field is displayed and returns
     * the text entered as the Confirm Password in the Confirm Password text field
     *
     * @return {String} - the Confirm Password typed in the text field
     */
    public String getConfirmPassword(){
        log.debug("getConfirmPassword()");
        Assert.assertTrue(isConfirmPasswordTextFieldDisplayed(),
                "Confirm Password Text Field is NOT displayed on the Add User Dialog Box!");
        return getValueFromWebElement(confirmPasswordTextField);
    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the Username Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Username Text Field or not
     */
    public boolean isUsernameTextFieldLabelVisible(){
        log.debug("isUsernameTextFieldLabelVisible");
        return isWebElementVisible(usernameTextFieldLabel);
    }

    /**
     * Method that checks if Username Text Field Label is visible on
     * Add User Dialog Box and gets Username Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getUsernameTextFieldLabel(){
        log.debug("getUsernameTextFieldLabel()");
        Assert.assertTrue(isUsernameTextFieldLabelVisible(),"Username Label is NOT visible on Add User Dialog Box");
        return getTextFromWebElement(usernameTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the First Name Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on First Name Text Field or not
     */
    public boolean isFirstNameTextFieldLabelVisible(){
        log.debug("isFirstNameTextFieldLabelVisible");
        return isWebElementVisible(firstNameTextFieldLabel);
    }

    /**
     * Method that checks if First Name Text Field Label is visible on
     * Add User Dialog Box and gets First Name Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getFirstNameTextFieldLabel(){
        log.debug("getFirstNameTextFieldLabel()");
        Assert.assertTrue(isFirstNameTextFieldLabelVisible(),"First Name Label is NOT visible on Add User Dialog Box");
        return getTextFromWebElement(firstNameTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the Last Name Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Last Name Text Field or not
     */
    public boolean isLastNameTextFieldLabelVisible(){
        log.debug("isLastNameTextFieldLabelVisible");
        return isWebElementVisible(lastNameTextFieldLabel);
    }

    /**
     * Method that checks if Last Name Text Field Label is visible on
     * Add User Dialog Box and gets Last Name Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getLastNameTextFieldLabel(){
        log.debug("getLastNameTextFieldLabel()");
        Assert.assertTrue(isLastNameTextFieldLabelVisible(),"Last Name Label is NOT visible on Add User Dialog Box");
        return getTextFromWebElement(lastNameTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the Email Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Email Text Field or not
     */
    public boolean isEmailTextFieldLabelVisible(){
        log.debug("isEmailTextFieldLabelVisible");
        return isWebElementVisible(emailTextFieldLabel);
    }

    /**
     * Method that checks if Email Text Field Label is visible on
     * Add User Dialog Box and gets Email Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getEmailTextFieldLabel(){
        log.debug("getEmailTextFieldLabel()");
        Assert.assertTrue(isEmailTextFieldLabelVisible(),"Email Label is NOT visible on Add User Dialog Box");
        return getTextFromWebElement(emailTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the About Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on About Text Field or not
     */
    public boolean isAboutTextFieldLabelVisible(){
        log.debug("isAboutTextFieldLabelVisible");
        return isWebElementVisible(aboutTextFieldLabel);
    }

    /**
     * Method that checks if About Text Field Label is visible on
     * Add User Dialog Box and gets About Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getAboutTextFieldLabel(){
        log.debug("getAboutTextFieldLabel()");
        Assert.assertTrue(isAboutTextFieldLabelVisible(),"About Label is NOT visible on Add User Dialog Box");
        return getTextFromWebElement(aboutTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the Secret Question Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Secret Question Text Field or not
     */
    public boolean isSecretQuestionTextFieldLabelVisible(){
        log.debug("isSecretQuestionTextFieldLabelVisible");
        return isWebElementVisible(secretQuestionTextFieldLabel);
    }

    /**
     * Method that checks if Secret Question Text Field Label is visible on
     * Add User Dialog Box and gets Secret Question Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getSecretQuestionTextFieldLabel(){
        log.debug("getSecretQuestionTextFieldLabel()");
        Assert.assertTrue(isSecretQuestionTextFieldLabelVisible(),"Secret Question Label is NOT visible on Add User Dialog Box");
        return getTextFromWebElement(secretQuestionTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the Secret Answer Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Secret Answer Text Field or not
     */
    public boolean isSecretAnswerTextFieldLabelVisible(){
        log.debug("isSecretAnswerTextFieldLabelVisible");
        return isWebElementVisible(secretAnswerTextFieldLabel);
    }

    /**
     * Method that checks if Secret Answer Text Field Label is visible on
     * Add User Dialog Box and gets Secret Answer Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getSecretAnswerTextFieldLabel(){
        log.debug("getSecretAnswerTextFieldLabel()");
        Assert.assertTrue(isSecretAnswerTextFieldLabelVisible(),"Secret Answer Label is NOT visible on Add User Dialog Box");
        return getTextFromWebElement(secretAnswerTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the Password Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Password Text Field or not
     */
    public boolean isPasswordTextFieldLabelVisible(){
        log.debug("isPasswordTextFieldLabelVisible");
        return isWebElementVisible(passwordTextFieldLabel);
    }

    /**
     * Method that checks if Password Text Field Label is visible on
     * Add User Dialog Box and gets Password Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getPasswordTextFieldLabel(){
        log.debug("getPasswordTextFieldLabel()");
        Assert.assertTrue(isPasswordTextFieldLabelVisible(),"Password Label is NOT visible on Add User Dialog Box");
        return getTextFromWebElement(passwordTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add User Dialog Box,
     * the Confirm Password Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Confirm Password Text Field or not
     */
    public boolean isConfirmPasswordTextFieldLabelVisible(){
        log.debug("isConfirmPasswordTextFieldLabelVisible");
        return isWebElementVisible(confirmPasswordTextFieldLabel);
    }

    /**
     * Method that checks if Confirm Password Text Field Label is visible on
     * Add User Dialog Box and gets Confirm Password Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getConfirmPasswordTextFieldLabel(){
        log.debug("getConfirmPasswordTextFieldLabel()");
        Assert.assertTrue(isConfirmPasswordTextFieldLabelVisible(),"Confirm Password Label is NOT visible on Add User Dialog Box");
        return getTextFromWebElement(confirmPasswordTextFieldLabel);
    }
}
