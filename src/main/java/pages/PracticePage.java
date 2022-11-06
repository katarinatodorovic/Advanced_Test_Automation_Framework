package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import ru.yandex.qatools.ashot.Screenshot;
import utils.ScreenshotUtils;

public class PracticePage extends CommonLoggedInPage{

    // Locators
    private final String PRACTICE_PAGE_URL = getPageUrl(PageUrlPaths.PRACTICE_PAGE);
    private final String draggableImageLocatorString = "#drag-image";
    private final String dragAreaLocatorString= "#drag-image";
    private final String dropAreaLocatorString= "#drop-area";

    private final By draggableImageLocator = By.id("drag-image");
    private final By samsaraLogoImageLocator = By.xpath("//img[@src='images/samsara.png']");
    private final String samsaraImgFile = "SamsaraLogo.png";

    // Page Factory WebElements
    @FindBy(id = "useless-tooltip")
    private WebElement  uselessTooltipTitle;

    @FindBy(id = "useless-tooltip-text")
    private WebElement  uselessTooltipText;

    @FindBy(id = "drag-image")
    private WebElement  draggableImage;

    @FindBy(id = "drag-area")
    private WebElement  dragArea;

    @FindBy(id = "drop-area")
    private WebElement  dropArea;

    @FindBy(id = "drag-and-drop-message")
    private WebElement  dragAndDropMessage;

    // if we want to use only PageFactory to locate elements
    @FindBy(xpath = "//div[@id='drag-area']//img[@id='drag-image']")
    private WebElement draggableImageInDragArea;

    @FindBy(xpath = "//div[@id='drop-area']//img[@id='drag-image']")
    private WebElement draggableImageInDropArea;


    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public PracticePage(WebDriver driver) {
        super(driver);
        log.trace("new PracticePage()");
    }

    /**
     * Method that returns an instance of the PracticePage if bVerify is true
     * then it verifies if we are on the PracticePage, and wait for loading of
     * the PracticePage to be completed, if it is false then it returns an instance
     * of the PracticePage. This method can be used for negative scenario also -
     * e.g. can we open the PracticePage even if we are logged out or only when we are logged in
     *
     * @param bVerify {boolean} - if you want to verify that we are on the PracticePage or not
     *
     * @return {PracticePage} - an instance of the PracticePage
     */
    public PracticePage open(boolean bVerify){
        openUrl(PRACTICE_PAGE_URL);
        log.debug(String.format("Open PracticePage(): %s",PRACTICE_PAGE_URL));
        if(bVerify){
            verifyPracticePage();
        }
        return this;
    }

