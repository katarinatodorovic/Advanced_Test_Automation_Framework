package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;

public class BrokenLinkPage extends CommonLoggedInPage{

    private final String BROKEN_LINK_PAGE_URL = getPageUrl(PageUrlPaths.BROKEN_LINK);

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public BrokenLinkPage(WebDriver driver) {
        super(driver);
        log.trace("new BrokenLinkPage()");
    }

    /**
     * Method that returns an instance of the BrokenLinkPage if bVerify is true
     * then it verifies if we are on the BrokenLinkPage,
     * and wait for loading of the BrokenLinkPage to be completed, if it is false then it
     * returns an instance of the BrokenLinkPage
     * This method can be used for negative scenario also - e.g. can we open the BrokenLinkPage
     * even if we are logged out or only when we are logged in
     *
     * @param bVerify {boolean} - if you want to verify that we are on the BrokenLinkPage or not
     *
     * @return {BrokenLinkPage} - an instance of the BrokenLinkPage
     */
    public BrokenLinkPage open(boolean bVerify){
        openUrl(BROKEN_LINK_PAGE_URL);
        log.debug(String.format("Open BrokenLinkPage(): %s",BROKEN_LINK_PAGE_URL));
        if(bVerify){
            verifyBrokenLinkPage();
        }
        return this;
    }

    /**
     * Method that opens the BrokenLinkPage and can be done with or without
     * verification of the BrokenLinkPage
     *
     * @return {BrokenLinkPage} - an instance of the BrokenLinkPage
     */
    public BrokenLinkPage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the BrokenLinkPage,
     * wait for loading of the BrokenLinkPage to be completed and
     * return an instance of the BrokenLinkPage
     *
     * @return {BrokenLinkPage} - an instance of the BrokenLinkPage
     */
    public BrokenLinkPage verifyBrokenLinkPage(){

        log.debug("verifyBrokenLinkPage");
        waitForUrlChangeToExactUrl(BROKEN_LINK_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }
}
