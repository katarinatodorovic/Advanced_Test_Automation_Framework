package pages;

import data.PageUrlPaths;
import data.Time;
import objects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * A class which is for storing the items related to the Login page
 * the Login page is the same as logged out page
 */
public class  LoginPage extends CommonLoggedOutPage{

    private final String LOGIN_PAGE_URL = getPageUrl(PageUrlPaths.LOGIN_PAGE);

    // Every field in this class should be private because we don't want
    // someone out of this page especially in test to directly access this web elements or this locator
    // because of it is easier to maintain than to go in every class and change one by one element
    // here we have methods to get those elements and that elements become independent of tool
    // that we use like Selenium or some other

    private final String loginBoxLocatorString = "//div[@id='loginbox']";
    private final By usernameTextFieldLocator = By.id("username");
    private final By passwordTextFieldLocator = By.id("password");
    // Text (<button type="button">Click Me!</button>)is not good for catching
    // web element for verification because if we have localization our text
    // will change and the method will not be working
    // even dough button is there, and we will get an error that web
    // element can't be found
    private final By loginButtonLocator =
        By.xpath(loginBoxLocatorString + "//input[contains(@class, 'btn-primary')]");

    private final By successMessageLocator =
            By.xpath(loginBoxLocatorString + "//div[contains(@class, 'alert-success')]");
    private final By errorMessageLocator =
            By.xpath(loginBoxLocatorString + "//div[contains(@class, 'alert-danger')]");

    private final By createAccountLinkLocator = By.xpath(loginBoxLocatorString + "//a[@href='" + PageUrlPaths.REGISTER_PAGE + "']");
    private final By resetPasswordLinkLocator = By.xpath(loginBoxLocatorString + "//a[@href='" + PageUrlPaths.RESET_PASSWORD_PAGE + "']");

    // Bad practise because, every method should get a WebElement at that moment that element is used
    // private final WebElement usernameTextField = driver.findElement(usernameTextFieldLocator);
    // if DOM structure is changed or something is refreshed we will get an error stale element ...
    // or reference element is not attached to dom structure...

    // If we make one global WebElement like this:
    // private final WebElement userNameTextField = getWebElement(usernameTextFieldLocator);
    // this is the one of the awful practice because with this approach we have a lot of
    // StaleElementReferenceException exceptions. This way WebElement is initialized only once
    // with initialization of the page and every method depending on this element, so when DOM
    // structure is changed or refreshed every element will be messed up, the alternative and better
    // way to this approach private final WebElement userNameTextField = getWebElement(usernameTextFieldLocator);
    // is PageFactory

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public LoginPage(WebDriver driver){
        super(driver);
        log.trace("new LogInPage()");
    }

    /**
     * Method that opens the LoginPage verify that we are on that page
     * and returns the instance of LoginPage
     *
     * @param bVerify {boolean} - if we want to verify that we are on LoginPage or not
     *
     * @return {LoginPage} - an instance of the LoginPage
     */
    //here LOGIN_PAGE_URL is parametrized because if there is a change on the URL path
    //we can modify our method just on one place in order to work instead on multiple places
    public LoginPage open(boolean bVerify){
        openUrl(LOGIN_PAGE_URL);
        log.debug(String.format("Open LoginPage(): %s",LOGIN_PAGE_URL));
        if(bVerify){
            verifyLoginPage();
        }
        return this;
    }

