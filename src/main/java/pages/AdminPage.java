package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class  AdminPage extends CommonLoggedInPage{

    private final String ADMIN_PAGE_URL = getPageUrl(PageUrlPaths.ADMIN_PAGE);

    // Locators
     @FindBy(id="usersAllowed")
     private WebElement allowUserToShareRegistrationCodeCheckbox;

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public AdminPage(WebDriver driver) {

        super(driver);
        log.trace("new AdminPage()");
    }

    /**
     * Method that returns an instance of the AdminPage if bVerify is true
     * then it verifies if we are on the AdminPage, and wait for loading of
     * the AdminPage to be completed; if it is false then it returns an
     * instance of the AdminPage. This method can be used for negative scenario
     * also - e.g. can we open the AdminPage even if we are logged out, or
     * it can be opened only when we are logged in
     *
     * @param bVerify {boolean} - if you want to verify that we are on the AdminPage or not
     *
     * @return {AdminPage} - an instance of the AdminPage
     */
    public AdminPage open(boolean bVerify){
        openUrl(ADMIN_PAGE_URL);
        log.debug(String.format("Open AdminPage(): %s",ADMIN_PAGE_URL));
        if(bVerify){
            verifyAdminPage();
        }
        return this;
    }

    /**
     * Method that opens the AdminPage and can be done with or without
     * verification of the AdminPage
     *
     * @return {AdminPage} -  an instance of the AdminPage
     */
    public AdminPage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the AdminPage,
     * wait for loading of the AdminPage to be completed and
     * returns an instance of the AdminPage
     *
     * @return {AdminPage} - an instance of the AdminPage
     */
    public AdminPage verifyAdminPage(){
        log.debug("verifyAdminPage()");
        waitForUrlChangeToExactUrl(ADMIN_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    /**
     * Method that checks if Allow User To Share Registration Code checkbox
     * if displayed or not
     *
     * @return {boolean} - if the WebElement was displayed on the page or not
     */
    public boolean isAllowUserToShareRegistrationCodeCheckboxDisplayed(){
        log.debug("isAllowUserToShareRegistrationCodeCheckboxDisplayed()");
        return isWebElementDisplayed(allowUserToShareRegistrationCodeCheckbox);
    }

    /**
     * Method that checks if Allow User To Share Registration Code checkbox
     * is already checked but first check if the checkbox is displayed
     *
     * @return {boolean} - if the WebElement was checked or not
     */
    public boolean isAllowUserToShareRegistrationCodeCheckboxChecked(){
        log.debug("isAllowUserToShareRegistrationCodeCheckboxChecked()");
        Assert.assertTrue(isAllowUserToShareRegistrationCodeCheckboxDisplayed(),
                "Allow User To Share Registration Code checkbox is not displayed on Admin Page!");
        return isWebElementSelected(allowUserToShareRegistrationCodeCheckbox);
    }

    /**
     * Method that checks if Allow User To Share Registration Code checkbox
     * is checked - if it is not then check otherwise leave checked
     *
     * @return {AdminPage} - an instance of the AdminPage
     */
    public AdminPage checkAllowUserToShareRegistrationCode(){
        log.debug("checkAllowUserToShareRegistrationCode()");
        if(!isAllowUserToShareRegistrationCodeCheckboxChecked()){
            clickOnWebElement(allowUserToShareRegistrationCodeCheckbox);
        }
        return this;
    }

    /**
     * Method that checks if Allow User To Share Registration Code checkbox
     * is unchecked - if it is not then uncheck otherwise leave unchecked
     *
     * @return {AdminPage} - an instance of the AdminPage
     */
    public AdminPage uncheckAllowUserToShareRegistrationCode(){
        log.debug("checkAllowUserToShareRegistrationCode()");
        if(isAllowUserToShareRegistrationCodeCheckboxChecked()){
            clickOnWebElement(allowUserToShareRegistrationCodeCheckbox);
        }
        return this;
    }
}
