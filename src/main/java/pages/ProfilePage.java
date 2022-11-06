package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.Screenshot;
import utils.ScreenshotUtils;
import java.awt.image.BufferedImage;

public class ProfilePage extends CommonLoggedInPage{

    private final String PROFILE_PAGE_URL = getPageUrl(PageUrlPaths.PROFILE_PAGE);
    private final By profileImageLocator = By.id("profile-img");

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public ProfilePage(WebDriver driver) {
        super(driver);
        log.trace("new ProfilePage()");
    }

    /**
     * Method that returns an instance of the ProfilePage if bVerify is true
     * then it verifies if we are on the ProfilePage,
     * and wait for loading of the ProfilePage to be completed, if it is false then it
     * returns an instance of the ProfilePage
     * This method can be used for negative scenario also - e.g. can we open the ProfilePage
     * even if we are logged out or only when we are logged in
     *
     * @param bVerify {boolean} - if you want to verify that we are on the ProfilePge or not
     *
     * @return {ProfilePage} - an instance of the ProfilePage
     */
    public ProfilePage open(boolean bVerify){
        openUrl(PROFILE_PAGE_URL);
        log.debug(String.format("Open ProfilePage(): %s",PROFILE_PAGE_URL));
        if(bVerify){
            verifyProfilePage();
        }
        return this;
    }

    /**
     * Method that opens the ProfilePage and can be done with or without
     * verification of the ProfilePage
     *
     * @return {ProfilePage} -  an instance of the ProfilePage
     */
    public ProfilePage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the ProfilePage,
     * wait for loading of the ProfilePage to be completed and
     * return an instance of the ProfilePage
     *
     * @return {ProfilePage} - an instance of the ProfilePage
     */
    public ProfilePage verifyProfilePage(){
        log.debug("verifyProfilePage");
        waitForUrlChangeToExactUrl(PROFILE_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    /**
     * Method that get the Profile Image using ashot
     * (an image that can be used as a reference to be compared with the image taken by the test)
     *
     * @return {Screenshot} - a Screenshot of the Profile image
     */
    public BufferedImage getProfileImageSnapShot(){
        log.debug("getProfileImageSnapShot()");
        WebElement profileImage = getWebElement(profileImageLocator);
        return ScreenshotUtils.takeScreenShotOfWebElement(driver, profileImage);
    }

    /**
     * Method that get the Profile Image using ashot
     * (an image that can be used as a reference to be compared with the image taken by the test)
     *
     * @return {Screenshot} - a Screenshot of the Profile image
     */
    public Screenshot getProfileImageSnapShotAS(){
        log.debug("getProfileImageSnapShotAS()");
        WebElement profileImage = getWebElement(profileImageLocator);
        return ScreenshotUtils.takeScreenShotOfWebElementAS(driver, profileImage);
    }

    /**
     * Method that save the Profile Image using ScreenShotUtils class
     * (an image that can be used as a reference to be compared with the image taken by the test)
     *
     * @param sImageName {String} - name that image will be saved as
     */
    public void saveProfileImageSnapShot(String sImageName){
        log.debug("saveProfileImageSnapShot()");
        WebElement profileImage = getWebElement(profileImageLocator);
        // first save image and store as a reference to compare
        ScreenshotUtils.saveScreenshotOfWebElement(driver, profileImage, sImageName);
    }

    /**
     * Method that save the Profile Image using ashot
     * (an image that can be used as a reference to be compared with the image taken by the test)
     *
     * @param sImageName {String} - name that image will be saved as
     */
    public void saveProfileImageSnapShotAS(String sImageName){
        log.debug("saveProfileImageSnapShotAS()");
        WebElement profileImage = getWebElement(profileImageLocator);
        ScreenshotUtils.saveScreenShotOfWebElementAS(getProfileImageSnapShotAS(),sImageName);
    }
}