    /**
     * Method that opens the LoginPage and can be done with or without
     * verification of the LoginPage
     *
     * @return {LoginPage} -  an instance of the LoginPage
     */
    public LoginPage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the LoginPage, wait for loading of
     * the Login Page to be completed and returns an instance of the LoginPage
     * This method can be used for negative scenario also - e.g. can we open the LoginPage
     * even if we are logged out or only when we are logged in
     *
     * @return {LoginPage} - an instance of the LoginPage
     */
    public LoginPage verifyLoginPage(){

        log.debug("verifyLoginPage");
        waitForUrlChange(LOGIN_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
        //here can be added wait for specific element to be loaded
    }

    /**
     * Method that checks if username text field is displayed and returns
     * true if it is displayed
     *
     * @return {boolean} - if username text field is displayed or not
     */
    public boolean isUsernameTextFieldDisplayed(){
        log.debug("isUsernameTextFieldDisplayed");
        return isWebElementDisplayed(usernameTextFieldLocator);
    }

    /**
     * Method that checks if username text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and will enter the username in the username field,
     * @description - if there is no Assert and test failed, it breaks with no
     * such element exception, and this approach is better for triage
     *
     * @param sUsername {String} - the username that we want to type
     *
     * @return {LoginPage} - an instance of the LoginPage
     */
    public LoginPage typeUsername(String sUsername){
        log.debug(String.format("typeUsername (%s)", sUsername));
        Assert.assertTrue(isUsernameTextFieldDisplayed(),
                "Username Text Field is NOT displayed on the Login Page!");
        WebElement usernameTextField = getWebElement(usernameTextFieldLocator);
        clearAndTypeTextToWebElement(usernameTextField, sUsername);
        return this;
    }

    /**
     * Method that checks if username text field is displayed and returns
     * the text entered as username in the username text field
     *
     * @return {String} - the username typed in the text field
     */
    public String getUsername(){
        log.debug("getUsername ");
        WebElement usernameTextField = getWebElement(usernameTextFieldLocator);
        Assert.assertTrue(isUsernameTextFieldDisplayed(),
                "Username Text Field is NOT displayed on the Login Page!");
        return getValueFromWebElement(usernameTextField);
    }

    /**
     * Method that checks if password text field is displayed and returns
     * true if it is displayed
     *
     * @return {boolean} - if password text field is displayed or not
     */
    public boolean isPasswordTextFieldDisplayed(){
        log.debug("isPasswordTextFieldDisplayed");
        return isWebElementDisplayed(passwordTextFieldLocator);
    }

    /**
     * Method that checks if password text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the password in the password field,
     * @description - if there is no Assert and test failed, it breaks with no
     * such element exception, and this approach is better for triage
     *
     * @param sPassword {String} - the password that we want to type in the text field
     *
     * @return {LoginPage} - an instance of the LoginPage
     */
    public LoginPage typePassword(String sPassword){
        log.debug(String.format("typePassword (%s)",sPassword));
        Assert.assertTrue(isPasswordTextFieldDisplayed(),
                "Password Text Field is NOT displayed on the Login Page!");
        WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
        clearAndTypeTextToWebElement(passwordTextField, sPassword);
        return this;
    }

    /**
     * Method that checks if password text field is displayed and returns
     * the text entered as password in the username text field
     *
     * @return {String} - the password typed in the text field
     */
    public String getPassword(){
        log.debug("getPassword ");
        Assert.assertTrue(isPasswordTextFieldDisplayed(),
                "Password Text Field is NOT displayed on the Login Page!");
        WebElement passwordTextField = getWebElement(passwordTextFieldLocator);
        return getValueFromWebElement(passwordTextField);
    }

    /**
     * Method that checks if the Login button is displayed
     *
     * @return {WebElement} - the Login button
     */
    public boolean isLoginButtonDisplayed(){
        log.debug("isLoginButtonDisplayed");
        return isWebElementDisplayed(loginButtonLocator);
    }

    /**
     * Method that checks if the Login button is displayed,
     * and if it is displayed then clicks on the Login button
     */
    private void clickOnLoginButton() {
        log.debug("clickOnLoginButton()");
        Assert.assertTrue(isLoginButtonDisplayed(),
                "Login Button is NOT displayed on the Login Page!");
        WebElement loginButton = getWebElement(loginButtonLocator);
        clickOnWebElement(loginButton);
    }

    /**
     * Method that clicks on the Login button and verify if we are ended up on
     * the WelcomePage and returns an instance of the WelcomePage
     *
     * @return {WelcomePage} - an instance of the WelcomePage
     */
    public WelcomePage clickOnLoginButtonVerifyWelcomePage(){
        log.debug("clickOnLoginButtonVerifyWelcomePage()");
        clickOnLoginButton();
        WelcomePage welcomePage = new WelcomePage(driver);
        return welcomePage.verifyWelcomePage();
    }


    /**
     * Method that clicks on the login button and verify if we are still on
     * the LoginPage and returns an instance of the LoginPage
     * e.g. - when we enter the wrong credentials we are still on the LoginPage
     *
     * @return {LoginPage} - an instance of the LoginPage
     */
    public LoginPage clickOnLoginButtonVerifyLoginPage(){
        log.debug("clickOnLoginButtonNoWelcome()");
        clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.verifyLoginPage();
    }

    /**
     * Method that checks if the Login button is displayed,
     * gets the Login button title and return text as String
     *
     * @return {String} - text that is being displayed on Login button
     */
    public String getLoginButtonTitle(){
        log.debug("getLoginButtonTitle()");
        Assert.assertTrue(isLoginButtonDisplayed(),
                "Login Button is NOT displayed on the Login Page!");
        //if we had loginButton as static variable it could happen that we have
        // StaleElementReferenceException
        WebElement loginButton = getWebElement(loginButtonLocator);
        return getValueFromWebElement(loginButton);
    }

    /**
     * Method that checks if successful logout message is displayed or not
     * on the LoginPage
     *
     * @return {boolean} - if message is displayed or not
     */
    public boolean isSuccessMessageDisplayed(){
        log.debug("isSuccessMessageDisplayed()");
        return isWebElementDisplayed(successMessageLocator);
    }

    /**
     * Method that checks if successful logout message is displayed or not,
     * and returns the message displayed after we are logged out
     *
     * @return {String} - message that is displayed after we are logged out
     */
    public String getSuccessMessage(){
        log.debug("getSuccessMessage");
        Assert.assertTrue(isSuccessMessageDisplayed(), "Success Message is NOT" +
                " present on the Login Page");
        WebElement successMessage = getWebElement(successMessageLocator);
        return getTextFromWebElement(successMessage);

    }

    /**
     * Method that checks if error logout message is displayed or not
     * on the LoginPage
     *
     * @return {boolean} - if message is displayed or not
     */
    public boolean isErrorMessageDisplayed(){
        log.debug("isErrorMessageDisplayed()");
        return isWebElementDisplayed(errorMessageLocator);
    }

    /**
     * Method that checks if error logout message is displayed or not,
     * and returns the message displayed after we are logged out
     *
     * @return {String} - message that is displayed after we are logged out
     */
    public String getErrorMessage(){
        log.debug("getErrorMessage");
        Assert.assertTrue(isErrorMessageDisplayed(), "Error Message is NOT" +
                " present on the Login Page");
        WebElement errorMessage = getWebElement(errorMessageLocator);
        return getTextFromWebElement(errorMessage);

    }

    /**
     * Method that checks if Create Account link is displayed or not
     * on the Logout page
     *
     * @return {boolean} - if link is displayed or not
     */
    public boolean isCreateAccountLinkDisplayed(){
        log.debug("isCreateAccountLinkDisplayed()");
        return isWebElementDisplayed(createAccountLinkLocator);
    }

    /**
     * Method that checks if the Create Account link is displayed on Logout page
     * or not, clicks on the Create Account link, verify that after click we are on
     * the Register page and returns an instance of the Register page
     *
     * @return {RegisterPage} - an instance of the RegisterPage
     */
    public RegisterPage clickCreateAccountLink(){
        log.debug("clickCreateAccountLink()");
        Assert.assertTrue(isCreateAccountLinkDisplayed(), "Create Account link is NOT displayed on Login Page!");
        WebElement createAccountLink = getWebElement(createAccountLinkLocator);
        clickOnWebElement(createAccountLink);
        RegisterPage registerPage = new RegisterPage(driver);
        return registerPage.verifyRegisterPage();
    }

    /**
     * Method that checks if the Create Account link is displayed on
     * Logout page or not and returns the Create Account link title
     *
     * @return {String} - Create Account link title
     */
    public String getCreateAccountLinkTitle(){
        log.debug("getCreateAccountLinkTitle()");
        Assert.assertTrue(isCreateAccountLinkDisplayed(),"Create Account link is NOT displayed on Login Pager");
        WebElement createAccountLink = getWebElement(createAccountLinkLocator);
        return getTextFromWebElement(createAccountLink);
    }

    /**
     * Method that checks if Reset Password link is displayed or not
     * on the Logout page
     *
     * @return {boolean} - if link is displayed or not
     */
    public boolean isResetPasswordLinkDisplayed(){
        log.debug("isResetPasswordLinkDisplayed()");
        return isWebElementDisplayed(createAccountLinkLocator);
    }

    /**
     * Method that checks if the Reset Password link is displayed on Logout page
     * or not, clicks on the Reset Password link, verify that after click we are on
     * the Reset Password page and returns an instance of the Reset Password page
     *
     * @return {Reset Password} - an instance of the Reset Password
     */
    public ResetPasswordPage clickResetPasswordLink(){
        log.debug("clickResetPasswordLink()");
        Assert.assertTrue(isResetPasswordLinkDisplayed(), "Reset Password link is NOT displayed on Login Page!");
        WebElement resetPasswordLink = getWebElement(resetPasswordLinkLocator);
        clickOnWebElement(resetPasswordLink);
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);
        return resetPasswordPage.verifyResetPasswordPage();
    }

