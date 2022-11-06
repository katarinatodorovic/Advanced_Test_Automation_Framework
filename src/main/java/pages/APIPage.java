package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;

public class APIPage extends CommonLoggedInPage{

    private final String API_PAGE_URL = getPageUrl(PageUrlPaths.API_PAGE);

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public APIPage(WebDriver driver) {
        super(driver);
        log.trace("new APIPage()");
    }

    /**
     * Method that returns an instance of the APIPage if bVerify is true
     * then it verifies if we are on the APIPage, and wait for loading of
     * the APIPage to be completed, if it is false then it returns an instance
     * of the APIPage. This method can be used for negative scenario also - e.g. can we
     * open the APIPage even if we are logged out or only when we are logged in
     *
     * @param bVerify {boolean} - if you want to verify that we are on the APIPage or not
     *
     * @return {APIPage} - an instance of the APIPage
     */
    public APIPage open(boolean bVerify){
        openUrl(API_PAGE_URL);
        log.debug(String.format("Open APIPage(): %s",API_PAGE_URL));
        if(bVerify){
            verifyAPIPage();
        }
        return this;
    }

    /**
     * Method that opens the APIPage and can be done with or without
     * verification of the APIPage
     *
     * @return {APIPage} -  an instance of the APIPage
     */
    public APIPage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the APIPage,
     * wait for loading of the APIPage to be completed and
     * return an instance of the APIPage
     *
     * @return {APIPage} - an instance of the APIPage
     */
    public APIPage verifyAPIPage(){

        log.debug("verifyAPIPage");
        waitForUrlChangeToExactUrl(API_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }
}
