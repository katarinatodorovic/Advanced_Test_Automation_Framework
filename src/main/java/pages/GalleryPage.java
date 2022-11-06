package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;

public class GalleryPage extends CommonLoggedInPage{

    private final String GALLERY_PAGE_URL = getPageUrl(PageUrlPaths.GALLERY_PAGE);

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public GalleryPage(WebDriver driver) {
        super(driver);
        log.trace("new GalleryPage()");
    }

    /**
     * Method that returns an instance of the GalleryPage if bVerify is true
     * then it verifies if we are on the GalleryPage,and wait for loading of
     * the GalleryPage to be completed, if it is false then it returns an
     * instance of the GalleryPage. This method can be used for negative
     * scenario also - e.g. can we open the GalleryPage even if we are logged
     * out or only when we are logged in
     *
     * @param bVerify {boolean} - if you want to verify that we are on the AdminPage or not
     *
     * @return {GalleryPage} - an instance of the GalleryPage
     */
    public GalleryPage open(boolean bVerify){
        openUrl(GALLERY_PAGE_URL);
        log.debug(String.format("Open GalleryPage(): %s",GALLERY_PAGE_URL));
        if(bVerify){
            verifyGalleryPage();
        }
        return this;
    }

    /**
     * Method that opens the GalleryPage and can be done with or without
     * verification of the GalleryPage
     *
     * @return {GalleryPage} -  an instance of the GalleryPage
     */
    public GalleryPage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the GalleryPage,
     * wait for loading of the GalleryPage to be completed and
     * return an instance of the GalleryPage
     *
     * @return {GalleryPage} - an instance of the GalleryPage
     */
    public GalleryPage verifyGalleryPage(){

        log.debug("verifyGalleryPage");
        waitForUrlChangeToExactUrl(GALLERY_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }
}