    /**
     * Method that checks if the Reset Password link is displayed on
     * Logout page or not and returns the Reset Password link title
     *
     * @return {String} - Reset Password link title
     */
    public String getResetPasswordLinkTitle(){
        log.debug("getResetPasswordLinkTitle()");
        Assert.assertTrue(isResetPasswordLinkDisplayed(),"Reset Password link is NOT displayed on Login Pager");
        WebElement resetPasswordLink = getWebElement(resetPasswordLinkLocator);
        return getTextFromWebElement(resetPasswordLink);
    }

    /**
     * Method that enters username and password, and returns an instance
     * of the WelcomePage after we are logged in
     *
     * @param sUsername {String} - username
     * @param sPassword {String} - password
     *
     * @return {WelcomePage} - an instance of the WelcomePage
     */
    public WelcomePage login(String sUsername, String sPassword){
        //complex methods is justified when it stays on one page if it is
        //going through more than one page that is not POM
        log.info(String.format("login (%s, %s)", sUsername, sPassword));
        this.typeUsername(sUsername);
        this.typePassword(sPassword);
       return this.clickOnLoginButtonVerifyWelcomePage();
    }

    /**
     * Method that enters username and password of user that we passed as parameter,
     * and returns an instance of the WelcomePage after we are logged in
     * @description - it is useful when we create new User to check if that newly
     * created User can log in
     *
     * @param user {User} - User
     *
     * @return {WelcomePage} - an instance of the WelcomePage
     */
    public WelcomePage login(User user){
        log.info(String.format("login (%s)", user));
        return login(user.getUsername(),user.getPassword());
    }
}
