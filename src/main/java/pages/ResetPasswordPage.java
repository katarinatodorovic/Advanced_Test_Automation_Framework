package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;

public class ResetPasswordPage extends CommonLoggedOutPage{
    private final String RESET_PASSWORD_URL = getPageUrl(PageUrlPaths.RESET_PASSWORD_PAGE);

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public ResetPasswordPage(WebDriver driver) {
        super(driver);
        log.trace("new ResetPasswordPage()");
    }

    /**
     * Method that returns an instance of the ResetPasswordPage if bVerify is true
     * then it verifies if we are on the ResetPasswordPage, and wait for loading of
     * the ResetPasswordPage to be completed, if it is false then it returns an
     * instance of the ResetPasswordPage
     *
     * @param bVerify {boolean} - if you want to verify that we are on the ResetPasswordPage or not
     *
     * @return {ResetPasswordPage} - an instance of the ResetPasswordPage
     */
    public ResetPasswordPage open(boolean bVerify){
        openUrl(RESET_PASSWORD_URL);
        log.debug(String.format("Open ResetPasswordPage(): %s",RESET_PASSWORD_URL));
        if(bVerify){
            verifyResetPasswordPage();
        }
        return this;
    }

    /**
     * Method that opens the ResetPasswordPage and can be done with or without
     * verification of the ResetPasswordPage
     *
     * @return {ResetPasswordPage} -  an instance of the ResetPasswordPage
     */
    public ResetPasswordPage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the ResetPasswordPage,
     * wait for loading of the ResetPasswordPage to be completed and
     * return an instance of the ResetPasswordPage
     *
     * @return {ResetPasswordPage} - an instance of the ResetPasswordPage
     */
    public ResetPasswordPage verifyResetPasswordPage(){
        log.debug("verifyResetPasswordPage");
        waitForUrlChangeToExactUrl(RESET_PASSWORD_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }
}
