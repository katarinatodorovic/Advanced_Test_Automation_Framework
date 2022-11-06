package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;

public class WelcomePage extends CommonLoggedInPage{

    private final String WELCOME_PAGE_URL = getPageUrl(PageUrlPaths.WELCOME_PAGE);

    /**
     * A constructor for a WebDriver that is defined by the parent CommonLoggedInPage class
     */
    public WelcomePage(WebDriver driver) {

        super(driver);
        log.trace("new WelcomePage()");
    }

    /**
     * Method that returns an instance of the WelcomePage if bVerify is true
     * then it verifies if we are on the WelcomePage,
     * and wait for loading of the WelcomePage to be completed, if it is false then it
     * returns an instance of the WelcomePage
     * This method can be used for negative scenario also - e.g. can we open the WelcomePage
     * even if we are logged out or only when we are logged in
     *
     * @return {WelcomePage} - an instance of the WelcomePage
     */
    public WelcomePage open(boolean bVerify){
        openUrl(WELCOME_PAGE_URL);
        log.debug(String.format("Open WelcomePage(): %s",WELCOME_PAGE_URL));
        if(bVerify){
            verifyWelcomePage();
        }
        return this;
    }

    /**
     * Method that opens the WelcomePage and can be done with or without
     * verification of the WelcomePage
     *
     * @return {WelcomePage} -  an instance of the WelcomePage
     */
    public WelcomePage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the WelcomePage,
     * wait for loading of the WelcomePage to be completed and
     * return an instance of the WelcomePage
     *
     * @return {WelcomePage} - an instance of the WelcomePage
     */
    public WelcomePage verifyWelcomePage(){

        log.debug("verifyWelcomePage");
        waitForUrlChangeToExactUrl(WELCOME_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }
}
