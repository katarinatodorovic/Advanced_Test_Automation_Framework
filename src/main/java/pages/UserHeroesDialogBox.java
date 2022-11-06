package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import java.util.List;

public class UserHeroesDialogBox extends BasePageCLass{

    // Locators
    private final String userHeroesDialogBoxString = "//div[@id='heroesModal']";


    @FindBy(id="heroesModal")
    private WebElement userHeroesDialogBox;

    @FindBy(xpath="//table[@class='table']")
    private WebElement userHeroesTable;

    @FindBy(xpath=".//button[@class='btn btn-primary']")
    private WebElement addNewHeroButton;

    @FindBy(xpath=userHeroesDialogBoxString +"//button[contains(@class,'btn-default')]")
    private WebElement closeButton;

    @FindBy(xpath="//h4[contains(@class,'modal-title')]")
    private WebElement userHeroesDialogBoxTitle;

    @FindBy(xpath=userHeroesDialogBoxString + "//th[@class='name']")
    private WebElement columnName;

    @FindBy(xpath=userHeroesDialogBoxString + "//th[@class='type']")
    private WebElement columnClass;

    @FindBy(xpath=userHeroesDialogBoxString + "//th[@class='level']")
    private WebElement columnLevel;

    @FindBy(xpath = "//td[@class='name']")
    private WebElement heroesName;

    @FindBy(xpath = "//td[@class='type']")
    private WebElement heroesClass;

    @FindBy(xpath = "//td[@class='level']")
    private WebElement heroesLevel;

    /**
     * Method that creates the xpath for Hero Name in the User Heroes Table
     *
     * @return {String} - xpath for Hero Name
     */
    private String createXpathForHeroNameInUserHeroesTable(String sHeroName){
        return String.format(".//td[@class='name']/self::td[text()='%s']",sHeroName);
    }

    /**
     * Method that creates the xpath for Hero Class in the User Heroes Table
     *
     * @return {String} - xpath for Hero Class
     */
    private String createXpathForHeroClassInUserHeroesTable(String sHeroName){
        return String.format(".//td[@class='name']/self::td[text()='%s']/following-sibling::td[1]",sHeroName);
    }

    /**
     * Method that creates the xpath for Hero Level in the User Heroes Table
     *
     * @return {String} - xpath for Hero Level
     */
    private String createXpathForHeroLevelInUserHeroesTable(String sHeroName){
        return String.format(".//td[@class='name']/self::td[text()='%s']/following-sibling::td[2]",sHeroName);
    }

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public UserHeroesDialogBox(WebDriver driver) {
        super(driver);
        log.trace("new UserHeroesDialogBox()");
    }
    /**
     * Method that checks if User Heroes dialog box is open on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if User Heroes dialog box is visible on the Users page
     */
    private boolean isUserHeroesDialogBoxOpened(int timeout){
        return isWebElementVisible(userHeroesDialogBox, timeout);
    }

    /**
     * Method that checks if User Heroes dialog box is closed on the Users page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if User Heroes dialog box is closed the Users page
     */
    private boolean isUserHeroesDialogBoxClosed(int timeout){
        return isWebElementInvisible(userHeroesDialogBox, timeout);
    }