    /**
     * Method that opens the PracticePage and can be done with or without
     * verification of the PracticePage
     *
     * @return {PracticePage} -  an instance of the PracticePage
     */
    public PracticePage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the PracticePage,
     * wait for loading of the PracticePage to be completed and
     * return an instance of the PracticePage
     *
     * @return {PracticePage} - an instance of the PracticePage
     */
    public PracticePage verifyPracticePage(){

        log.debug("verifyPracticePage");
        waitForUrlChangeToExactUrl(PRACTICE_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    /**
     * Method that checks if Useless Tooltip is displayed and returns
     * true if it is displayed
     *
     * @return {boolean} - if Useless Tooltip is displayed or not
     */
    public boolean isUselessTooltipDisplayed(){
        log.debug("isUselessTooltipDisplayed()");
        return isWebElementDisplayed(uselessTooltipText);
    }

    /**
     * Method that checks if the Useless Tooltip is displayed,
     * gets Useless Tooltip text and return that text as a String
     *
     * @return {String} - text that is being displayed as Useless Tooltip
     * on mouse hover
     */
    public  String getUselessTooltipText(){
        log.debug("getUselessTooltipText()");
        moveMouseToWebElement(uselessTooltipTitle);
        Assert.assertTrue(isUselessTooltipDisplayed(),
                "Useless Tooltip is NOT displayed on the Practice Page!");
        return getTextFromWebElement(uselessTooltipText);
    }

    /**
     * Method that checks if Drag And Drop Message is displayed and returns
     * true if it is displayed
     *
     * @return {boolean} - if Drag An dDrop Message is displayed or not
     */
    public boolean isDragAndDropMessageDisplayed(){
        log.debug("isDragAndDropMessageDisplayed()");
        return isWebElementDisplayed(dragAndDropMessage);
    }

    /**
     * Method that checks if the Drag And Drop Message is displayed,
     * gets Drag And Drop Message text and return that text as a String
     *
     * @return {String} - text that is being displayed as Drag And Drop Message
     */
    public  String geDragAndDropMessageText(){
        log.debug("geDragAndDropMessageText()");
        Assert.assertTrue(isDragAndDropMessageDisplayed(),
                "Drag And Drop Message is NOT displayed on the Practice Page!");
        return getTextFromWebElement(dragAndDropMessage);
    }

    /**
     * Method that checks if Image is present in Drag Area
     * true if it is present
     *
     * @return {boolean} - if image is present in Drag area or not
     */
    public boolean isImagePresentInDragArea(){
        log.debug("isImagePresentInDragArea()");
        // if we only use PageFactory this method will return the line below
        // instead of isNestedWebElementDisplayed()
       //return isWebElementDisplayed(draggableImageInDragArea);
        return isNestedWebElementDisplayed(dragArea, draggableImageLocator);
    }

    /**
     * Method that checks if Image is present in Drop Area
     * true if it is present
     *
     * @return {boolean} - if image is present in Drop area or not
     */
    public boolean isImagePresentInDropArea(){
        log.debug("isImagePresentInDropArea()");
        // if we only use PageFactory this method will return the line below
        // instead of isNestedWebElementDisplayed()
        //return isWebElementDisplayed(draggableImageInDropArea);
        return isNestedWebElementDisplayed(dropArea, draggableImageLocator);
    }

    /**
     * Method that drags and drops an image on the Practice page using JS
     *
     * @return {PracticePage} - an instance of the PracticePage
     */
    public PracticePage dragAndDropImage(){
        log.debug("dragAndDropImage()");
        doDragAndDropJS(draggableImageLocatorString, dropAreaLocatorString);
        // Because DOM structure is refreshed we need to return new instance
        // of the Practice Page instead of return this and avoid stale element exception;
        return new PracticePage(driver).verifyPracticePage();
    }

    /**
     * Method that clicks on Samsara Logo image
     *
     * @return {WelcomePage} - an instance of the Welcome Page
     */
    public WelcomePage clickSamsaraLogoImage() {
        log.debug("clickSamsaraLogoImage()");
        Point location = ScreenshotUtils.getImageCenterLocation(driver, samsaraImgFile, 0);
        clickOnLocation(location);
       // clickOnLocationJS(location);
        return new WelcomePage(driver);
    }

    /**
     * Method that get the Samsara Logo Image using ashot
     * (an image that can be used as a reference to be compared with the image taken by the test)
     *
     * @return {Screenshot} - a Screenshot of the Samsara Logo image
     */
    public Screenshot getSamsaraLogoImageSnapShotAS(){
        log.debug("getSamsaraLogoImageSnapShotAS()");
        WebElement profileImage = getWebElement(samsaraLogoImageLocator);
        return ScreenshotUtils.takeScreenShotOfWebElementAS(driver, profileImage);
    }

    /**
     * Method that save the Samsara Logo Image using ashot
     * (an image that can be used as a reference to be compared with the image taken by the test)
     *
     * @param sImageName {String} - name that image will be saved as
     */
    public void saveSamsaraLogoImageSnapShotAS(String sImageName){
        log.debug("saveSamsaraLogoImageSnapShotAS()");
     //   WebElement profileImage = getWebElement(samsaraLogoImageLocator);
        ScreenshotUtils.saveScreenShotOfWebElementAS(getSamsaraLogoImageSnapShotAS(), sImageName);
    }
}
