package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import java.util.List;

public class HeroesPage extends CommonLoggedInPage{

    // Locators
    private final String HEROES_PAGE_URL = getPageUrl(PageUrlPaths.HEROES_PAGE);
    private final By heroesTableLocator = By.id("heroes-table");

    @FindBy(id="heroes-table")
    private WebElement heroesTable;

    @FindBy(xpath = "//a[contains(@class,'btn-info') and contains(@onclick,'openAddHeroModal')]")
    private WebElement addNewHeroButton;

   @FindBy(xpath = "//table[@id='heroes-table']/tbody/tr")
    private List<WebElement>heroesTableRow;

    @FindBy(id="search")
    private WebElement searchTextBox;

    @FindBy(xpath="//form[@id='searchForm']//i[contains(@class, 'glyphicon-search')]")
    private WebElement searchButton;

    /**
     * Method that creates the xpath for Hero Name in the Heroes Table
     *
     * @param sHeroName {String} - Hero's name
     *
     * @return {String} - xpath for Hero Name in the Heroes Table
     */
    public String createXpathForHeroNameInHeroesTable(String sHeroName){
        return String.format(".//tbody//td[1]/self::td[text()='%s']",sHeroName);
    }

    /**
     * Method that creates the xpath for Hero Class in the Heroes Table
     *
     * @param sHeroName {String} - Hero's name
     *
     * @return {String} - xpath for Hero Class in the Heroes Table
     */
    private String createXpathForHeroClassInHeroesTable(String sHeroName){
        return createXpathForHeroNameInHeroesTable(sHeroName) + "/following-sibling::td[1]";
    }

    /**
     * Method that creates the xpath for Hero Level in the Heroes Table
     *
     * @param sHeroName {String} - Hero's name
     *
     * @return {String} - xpath for Hero Level in the Heroes Table
     */
    private String createXpathForHeroLevelInHeroesTable(String sHeroName){
        return createXpathForHeroNameInHeroesTable(sHeroName) + "/following-sibling::td[2]";
    }

    /**
     * Method that creates the xpath for Hero's User in the Heroes Table
     *
     * @param sHeroName {String} - Hero's name
     *
     * @return {String} - xpath for Hero's User  in the Heroes Table
     */
    private String createXpathForHeroesUserInHeroesTable(String sHeroName){
        return createXpathForHeroNameInHeroesTable(sHeroName) + "/following-sibling::td[3]";
    }

    /**
     * Method that creates the xpath for Action Icons in the Heroes Table
     * @description - locator for container with details, edit and delete Hero icon
     *
     * @param sHeroName {String} - Hero's name
     *
     * @return {String} - xpath for Action Icons in the Heroes Table
     */
    private String createXpathForActionIconsInHeroesTable(String sHeroName){
        return createXpathForHeroNameInHeroesTable(sHeroName) + "/following-sibling::td[4]";
    }

    /**
     * Method that creates the xpath for Heroes Details Icon in the Heroes Table
     *
     * @param sHeroName {String} - Hero's name
     *
     * @return {String} - xpath for Heroes Details Icon in the Heroes Table
     */
    private String createXpathForHeroDetailsIconInHeroesTable(String sHeroName){
        return createXpathForActionIconsInHeroesTable(sHeroName) + "/a[contains(@class, 'btn-info')]";
    }

    /**
     * Method that creates the xpath for Edit Heroes Icon in the Heroes Table
     *
     * @param sHeroName {String} - Hero's name
     *
     * @return {String} - xpath for Edit Heroes Icon in the Heroes Table
     */
    private String createXpathForEditHeroIconInHeroesTable(String sHeroName){
        return createXpathForActionIconsInHeroesTable(sHeroName) + "/a[contains(@class, 'btn-success')]";
    }