    /**
     * Method that waits until User Heroes dialog box is loaded and
     * checks if User Heroes dialog box is open on the Users page
     *
     * @return {UserHeroesDialogBox} - an instance of UserHeroesDialogBox
     */
    public UserHeroesDialogBox verifyUserHeroesDialogBox(){
        log.debug("verifyUserHeroesDialogBox()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isUserHeroesDialogBoxOpened(Time.TIME_SHORTER), "User Heroes Dialog Box is not opened");
        return this;
    }

    /**
     * Method that checks when we are on the User Heroes Dialog Box,
     * the page title is displayed
     *
     * @return {boolean} - if User Heroes Dialog Box title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(userHeroesDialogBoxTitle);
    }

    /**
     * Method that checks if the User Heroes Dialog Box title is displayed on
     * the User Heroes Dialog Box and gets User Heroes Dialog Box title
     *
     * @return {String} - User Heroes Dialog Box title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on User Heroes Dialog Box");
        return getTextFromWebElement(userHeroesDialogBoxTitle);
    }

    /**
     * Method that checks if the Close button is displayed on the User Heroes dialog box
     *
     * @return {boolean} - if Close button is displayed or not
     */
    public boolean isCloseButtonDisplayed(){
        log.debug("isCloseButtonDisplayed");
        return isWebElementDisplayed(closeButton);
    }

    /**
     * Method that checks if the Close button is displayed,
     * gets the Close button title and return text as a String
     *
     * @return {String} - text that is being displayed on Close button
     */
    public String getCloseButtonTitle(){
        log.debug("getCloseButtonTitle()");
        Assert.assertTrue(isCloseButtonDisplayed(),
                "Close button is NOT displayed on the User Heroes dialog box!");
        return getValueFromWebElement(closeButton);
    }

    /**
     * Method that checks if the Close button is displayed on the User Heroes dialog box,
     * clicks on the Close button, checks if User Heroes dialog box is closed, and returns
     * an instance of the Users page
     *
     * @return {HeroPage} - an instance of the UsersPage
     */
    public UsersPage clickOnCloseButton() {
        log.debug("clickOnCloseButton()");
        Assert.assertTrue(isCloseButtonDisplayed(),
                "Close Button is NOT displayed on the User Heroes Dialog Box!");
        clickOnWebElement(closeButton);
        Assert.assertTrue(isUserHeroesDialogBoxClosed(Time.TIME_SHORTER), "User Heroes Dialog Box is not closed");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    /**
     * Method that checks if the Add New Hero button is displayed on the User Heroes dialog box
     *
     * @return {boolean} - if Add New Hero button is displayed or not
     */
    public boolean isAddNewHeroButtonDisplayed(){
        log.debug("isAddNewHeroDisplayed");
        return isWebElementDisplayed(addNewHeroButton);
    }

    /**
     * Method that checks if the Add New Hero button is displayed,
     * gets the Add New Hero button title and return text as a String
     *
     * @return {String} - text that is being displayed on Add New Hero button
     */
    public String getAddNewHeroButtonTitle(){
        log.debug("getAddNewHeroButtonTitle()");
        Assert.assertTrue(isAddNewHeroButtonDisplayed(),
                "Add New Hero button is NOT displayed on the User Heroes dialog box!");
        return getValueFromWebElement(addNewHeroButton);
    }

    /**
     * Method that checks if the Add New Hero button is displayed on the User Heroes dialog box,
     * clicks on the Add New Hero button, checks if Add Hero dialog box is opened, and returns
     * an instance of the AddHeroDialogBox
     *
     * @return {HeroPage} - an instance of the AddHeroDialogBox
     */
    public AddHeroDialogBox clickOnAddNewHeroButton() {
        log.debug("clickOnAddNewHeroButton()");
        Assert.assertTrue(isAddNewHeroButtonDisplayed(),
                "Add New Hero Button is NOT displayed on the User Heroes Dialog Box!");
        clickOnWebElement(addNewHeroButton);
        AddHeroDialogBox addHeroDialogBox = new AddHeroDialogBox(driver);
        Assert.assertTrue(addHeroDialogBox.isAddHeroDialogBoxOpened(Time.TIME_SHORTER), "User Heroes Dialog Box is not closed");
        return addHeroDialogBox.verifyAddHeroDialogBox();
    }

    /**
     * Method that checks when we are on the User Heroes Dialog Box,
     * the Column Name is displayed
     *
     * @return {boolean} - if Column Name is displayed or not
     */
    public boolean isColumnNameDisplayed(){
        log.debug("isColumnNameDisplayed()");
        return isWebElementDisplayed(columnName);
    }

    /**
     * Method that checks when we are on the User Heroes Dialog Box,
     * the Column Class is displayed
     *
     * @return {boolean} - if Column Class is displayed or not
     */
    public boolean isColumnClassDisplayed(){
        log.debug("isColumnClassDisplayed()");
        return isWebElementDisplayed(columnClass);
    }

    /**
     * Method that checks when we are on the User Heroes Dialog Box,
     * the Column Level is displayed
     *
     * @return {boolean} - if Column Level is displayed or not
     */
    public boolean isColumnLevelDisplayed(){
        log.debug("isColumnLevelDisplayed()");
        return isWebElementDisplayed(columnLevel);
    }

    /**
     * Method that return the String that is displayed as a Name Column
     *
     * @return {String} - Name Column
     */
    public String getColumnNameText(){
        log.debug("getNameColumnText()");
        Assert.assertTrue(isColumnNameDisplayed(),"Column Name  Text is NOT displayed on User Heroes Dialog Box");
        return getTextFromWebElement(columnName);
    }

    /**
     * Method that return the String that is displayed as a Class Column
     *
     * @return {String} - Class Column
     */
    public String getClassColumnText(){
        log.debug("getClassColumnText()");
        Assert.assertTrue(isColumnClassDisplayed(),"Column Class Text is NOT displayed on User Heroes Dialog Box");
        return getTextFromWebElement(columnClass);
    }

    /**
     * Method that return the String that is displayed as a Level Column
     *
     * @return {String} - Level Column
     */
    public String getLevelColumnText(){
        log.debug("getLevelColumnText()");
        Assert.assertTrue(isColumnLevelDisplayed(),"Column Level Text is NOT displayed on User Heroes Dialog Box");
        return getTextFromWebElement(columnLevel);
    }

    /**
     * Method that checks when we are on the User Heroes Dialog Box,
     * the Hero's Name is displayed
     *
     * @return {boolean} - if Hero's Name is displayed or not
     */
    public boolean isHeroesNameDisplayed(){
        log.debug("isHeroesNameDisplayed()");
        return isWebElementDisplayed(heroesName);
    }

    /**
     * Method that checks when we are on the User Heroes Dialog Box,
     * the Hero's Class is displayed
     *
     * @return {boolean} - if Hero's Class is displayed or not
     */
    public boolean isHeroesClassDisplayed(){
        log.debug("isHeroesClassDisplayed()");
        return isWebElementDisplayed(heroesClass);
    }

    /**
     * Method that checks when we are on the User Heroes Dialog Box,
     * the Hero's Level is displayed
     *
     * @return {boolean} - if Hero's Level is displayed or not
     */
    public boolean isHeroesLevelDisplayed(){
        log.debug("isHeroesLevelDisplayed()");
        return isWebElementDisplayed(heroesLevel);
    }

    /**
     * Method that return the String that is displayed as a Hero's Name
     *
     * @return {String} - Hero's Name
     */
    public String getHeroesNameText(){
        log.debug("getHeroesNameText()");
        Assert.assertTrue(isHeroesNameDisplayed(),"Hero's Name  Text is NOT displayed on User Heroes Dialog Box!");
        return getTextFromWebElement(heroesName);
    }

    /**
     * Method that return the String that is displayed as a Hero's Class
     *
     * @return {String} - Hero's Class
     */
    public String getHeroesClassText(){
        log.debug("getHeroesClassText()");
        Assert.assertTrue(isHeroesClassDisplayed(),"Hero's Class Text is NOT displayed on User Heroes Dialog Box!");
        return getTextFromWebElement(heroesClass);
    }

    /**
     * Method that return the String that is displayed as a Hero's Level
     *
     * @return {String} - Hero's Level
     */
    public String getHeroesLevelText(){
        log.debug("getHeroesLevelText()");
        Assert.assertTrue(isHeroesLevelDisplayed(),"Hero's Level Text is NOT displayed on User Heroes Dialog Box!");
        return getTextFromWebElement(heroesLevel);
    }

    /**
     * Method that gets the number od rows from the User Heroes table
     *
     * @return {int} - number of rows that User Heroes table contains
     */
    public int getNumberOfRowsInUserHeroesTable(){
        log.debug("getNumberOfRowsInUserHeroesTable()");
        String xPath = ".//td[@class='name']";
        List<WebElement> usersTableRows = getNestedWebElements(userHeroesTable,By.xpath(xPath));
        return usersTableRows.size();
    }

    /**
     * Method that checks if Hero is present in the User Heroes
     * @description - instead of using two for loops it is better to use xpath axes
     *
     * @param sHeroName {String} - Hero Name we want to check if is present
     *
     * @return {boolean} - if Hero is present in the User Heroes table or not
     */
    public boolean isHeroPresentInUserHeroesTable(String sHeroName) {
        log.debug(String.format("isHeroPresentInUserHeroesTable(%s)",sHeroName));
        String xPath = createXpathForHeroNameInUserHeroesTable(sHeroName);
        return isWebElementDisplayed(By.xpath(xPath));
    }


    /**
     * Method that checks if Hero is present in the User Heroes Table, create xpath for
     * that Hero and gets a Hero Class from the User Heroes Table
     * @description - instead of using two for loops it is better to use xpath axes
     *
     * @param sHeroName {String} - Hero Name for which we want to check class
     *
     * @return {String} - Hero Class from the User Heroes Table
     */
    public String getHeroClassFromUserHeroesTable(String sHeroName){
        log.debug(String.format("getHeroClassFromUserHeroesTable(%s)",sHeroName));
        Assert.assertTrue(isHeroPresentInUserHeroesTable(sHeroName),String.format("Hero %s was not present in the user table", sHeroName));
        String xPath = createXpathForHeroClassInUserHeroesTable(sHeroName);
        WebElement heroClass = getNestedWebElement(userHeroesTable, By.xpath(xPath));
        return getTextFromWebElement(heroClass);
    }

    /**
     * Method that checks if Hero is present in the User Heroes Table, create xpath for
     * that Hero and gets a Hero Level from the User Heroes Table
     * @description - instead of using two for loops it is better to use xpath axes
     *
     * @param sHeroName {String} - Hero Name for which we want to check level
     *
     * @return {String} - Hero Level from the User Heroes Table
     */
    public String getHeroLevelFromUserHeroesTable(String sHeroName){
        log.debug(String.format("getHeroLevelFromUserHeroesTable(%s)",sHeroName));
        Assert.assertTrue(isHeroPresentInUserHeroesTable(sHeroName),String.format("Hero %s was not present in the user table", sHeroName));
        String xPath = createXpathForHeroLevelInUserHeroesTable(sHeroName);
        WebElement heroLevel = getNestedWebElement(userHeroesTable, By.xpath(xPath));
        return getTextFromWebElement(heroLevel);
    }
}
