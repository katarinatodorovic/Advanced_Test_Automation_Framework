package pages;

import data.PageUrlPaths;
import data.Time;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class RegisterPage extends CommonLoggedOutPage{

    private final String REGISTER_PAGE_URL = getPageUrl(PageUrlPaths.REGISTER_PAGE);

    // Page Factory Locators
    @FindBy(id="register-user-form")
    private WebElement registerUserForm;
    @FindBy(xpath="//h4[@class='modal-title']")
    private WebElement registerUserFormTitle;
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

    @FindBy(id="usernameMessage")
    private WebElement usernameTextFieldErrorMessage;

    @FindBy(id="firstNameMessage")
    private WebElement firstNameTextFieldErrorMessage;

    @FindBy(id="lastNameMessage")
    private WebElement lastNameTextFieldErrorMessage;

    @FindBy(id="emailMessage")
    private WebElement emailTextFieldErrorMessage;

    @FindBy(id="aboutMessage")
    private WebElement aboutTextFieldErrorMessage;

    @FindBy(id="secretQuestionMessage")
    private WebElement secretQuestionTextFieldErrorMessage;

    @FindBy(id="secretAnswerMessage")
    private WebElement secretAnswerTextFieldErrorMessage;

    @FindBy(id="passwordMessage")
    private WebElement passwordTextFieldErrorMessage;

    @FindBy(id="repasswordMessage")
    private WebElement confirmPasswordTextFieldErrorMessage;

    @FindBy(id="submitButton")
    private WebElement signUpButton;

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public RegisterPage(WebDriver driver) {
        super(driver);
        log.trace("new RegisterPage()");
    }

    /**
     * Method that returns an instance of the RegisterPage if bVerify is true
     * then it verifies if we are on the RegisterPage, and wait for loading of
     * the RegisterPage to be completed, if it is false then it returns an
     * instance of the RegisterPage
     *
     * @param bVerify {boolean} - if you want to verify that we are on the RegisterPage or not
     *
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage open(boolean bVerify){
        openUrl(REGISTER_PAGE_URL);
        log.debug(String.format("Open RegisterPage(): %s",REGISTER_PAGE_URL));
        if(bVerify){
            verifyRegisterPage();
        }
        return this;
    }

    /**
     * Method that opens the RegisterPage and can be done with or without
     * verification of the RegisterPage
     *
     * @return {RegisterPage} -  an instance of the RegisterPage
     */
    public RegisterPage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the RegisterPage,
     * wait for loading of the RegisterPage to be completed and
     * return an instance of the RegisterPage
     *
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage verifyRegisterPage(){

        log.debug("verifyRegisterPage");
        waitForUrlChangeToExactUrl(REGISTER_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    /**
     * Method that checks if Register User Form is displayed on the Register page
     *
     * @return {boolean} - if Register User Form is displayed on the Register page
     */
    public boolean isRegisterUserFormDisplayed(){
        return isWebElementDisplayed(registerUserForm);
    }


    /**
     * Method that wait until Register User Form is loaded and
     * checks if Register User Form is open on the Register page
     *
     * @return {AddUserDialogBox} - an instance of AddUserDialogBox
     */
    public RegisterPage verifyRegisterUserForm(){
        log.debug("verifyRegisterUserForm()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isRegisterUserFormDisplayed(), "Register User Form is not opened");
        return this;

    }

    /**
     * Method that checks if Register User Form is closed on the Login page
     *
     * @param timeout {int} - timeout in seconds
     * @return {boolean} - if Register User Form is closed the Login page
     */
    public boolean isRegisterUserFormClosed(int timeout){
        return isWebElementInvisible(registerUserForm, timeout);
    }

    /**
     * Method that checks when we are on Register User Form,
     * the page title is displayed
     *
     * @return {boolean} - if Register User Form title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(registerUserFormTitle);
    }

    /**
     * Method that checks if the Register User Form title is displayed on
     * Register User Form and gets Register User Form title
     *
     * @return {String} - Register User Form title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on Register User Form");
        return getTextFromWebElement(registerUserFormTitle);
    }

    /**
     * Method that checks if the Username text field is displayed on RegisterPage
     * and returns true if it is displayed
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
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage typeUsername(String sUsername){
        log.debug(String.format("typeUsername (%s)", sUsername));
        Assert.assertTrue(isUsernameTextFieldDisplayed(),
                "Username Text Field is NOT displayed on the Register Page!");
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
                "Username Text Field is NOT displayed on the Register Page!");
        return getValueFromWebElement(usernameTextField);
    }

    /**
     * Method that checks if the First Name text field is displayed on RegisterPage
     * and returns true if it is displayed
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
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage typeFirstName(String sFirstName){
        log.debug(String.format("typeFirstName (%s)", sFirstName));
        Assert.assertTrue(isFirstNameTextFieldDisplayed(),
                "First Name Text Field is NOT displayed on the Register Page!");
        firstNameTextField.getText();
        clearAndTypeTextToWebElement(firstNameTextField, sFirstName);
        return this;
    }

    /**
     * Method that checks if the Username text field is displayed and returns
     * the text entered as the Username in the Username text field
     *
     * @return {String} - the Username typed in the text field
     */
    public String getFirstName(){
        log.debug("getFirstName()");
        Assert.assertTrue(isFirstNameTextFieldDisplayed(),
                "First Name Text Field is NOT displayed on the Register Page!");
        return getValueFromWebElement(firstNameTextField);
    }

    /**
     * Method that checks if the Last Name text field is displayed on RegisterPage
     * and returns true if it is displayed
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
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage typeLastName(String sLastName){
        log.debug(String.format("typeLastName (%s)", sLastName));
        Assert.assertTrue(isLastNameTextFieldDisplayed(),
                "Last Name Text Field is NOT displayed on the Register Page!");
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
                "Last Name Text Field is NOT displayed on the Register Page!");
        return getValueFromWebElement(lastNameTextField);
    }

    /**
     * Method that checks if the Email text field is displayed on RegisterPage
     * and returns true if it is displayed
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
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage typeEmailName(String sEmail){
        log.debug(String.format("typeEmail (%s)", sEmail));
        Assert.assertTrue(isEmailTextFieldDisplayed(),
                "Email Text Field is NOT displayed on the Register Page!");
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
                "Email Text Field is NOT displayed on the Register Page!");
        return getValueFromWebElement(emailTextField);
    }

    /**
     * Method that checks if the About text field is displayed on RegisterPage
     * and returns true if it is displayed
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
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage typeAbout(String sAbout){
        log.debug(String.format("typeAbout (%s)", sAbout));
        Assert.assertTrue(isAboutTextFieldDisplayed(),
                "About Text Field is NOT displayed on the Register Page!");
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
                "About Text Field is NOT displayed on the Register Page!");
        return getValueFromWebElement(aboutTextField);
    }

    /**
     * Method that checks if the Secret Question text field is displayed on RegisterPage
     * and returns true if it is displayed
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
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage typeSecretQuestion(String sSecretQuestion){
        log.debug(String.format("typeSecretQuestion (%s)", sSecretQuestion));
        Assert.assertTrue(isSecretQuestionTextFieldDisplayed(),
                "Secret Question Text Field is NOT displayed on the Register Page!");
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
                "Secret Question Text Field is NOT displayed on the Register Page!");
        return getValueFromWebElement(secretQuestionTextField);
    }

    /**
     * Method that checks if the Secret Answer text field is displayed on RegisterPage
     * and returns true if it is displayed
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
     * @return {RegisterPage } - an instance of the RegisterPage
     */
    public RegisterPage typeSecretAnswer(String sSecretAnswer){
        log.debug(String.format("typeSecretAnswer (%s)", sSecretAnswer));
        Assert.assertTrue(isSecretAnswerTextFieldDisplayed(),
                "Secret Answer Text Field is NOT displayed on the Register Page!");
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
                "Secret Answer Text Field is NOT displayed on the Register Page!");
        return getValueFromWebElement(secretAnswerTextField);
    }

    /**
     * Method that checks if the Password text field is displayed on RegisterPage
     * and returns true if it is displayed
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
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage typePassword(String sPassword){
        log.debug(String.format("typePassword (%s)", sPassword));
        Assert.assertTrue(isPasswordTextFieldDisplayed(),
                "Password Text Field is NOT displayed on the Register Page!");
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
                "Password Text Field is NOT displayed on the Register Page!");
        return getValueFromWebElement(passwordTextField);
    }

    /**
     * Method that checks if the Confirm Password text field is displayed on RegisterPage
     * and returns true if it is displayed
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
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage typeConfirmPassword(String sConfirmPassword){
        log.debug(String.format("typeConfirmPassword (%s)", sConfirmPassword));
        Assert.assertTrue(isConfirmPasswordTextFieldDisplayed(),
                "Confirm Password Text Field is NOT displayed on the Register Page!");
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
                "Confirm Password Text Field is NOT displayed on the Register Page!");
        return getValueFromWebElement(confirmPasswordTextField);
    }

    /**
     * Method that checks if the Sign-Up button is displayed on the RegisterPage dialog box
     *
     * @return {boolean} - if Sign-Up button is displayed or not
     */
    public boolean isSignUpButtonDisplayed(){
        log.debug("isSignUpButtonDisplayed");
        return isWebElementDisplayed(signUpButton);
    }

    /**
     * Method that checks if the Sign-Up button is Enabled on the RegisterPage dialog box,
     * but first it checks if button is displayed
     *
     * @return {boolean} - Sign-Up button
     */
    public boolean isSignUpButtonEnabled(){
        log.debug("isSignUpButtonEnabled");
        Assert.assertTrue(isSignUpButtonDisplayed(),
                "Sign Up Button is NOT displayed on the Register Page!");
        return isWebElementEnabled(signUpButton);
    }

    /**
     * Method that checks if the Sign-Up button is displayed on the RegisterPage dialog box,
     * clicks on the Sign-Up button, checks if RegisterPage dialog box is closed, and returns
     * an instance of the Login page
     *
     * @return {LoginPage} - an instance of the LoginPage
     */
    public LoginPage clickOnSignUpButton() {
        log.debug("clickOnSignUpButton()");
        Assert.assertTrue(isSignUpButtonEnabled(),
                "Sign Up Button is NOT Enabled on the Register Page!");
        clickOnWebElement(signUpButton);
        Assert.assertFalse(isRegisterUserFormDisplayed(), "Register User Form is not closed");
        return new LoginPage(driver).verifyLoginPage();
    }

    /**
     * Method that register User based on User that we are created in our test
     *
     * @param user {User} - User object
     *
     * @return {LoginPage} - LoginPage object
     */
    public LoginPage registerUser(User user){
        typeUsername(user.getUsername());
        typeFirstName(user.getFirstName());
        typeLastName(user.getLastName());
        typeEmailName(user.getEmail());
        typeAbout(user.getAbout());
        typeSecretQuestion(user.getSecretQuestion());
        typeSecretAnswer(user.getSecretAnswer());
        typePassword(user.getPassword());
        typeConfirmPassword(user.getPassword());
        return clickOnSignUpButton();
    }
}