    /**
     * Method that creates the xpath for Delete Heroes Icon in the Heroes Table
     *
     * @param sHeroName {String} - Hero's name
     *
     * @return {String} - xpath for Delete Heroes Icon in the Heroes Table
     */
    private String createXpathForDeleteHeroIconInHeroesTable(String sHeroName){
        return createXpathForActionIconsInHeroesTable(sHeroName) + "/a[contains(@class, 'btn-danger')]";
    }


    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public HeroesPage(WebDriver driver) {
        super(driver);
        log.trace("new HeroesPage()");
    }

    /**
     * Method that returns an instance of the HeroesPage if bVerify is true
     * then it verifies if we are on the HeroesPage,and wait for loading of
     * the HeroesPage to be completed, if it is false then it returns an
     * instance of the HeroesPage. This method can be used for negative
     * scenario also - e.g. can we open the HeroesPage even if we are logged
     * out or only when we are logged in
     *
     * @param bVerify {boolean} - if you want to verify that we are on the HeroesPage or not
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage open(boolean bVerify){
        openUrl(HEROES_PAGE_URL);
        log.debug(String.format("Open HeroesPage(): %s",HEROES_PAGE_URL));
        if(bVerify){
            verifyHeroesPage();
        }
        return this;
    }

    /**
     * Method that opens the HeroesPage and can be done with or without
     * verification of the HeroesPage
     *
     * @return {HeroesPage} -  an instance of the HeroesPage
     */
    public HeroesPage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the HeroesPage,
     * wait for loading of the HeroesPage to be completed and
     * return an instance of the HeroesPage
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage verifyHeroesPage(){

        log.debug("verifyHeroesPage");
        waitForUrlChange(HEROES_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    /**
     * Method that checks if the Add New Hero button is displayed
     *
     * @return {WebElement} - the Add New Hero button
     */
    public boolean isAddNewHeroButtonDisplayed(){
        log.debug("isAddNewHeroButtonDisplayed");
        return isWebElementDisplayed(addNewHeroButton);
    }

    /**
     * Method that checks if the Add New Hero button is displayed,
     * gets the Add New Hero button title and return text as String
     *
     * @return {String} - text that is being displayed on Add New Hero button
     */
    public String getAddNewHeroButtonTitle(){
        log.debug("getAddNewHeroButtonTitle()");
        Assert.assertTrue(isAddNewHeroButtonDisplayed(),
                "Add New Hero Button is NOT displayed on the Heroes Page!");
        return getValueFromWebElement(addNewHeroButton);
    }

    /**
     * Method that checks if the Add New Hero button is displayed,
     * and if it is displayed then clicks on the Add New Hero button
     *
     * @return {AddHeroDialogBox} - an instance of the AddHeroDialogBox
     */
    public AddHeroDialogBox clickAddNewHeroButton() {
        log.debug("clickAddNewHeroButton()");
        Assert.assertTrue(isAddNewHeroButtonDisplayed(),
                "Add New Hero Button is NOT displayed on the Heroes Page!");
        clickOnWebElement(addNewHeroButton);
        return new AddHeroDialogBox(driver);
    }

    /**
     * Method that checks if the Search Text Box is displayed on Heroes page
     *
     * @return {boolean} - if it is displayed or not
     */
    public boolean isSearchTextBoxDisplayed(){
        log.debug("isSearchTextBoxDisplayed()");
        return isWebElementDisplayed(searchTextBox);
    }

    /**
     * Method that checks if search text box is displayed, type text
     * that we want to search for and returns an instance of the HeroesPage
     *
     * @param sTextToSearch {String} - text we want to search for
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage typeSearchText(String sTextToSearch){
        log.debug("typeSearchText()");
        Assert.assertTrue(isSearchTextBoxDisplayed(),
                "Search Text Box is NOT displayed on the Heroes Page!");
        clearAndTypeTextToWebElement(searchTextBox, sTextToSearch);
        return this;
    }

    /**
     * Method that checks if search text box is displayed and
     * gets text that we typed in the search box
     *
     * @return {String} - text that we typed in the search box
     */
    public String getSearchText(){
        log.debug("getSearchText()");
        Assert.assertTrue(isSearchTextBoxDisplayed(),
                "Search Text Box is NOT displayed on the Heroes Page!");
        return getValueFromWebElement(searchTextBox);
    }

    /**
     * Method that checks if the Search Button is displayed on Heroes page
     *
     * @return {WebElement} - if the Search Button is displayed or not
     */
    public boolean isSearchButtonDisplayed(){
        log.debug("isSearchButtonDisplayed()");
        return isWebElementDisplayed(searchButton);
    }

    /**
     * Method that checks if search text box is displayed and click on search button
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage clickSearchButton(){
        log.debug("clickSearchButton()");
        Assert.assertTrue(isSearchButtonDisplayed(),"Search button is NOT displayed on Heroes Page!");
        clickOnWebElement(searchButton);
        return this;
    }

    /**
     * Method that gets the number od rows from the Heroes table
     *
     * @return {int} - number of rows that Heroes' table contains
     */
    public int getNumberOfRowsInHeroesTable(){
        log.debug("getNumberOfRowsInHeroesTable");
        WebElement heroesTable = getWebElement(heroesTableLocator);
        String xPath = ".//tbody/tr";
        List<WebElement> usersTableRows = getNestedWebElements(heroesTable,By.xpath(xPath));
        return usersTableRows.size();
    }

    /**
     * Method that checks if Hero is present in the Heroes Table
     * @description - instead of using two for loops it is better to use xpath axes
     *
     * @param sHeroName {String} - Name of a Hero we want to check a presence for
     *
     * @return {boolean} - if Hero is present in the Heroes Table or not
     */
    public boolean isHeroPresentInHeroesTable(String sHeroName) {
        log.debug(String.format("isHeroPresentInHeroesTable(%s)",sHeroName));
        String xPath = createXpathForHeroNameInHeroesTable(sHeroName);
        return isNestedWebElementDisplayed(heroesTable,By.xpath(xPath));
    }

    /**
     * Method that check if Hero is present in the Heroes Table, create xpath for
     * that Hero's Class and gets a Class from the Heroes Table
     *
     * @param sHeroName {String} - Name of a Hero we want to check a class for
     *
     * @return {String} - Hero's Class from the Heroes Table
     */
    public String getHeroClassFromHeroesTable(String sHeroName){
        log.debug(String.format("getHeroClassFromHeroesTable(%s)",sHeroName));
        Assert.assertTrue(isHeroPresentInHeroesTable(sHeroName),String.format("Hero %s was not present in the Heroes table",sHeroName));
        String xPath = createXpathForHeroClassInHeroesTable(sHeroName);
        WebElement heroClass = getNestedWebElement(heroesTable, By.xpath(xPath));
        return getTextFromWebElement(heroClass);
    }

    /**
     * Method that check if Hero is present in the Heroes Table, create xpath for
     * that Hero's Level and gets a Level from the Heroes Table
     *
     * @param sHeroName {String} - Name of a Hero we want to check a Level for
     *
     * @return {String} - Hero's Level from the Heroes Table
     */
    public String getHeroLevelFromHeroesTable(String sHeroName){
        log.debug(String.format("getHeroLevelFromHeroesTable(%s)",sHeroName));
        Assert.assertTrue(isHeroPresentInHeroesTable(sHeroName),String.format("Hero %s was not present in the Heroes table",sHeroName));
        WebElement heroTable = getWebElement(heroesTableLocator);
        String xPath = createXpathForHeroLevelInHeroesTable(sHeroName);
        WebElement heroLevel = getNestedWebElement(heroTable, By.xpath(xPath));
        return getTextFromWebElement(heroLevel);
    }

    /**
     * Method that check if Hero is present in the Heroes Table, create xpath for
     * the Hero's User Link WebElement and return that WebElement
     *
     * @param sHeroName {String} - Name of a Hero we want to get a User's link for
     *
     * @return {WebElement} - Hero's User Link WebElement
     */
    private WebElement getHeroUserLinkWebElementInHeroTable(String sHeroName){
        Assert.assertTrue(isHeroPresentInHeroesTable(sHeroName),String.format("Hero %s was not present in the heroes table",sHeroName));
        String xPath = createXpathForHeroesUserInHeroesTable(sHeroName);
        return getNestedWebElement(heroesTable, By.xpath(xPath));
    }

    /**
     * Method that returns a Hero's User from Heroes table
     *
     * @param sHeroName {String} - Name of a Hero we want to check a User for
     *
     * @return {String} - name of a Hero's user
     */
    public String getHeroUserFromHeroesTable(String sHeroName){
        log.debug(String.format("getHeroUserFromHeroesTable(%s)",sHeroName));
        Assert.assertTrue(isHeroPresentInHeroesTable(sHeroName),String.format("Hero %s was not present in the Heroes table",sHeroName));
        WebElement heroTable = getWebElement(heroesTableLocator);
        String xPath = createXpathForHeroesUserInHeroesTable(sHeroName);
        WebElement heroUser =  getNestedWebElement(heroTable, By.xpath(xPath));
        return getTextFromWebElement(heroUser);
    }

    /**
     * Method that clicks on the Hero's User Link from the Heroes Table and
     * returns an instance of the UserDetailsDialogBox
     *
     * @param sHeroName {String} - Name of a Hero we want to click on a User's link
     *
     * @return {UserDetailsDialogBox} - an instance of the UserDetailsDialogBox
     */
    public UserDetailsDialogBox clickUserLinkInHeroesTable(String sHeroName){
        log.debug(String.format("clickUserLinkInHeroesTable for Hero %s",sHeroName));
        WebElement userLink = getHeroUserLinkWebElementInHeroTable(sHeroName);
        clickOnWebElement(userLink);
        return new UserDetailsDialogBox(driver).verifyUserDetailsDialogBox();
    }

    /**
     * Method that checks if Hero's Details Icon is present in the Heroes Table
     *
     * @param sHeroName {String} -  Name of a Hero we want to check if
     *                             the Hero's Details Icon is present
     *
     * @return {boolean} - if Hero's Details Icon is present in the Heroes Table or not
     */
    public boolean isHeroDetailsIconPresentInHeroesTable(String sHeroName) {
        log.debug(String.format("isHeroDetailsIconPresentInHeroesTable(%s)",sHeroName));
        Assert.assertTrue(isHeroPresentInHeroesTable(sHeroName),String.format("Hero %s was not present in the Heroes table",sHeroName));
        String xPath = createXpathForHeroDetailsIconInHeroesTable(sHeroName);
        return isNestedWebElementDisplayed(heroesTable,By.xpath(xPath));
    }

    /**
     * Method that clicks on the Hero's Details Icon from the HeroesTable and
     * returns an instance of the HeroDetailsDialogBox
     *
     * @param sHeroName {String} - Name of a Hero we want to check if
     *                             the Hero's Details Icon is present
     *
     * @return {UserHeroesDialogBox} - an instance of the UserHeroesDialogBox
     */
    public HeroDetailsDialogBox clickHeroDetailsIconInHeroesTable(String sHeroName){
        log.debug(String.format("clickHeroDetailsIconInHeroesTable for Hero %s",sHeroName));
        Assert.assertTrue(isHeroDetailsIconPresentInHeroesTable(sHeroName),String.format("Hero details icon for Hero %s was not present in the Heroes table",sHeroName));
        String xPath = createXpathForHeroDetailsIconInHeroesTable(sHeroName);
        WebElement heroDetailsIcon = getNestedWebElement(heroesTable,By.xpath(xPath));
        clickOnWebElement(heroDetailsIcon);
        return new HeroDetailsDialogBox(driver).verifyHeroDetailsDialogBox();
    }

    /**
     * Method that checks if Edit Hero's Icon is present in the Heroes Table
     *
     * @param sHeroName {String} - Name of a Hero we want to check if
     *                             the Hero's Edit Icon is present
     *
     * @return {boolean} - if Edit User's Icon is present in the Heroes Table or not
     */
    public boolean isEditHeroIconPresentInHeroesTable(String sHeroName) {
        log.debug(String.format("isEditHeroIconPresentInHeroesTable(%s)",sHeroName));
        Assert.assertTrue(isHeroPresentInHeroesTable(sHeroName),String.format("Hero %s was not present in the Heroes table",sHeroName));
        String xPath = createXpathForEditHeroIconInHeroesTable(sHeroName);
        return isNestedWebElementDisplayed(heroesTable,By.xpath(xPath));
    }

    /**
     * Method that clicks on the Edit Hero's Icon from the HeroesTable and
     * returns an instance of the UserHeroesDialogBox
     *
     * @param sHeroName {String} - Name of a Hero we want to check if
     *                             the Hero's Edit Icon is present
     *
     * @return {EditHeroDialogBox} - an instance of the EditHeroDialogBox
     */
    public EditHeroDialogBox clickEditHeroIconInHeroesTable(String sHeroName){
        log.debug(String.format("clickEditHeroIconInHeroesTable for user %s",sHeroName));
        Assert.assertTrue(isEditHeroIconPresentInHeroesTable(sHeroName),String.format("Edit Hero icon for user %s was not present in the Heroes table",sHeroName));
        String xPath = createXpathForEditHeroIconInHeroesTable(sHeroName);
        WebElement editHeroIcon = getNestedWebElement(heroesTable,By.xpath(xPath));
        clickOnWebElement(editHeroIcon);
        return new EditHeroDialogBox(driver).verifyEditHeroDialogBox();
    }

    /**
     * Method that checks if Delete Hero's Icon is present in the Heroes Table
     *
     * @param sHeroName {String} - Name of a Hero we want to check if
     *                             the Hero's Edit Icon is present
     *
     * @return {boolean} - if Delete Hero's Icon is present in the Heroes Table or not
     */
    public boolean isDeleteHeroIconPresentInHeroesTable(String sHeroName) {
        log.debug(String.format("isDeleteHeroIconPresentInHeroesTable(%s)",sHeroName));
        Assert.assertTrue(isHeroPresentInHeroesTable(sHeroName),String.format("Hero %s was not present in the Heroes table",sHeroName));
        String xPath = createXpathForDeleteHeroIconInHeroesTable(sHeroName);
        return isNestedWebElementDisplayed(heroesTable,By.xpath(xPath));
    }

    /**
     * Method that clicks on the Delete Hero's Icon from the HeroesTable and
     * returns an instance of the DeleteHeroDialogBox
     *
     * @param sHeroName {String} - Name of a Hero we want to check if
     *                             the Hero's Delete Icon is present
     *
     * @return {DeleteHeroDialogBox} - an instance of the DeleteHeroDialogBox
     */
    public DeleteHeroDialogBox clickDeleteHeroIconInHeroesTable(String sHeroName){
        log.debug(String.format("clickDeleteHeroIconInHeroesTable for user %s",sHeroName));
        Assert.assertTrue(isDeleteHeroIconPresentInHeroesTable(sHeroName),String.format("Delete hero icon for user %s was not present in the Heroes table",sHeroName));
        String xPath = createXpathForDeleteHeroIconInHeroesTable(sHeroName);
        WebElement deleteHeroIcon = getNestedWebElement(heroesTable,By.xpath(xPath));
        clickOnWebElement(deleteHeroIcon);
        return new DeleteHeroDialogBox(driver).verifyDeleteHeroDialogBox();
    }
    /**
     * Method that type text in search box on Heroes page and clicks on search button
     *
     * @param sSearchText {String} - text we want to search for
     *
     * @return {HeroesPage} - an instance of HeroesPage
     */
    public HeroesPage searchHero(String sSearchText){
        typeSearchText(sSearchText);
        return clickSearchButton();
    }
}
