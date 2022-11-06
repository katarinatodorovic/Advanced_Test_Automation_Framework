package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class UsersPage extends CommonLoggedInPage{

    //Page Url Path
    private final String USERS_PAGE_URL = getPageUrl(PageUrlPaths.USERS_PAGE);

    //Locators
    private final By addNewUserButtonLocator = By.xpath("//a[contains(@class,'btn-info') and contains(@onclick,'openAddUserModal')]");
    private final By usersTableLocator = By.id("users-table");

    @FindBy(id="search")
    private WebElement searchTextBox;
    @FindBy(xpath="//form[@id='searchForm']//i[contains(@class, 'glyphicon-search')]")
    private WebElement searchButton;

    /**
     * Method that creates the xpath for Username in the Users Table
     *
     * @return {String} - xpath for Username in the Users Table
     */
    private String createXpathForUsernameInUsersTable(String sUsername){
        return String.format(".//tbody//td[1]/self::td[text()='%s']",sUsername);
    }

    /**
     * Method that creates the xpath for Display Name in the Users Table
     *
     * @return {String} - xpath for Display Name in the Users Table
     */
    private String createXpathForDisplayNameInUsersTable(String sUsername){
        return createXpathForUsernameInUsersTable(sUsername) + "/following-sibling::td[1]";
    }

    /**
     * Method that creates the xpath for Hero Count in the Users Table
     *
     * @return {String} - xpath for Hero Count in the Users Table
     */
    private String createXpathForHeroCountInUsersTable(String sUsername){
        return createXpathForUsernameInUsersTable(sUsername) + "/following-sibling::td[2]";
    }

    /**
     * Method that creates the xpath for Action Icons in the Users Table
     * @description - locator for container with details, edit and delete user icon
     *
     * @return {String} - xpath for Action Icons in the Users Table
     */
    private String createXpathForActionIconsInUsersTable(String sUsername){
        return createXpathForUsernameInUsersTable(sUsername) + "/following-sibling::td[3]";
    }

    /**
     * Method that creates the xpath for User Details Icon in the Users Table
     *
     * @return {String} - xpath for User Details Icon in the Users Table
     */
    private String createXpathForUserDetailsIconInUsersTable(String sUsername){
        return createXpathForActionIconsInUsersTable(sUsername) + "/a[contains(@class, 'btn-info')]";
    }

    /**
     * Method that creates the xpath for Edit User Icon in the Users Table
     *
     * @return {String} - xpath for Edit User Icon in the Users Table
     */
    private String createXpathForEditUserIconInUsersTable(String sUsername){
        return createXpathForActionIconsInUsersTable(sUsername) + "/a[contains(@class, 'btn-success')]";
    }

    /**
     * Method that creates the xpath for Delete User Icon in the Users Table
     *
     * @return {String} - xpath for Delete User Icon in the Users Table
     */
    private String createXpathForDeleteUserIconInUsersTable(String sUsername){
        return createXpathForActionIconsInUsersTable(sUsername) + "/a[contains(@class, 'btn-danger')]";
    }

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public UsersPage(WebDriver driver) {

        super(driver);
        log.trace("new UsersPage()");
    }

    /**
     * Method that returns an instance of the UsersPage if bVerify is true
     * then it verifies if we are on the UsersPage, and wait for loading of
     * the UsersPage to be completed, if it is false then it returns an
     * instance of the LoginPage. This method can be used for negative
     * scenario also - e.g. can we open the UsersPage
     * even if we are logged out or only when we are logged in
     *
     * @param bVerify {boolean} - if you want to verify that we are on the UsersPage or not
     *
     * @return {UsersPage} - an instance of the UsersPage
     */
    public UsersPage open(boolean bVerify){
        openUrl(USERS_PAGE_URL);
        log.debug(String.format("Open UsersPage(): %s",USERS_PAGE_URL));
        if(bVerify){
            verifyUsersPage();
        }
        return this;
    }

    /**
     * Method that opens the UsersPage and can be done with or without
     * verification of the UsersPage
     *
     * @return {UsersPage} -  an instance of the UsersPage
     */
    public UsersPage open(){
        return open(true);
    }

    /**
     * Method that verifies if we are on the UsersPage,
     * wait for loading of the UsersPage to be completed and
     * return an instance of the UsersPage
     *
     * @return {UsersPage} - an instance of the UsersPage
     */
    public UsersPage verifyUsersPage(){
        log.debug("verifyUsersPage");
        waitForUrlChange(USERS_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    /**
     * Method that checks if the Add New User button is displayed
     *
     * @return {WebElement} - the Add New User button
     */
    public boolean isAddNewUserButtonDisplayed(){
        log.debug("isAddNewUserButtonDisplayed");
        return isWebElementDisplayed(addNewUserButtonLocator);
    }

    /**
     * Method that checks if the Add New User button is displayed,
     * gets the Add New User button title and return text as String
     *
     * @return {String} - text that is being displayed on Add New User button
     */
    public String getAddNewUserButtonTitle(){
        log.debug("getAddNewUserButtonTitle()");
        Assert.assertTrue(isAddNewUserButtonDisplayed(),
                "Add New User Button is NOT displayed on the Users Page!");
        WebElement addNewUserButton = getWebElement(addNewUserButtonLocator);
        return getValueFromWebElement(addNewUserButton);
    }

    /**
     * Method that checks if the Add New User button is displayed,
     * and if it is displayed then clicks on the Add New User button
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddUserDialogBox clickAddNewUserButton() {
        log.debug("clickAddNewUserButton()");
        Assert.assertTrue(isAddNewUserButtonDisplayed(),
                "Add New User Button is NOT displayed on the Users Page!");
        WebElement addNewUserButton = getWebElement(addNewUserButtonLocator);
        clickOnWebElement(addNewUserButton);
        return new AddUserDialogBox(driver);
    }

    /**
     * Method that checks if the Search Text Box is displayed
     *
     * @return {WebElement} - the Search Text Box
     */
    public boolean isSearchTextBoxDisplayed(){
        log.debug("isSearchTextBoxDisplayed()");
        return isWebElementDisplayed(searchTextBox);
    }

    /**
     * Method that checks of search box is displayed, type text in
     * search box and return an instance of the UsersPage
     *
     * @param sTextToSearch {String} - text to search for
     *
     * @return {UsersPage} - an instance of the UsersPage
     */
    public UsersPage typeSearchText(String sTextToSearch){
        log.debug("typeSearchText() " + sTextToSearch);
        Assert.assertTrue(isSearchTextBoxDisplayed(),
                "Search Text Box is NOT displayed on the Users Page!");
        clearAndTypeTextToWebElement(searchTextBox, sTextToSearch);
        return this;
    }

    /**
     * Method that type test that we want to search in search box on usersPage
     * click on search button and return an instance od UsersPage
     * search box and return an instance of the UsersPage
     *
     * @param sTextToSearch {String} - text to search for
     *
     * @return {UsersPage} - an instance of the UsersPage
     */
    public UsersPage search(String sTextToSearch){
        log.debug("typeSearchText() " + sTextToSearch);
       typeSearchText(sTextToSearch);
        return clickSearchButton();
    }

    /**
     * Method that checks if the Search Text box is displayed and returns
     * the text entered in the Search Text box
     *
     * @return {String} - text that is searched for
     */
    public String getSearchText(){
        log.debug("getSearchText()");
        Assert.assertTrue(isSearchTextBoxDisplayed(),
                "Search Text Box is NOT displayed on the Users Page!");
        return getValueFromWebElement(searchTextBox);
    }

    /**
     * Method that checks if the Search Button is displayed
     *
     * @return {WebElement} - the Search Button
     */
    public boolean isSearchButtonDisplayed(){
        log.debug("isSearchButtonDisplayed()");
        return isWebElementDisplayed(searchButton);
    }

    /**
     * Method that checks if the Search Button is displayed on the Users Page,
     * clicks on the Search Button, and returns an instance of the Users page
     * @description - here we have the risk of StaleElementReferenceException
     * because page is refreshed after search and DOM structure is changed
     * and the problem is handled with -> return new UsersPage(driver), this way
     * we return the new instance of UsersPage and that page is returned after is verified
     *
     * @return {UsersPage} - an instance of the UsersPage
     */
    public UsersPage clickSearchButton(){
        log.debug("clickSearchButton()");
        Assert.assertTrue(isSearchButtonDisplayed(),"Search button is NOT displayed on Users Page!");
        clickOnWebElement(searchButton);
        return new UsersPage(driver).verifyUsersPage();
    }


    /**
     * Method that gets the number od rows from the Users table
     *
     * @return {int} - number of rows that Users' table contains
     */
    public int getNumberOfRowsInUsersTable(){
        log.debug("getNumberOfRowsInUsersTable");
        WebElement userTable = getWebElement(usersTableLocator);
        // Here must be used dot inside xpath which means that it search for an element
        // from the current node in the DOM. If there is no dot it will search for an element from
        // the beginning of the DOM structure. If before that is some dialog box was opened and
        // then closed it will also display an information from previous actions because
        // elements are hidden, so it will display activity even though they are not present. In
        // the first case there will be 10 and in other there will be 15 after we opened and closed
        // other dialog box
        String xPath = ".//tbody/tr";
        List<WebElement> usersTableRows = getNestedWebElements(userTable,By.xpath(xPath));
        return usersTableRows.size();
    }

    /**
     * Method that checks if User is present in the Users Table
     * @description - instead of using two for loops it is better to use xpath axes
     *
     * @param sUsername {String} - Username of the user we want to check for presence
     *
     * @return {boolean} - if user is present in the Users Table or not
     */
    public boolean isUserPresentInUsersTable(String sUsername) {
        log.debug(String.format("isUserPresentInUsersTable(%s)",sUsername));
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForUsernameInUsersTable(sUsername);
        return isNestedWebElementDisplayed(usersTable,By.xpath(xPath));
    }

    /**
     * Method that check if user is present in the User Table, create xpath for
     * that user Display Name and gets a Display Name from the Users Table,
     * @description - instead of using two for loops it is better to use xpath axes
     *
     * @param sUsername {String} - Username of the user we want to check for presence
     *
     * @return {String} - Display Name from the Users Table
     */
    public String getDisplayNameInUsersTable(String sUsername){
        log.debug(String.format("getDisplayNameInUsersTable(%s)",sUsername));
        Assert.assertTrue(isUserPresentInUsersTable(sUsername),String.format("User %s was not present in the user table",sUsername));
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForDisplayNameInUsersTable(sUsername);
        WebElement displayName = getNestedWebElement(usersTable, By.xpath(xPath));
        return getTextFromWebElement(displayName);
    }

    /**
     * Method that check if user is present in the User Table, create xpath for
     * the Hero Count Link WebElement and return that WebElement
     *
     * @param sUsername {String} - Username of the user for which we need a hero count
     *
     * @return {WebElement} - Hero Count Link WebElement
     */
    private WebElement getHeroCountLinkWebElementInUsersTable(String sUsername){
        Assert.assertTrue(isUserPresentInUsersTable(sUsername),String.format("User %s was not present in the user table",sUsername));
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForHeroCountInUsersTable(sUsername);
        return getNestedWebElement(usersTable, By.xpath(xPath));
    }

    /**
     * Method that returns a heroes count for that user
     *
     * @param sUsername {String} - Username of the user for which we need a hero count
     *
     * @return {int} - a number of heroes for specified user
     */
    public int getHeroCountInUsersTable(String sUsername){
        log.debug(String.format("getHeroCountInUsersTable for user %s",sUsername));
        WebElement heroCountLink = getHeroCountLinkWebElementInUsersTable(sUsername);
        return Integer.parseInt(getTextFromWebElement(heroCountLink));
    }

    /**
     * Method that clicks on the Hero Count Link from the UsersTable and
     * returns an instance of the UserHeroesDialogBox
     *
     * @param sUsername {String} - Username of the user for which we need a hero count
     *
     * @return {UserHeroesDialogBox} - an instance of the UserHeroesDialogBox
     */
    public UserHeroesDialogBox clickHeroCountLinkInUsersTable(String sUsername){
        log.debug(String.format("clickHeroCountLinkInUsersTable for user %s",sUsername));
        WebElement heroCountLink = getHeroCountLinkWebElementInUsersTable(sUsername);
        clickOnWebElement(heroCountLink);
        return new UserHeroesDialogBox(driver).verifyUserHeroesDialogBox();
    }

    /**
     * Method that checks if User Details Icon is present in the Users Table
     *
     * @param sUsername {String} - Username of the user for which we want to check if
     *                             the User Details Icon is present
     *
     * @return {boolean} - if User Details Icon is present in the Users Table or not
     */
    public boolean isUserDetailsIconPresentInUsersTable(String sUsername) {
        log.debug(String.format("isUserDetailsIconPresentInUsersTable(%s)",sUsername));
        Assert.assertTrue(isUserPresentInUsersTable(sUsername),String.format("User %s was not present in the user table",sUsername));
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForUserDetailsIconInUsersTable(sUsername);
        return isNestedWebElementDisplayed(usersTable,By.xpath(xPath));
    }

    /**
     * Method that clicks on the User Details Icon from the UsersTable and
     * returns an instance of the UserHeroesDialogBox
     *
     * @param sUsername {String} - Username of the user for which we want to click on
     *                             the User Details Icon
     *
     * @return {UserHeroesDialogBox} - an instance of the UserHeroesDialogBox
     */
    public UserDetailsDialogBox clickUserDetailsIconInUsersTable(String sUsername){
        log.debug(String.format("clickUserDetailsIconInUsersTable for user %s",sUsername));
        Assert.assertTrue(isUserDetailsIconPresentInUsersTable(sUsername),String.format("User details icon for user %s was not present in the user table",sUsername));
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForUserDetailsIconInUsersTable(sUsername);
        WebElement userDetailsIcon = getNestedWebElement(usersTable,By.xpath(xPath));
        clickOnWebElement(userDetailsIcon);
        return new UserDetailsDialogBox(driver).verifyUserDetailsDialogBox();
    }

    /**
     * Method that checks if Edit User Icon is present in the Users Table
     *
     * @param sUsername {String} - Username of the user for which we want to check if
     *                             the Edit User Icon is present
     *
     * @return {boolean} - if Edit User Icon is present in the Users Table or not
     */
    public boolean isEditUserIconPresentInUsersTable(String sUsername) {
        log.debug(String.format("isEditUserIconPresentInUsersTable(%s)",sUsername));
        Assert.assertTrue(isUserPresentInUsersTable(sUsername),String.format("User %s was not present in the user table",sUsername));
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForEditUserIconInUsersTable(sUsername);
        return isNestedWebElementDisplayed(usersTable,By.xpath(xPath));
    }

    /**
     * Method that clicks on the Edit User Icon from the UsersTable and
     * returns an instance of the UserHeroesDialogBox
     *
     * @param sUsername {String} - Username of the user for which we want to click on
     *                             the Edit User Icon
     *
     * @return {EditUserDialogBox} - an instance of the EditUserDialogBox
     */
    public EditUserDialogBox clickEditUserIconInUsersTable(String sUsername){
        log.debug(String.format("clickEditUserIconInUsersTable for user %s",sUsername));
        Assert.assertTrue(isEditUserIconPresentInUsersTable(sUsername),String.format("Edit user icon for user %s was not present in the user table",sUsername));
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForEditUserIconInUsersTable(sUsername);
        WebElement EditUserIcon = getNestedWebElement(usersTable,By.xpath(xPath));
        clickOnWebElement(EditUserIcon);
        return new EditUserDialogBox(driver).verifyEditUserDialogBox();
    }

    /**
     * Method that checks if Delete User Icon is present in the Users Table
     *
     * @param sUsername {String} - Username of the user for which we want to check if
     *                             Delete User Icon is present
     *
     * @return {boolean} - if Delete User Icon is present in the Users Table or not
     */
    public boolean isDeleteUserIconPresentInUsersTable(String sUsername) {
        log.debug(String.format("isDeleteUserIconPresentInUsersTable(%s)",sUsername));
        Assert.assertTrue(isUserPresentInUsersTable(sUsername),String.format("User %s was not present in the user table",sUsername));
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForDeleteUserIconInUsersTable(sUsername);
        return isNestedWebElementDisplayed(usersTable,By.xpath(xPath));
    }

    /**
     * Method that clicks on the Delete User Icon from the UsersTable and
     * returns an instance of the UserHeroesDialogBox
     *
     * @param sUsername {String} - Username of the user for which we want to click on
     *                             the Delete User Icon
     *
     * @return {UserHeroesDialogBox} - an instance of the UserHeroesDialogBox
     */
    public DeleteUserDialogBox clickDeleteUserIconInUsersTable(String sUsername){
        log.debug(String.format("clickDeleteUserIconInUsersTable for user %s",sUsername));
        Assert.assertTrue(isDeleteUserIconPresentInUsersTable(sUsername),String.format("Delete user icon for user %s was not present in the user table",sUsername));
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForDeleteUserIconInUsersTable(sUsername);
        WebElement deleteUserIcon = getNestedWebElement(usersTable,By.xpath(xPath));
        clickOnWebElement(deleteUserIcon);
        return new DeleteUserDialogBox(driver).verifyDeleteUserDialogBox();
    }
}
