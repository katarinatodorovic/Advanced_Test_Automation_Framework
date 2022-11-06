package pages;

import data.PageUrlPaths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public abstract class CommonLoggedInPage extends CommonPage{

    protected static final String headerLocatorString = "//header[@id='headContainer']";

    // Common Page locators
    protected final By samsaraLogoLocator = By.xpath(headerLocatorString + "//a[@class='navbar-brand']");
    private final By homeTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.HOME_PAGE  + "']");
    private final By usersTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.USERS_PAGE  + "']");
    private final By heroesTabLocator = By.xpath(headerLocatorString + "//a[@href='"+ PageUrlPaths.HEROES_PAGE  +"']");
    private final By galleryTabLocator = By.xpath(headerLocatorString + "//a[@href='"+ PageUrlPaths.GALLERY_PAGE  +"']");
    private final By apiTabLocator = By.xpath(headerLocatorString + "//a[@href='"+ PageUrlPaths.API_PAGE  +"']");
    private final By practiceTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.PRACTICE_PAGE  + "']");
    private final By brokenLinkTabLocator = By.xpath(headerLocatorString + "//a[@href='"+ PageUrlPaths.BROKEN_LINK  +"']");
    private final By adminTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.ADMIN_PAGE  + "']");
    private final By profileLinkLocator = By.xpath("//a[@href='"+ PageUrlPaths.PROFILE_PAGE  +"']");
    protected final By logoutLinkLocator = By.xpath(headerLocatorString + "//a[contains(@href, 'logoutForm.submit')]");

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public CommonLoggedInPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method that checks if the Samsara logo is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Samsara logo is displayed or not
     */
    public boolean isSamsaraLogoDisplayed(){
        log.debug("isSamsaraLogoDisplayed()");
        return isWebElementDisplayed(samsaraLogoLocator);
    }

    /**
     * Method that checks if the Samsara logo is displayed on navigation bar
     * or not, clicks on the Samsara logo, verify that after click we are on
     * the Welcome page and returns an instance of the Welcome page
     *
     * @return {WelcomePage} - an instance of the WelcomePage
     */
    public WelcomePage clickSamsaraLogo(){
        log.debug("clickSamsaraLogo()");
        Assert.assertTrue(isSamsaraLogoDisplayed(), "Samsara logo is NOT displayed on Navigation Bar!");
        WebElement samsaraLogo = getWebElement(samsaraLogoLocator);
        clickOnWebElement(samsaraLogo);
        WelcomePage welcomePage = new WelcomePage(driver);
        return welcomePage.verifyWelcomePage();
    }

    /**
     * Method that checks if the Home tab is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Home tab is displayed or not
     */
    public boolean isHomeTabDisplayed(){
        log.debug("isHomeTabDisplayed()");
        return isWebElementDisplayed(homeTabLocator);
    }

    /**
     * Method that checks if the Home tab is displayed on navigation bar
     * or not, clicks on the Home tab, verify that after click we are on
     * the Home page and returns an instance of the Home page
     *
     * @return {HomePage} - an instance of the HomePage
     */
    public HomePage clickHomeTab(){
        log.debug("clickHomeTab()");
        Assert.assertTrue(isHomeTabDisplayed(), "Home tab is NOT displayed on Navigation Bar!");
        WebElement homeTab = getWebElement(homeTabLocator);
        clickOnWebElement(homeTab);
        HomePage homePage = new HomePage(driver);
        return homePage.verifyHomePage();
    }

    /**
     * Method that checks if the Home tab is displayed on
     * navigation bar or not and returns the Home tab title
     *
     * @return {String} - Home tab title
     */
    public String getHomeTabTitle(){
        log.debug("getHomeTabTitle()");
        Assert.assertTrue(isHomeTabDisplayed(),"Home tab is NOT displayed on Navigation bar");
        WebElement homeTabTitle = getWebElement(homeTabLocator);
        return getTextFromWebElement(homeTabTitle);
    }

    /**
     * Method that checks if the Users tab is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Users tab is displayed or not
     */
    public boolean isUsersTabDisplayed(){
        log.debug("isUsersTabDisplayed()");
        return isWebElementDisplayed(usersTabLocator);
    }

    /**
     * Method that checks if the Users tab is displayed on navigation bar
     * or not, clicks on the Users tab, verify that after click we are on
     * the Users page and returns an instance of the Users page
     *
     * @return {UsersPage} - an instance of the UsersPage
     */
    public UsersPage clickUsersTab(){
        log.debug("clickUsersTab()");
        Assert.assertTrue(isUsersTabDisplayed(), "Users tab is NOT displayed on Navigation Bar!");
        WebElement usersTab = getWebElement(usersTabLocator);
        clickOnWebElement(usersTab);
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    /**
     * Method that checks if the Users tab is displayed on
     * navigation bar or not and returns the Users tab title
     *
     * @return {String} - Users tab title
     */
    public String getUsersTabTitle(){
        log.debug("getUsersTabTitle()");
        Assert.assertTrue(isUsersTabDisplayed(),"Users tab is NOT displayed on Navigation bar");
        WebElement usersTabTitle = getWebElement(usersTabLocator);
        return getTextFromWebElement(usersTabTitle);
    }

    /**
     * Method that checks if the Heroes tab is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Heroes tab is displayed or not
     */
    public boolean isHeroesTabDisplayed(){
        log.debug("isHeroesTabDisplayed()");
        return isWebElementDisplayed(heroesTabLocator);
    }

    /**
     * Method that checks if the Heroes tab is displayed on navigation bar
     * or not, clicks on the Heroes tab, verify that after click we are on
     * the Heroes page and returns an instance of the Heroes page
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage clickHeroesTab(){
        log.debug("clickHeroesTab()");
        Assert.assertTrue(isHeroesTabDisplayed(), "Heroes tab is NOT displayed on Navigation Bar!");
        WebElement heroesTab = getWebElement(heroesTabLocator);
        clickOnWebElement(heroesTab);
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    /**
     * Method that checks if the Heroes tab is displayed on
     * navigation bar or not and returns the Heroes tab title
     *
     * @return {String} - Heroes tab title
     */
    public String getHeroesTabTitle(){
        log.debug("getHeroesTabTitle()");
        Assert.assertTrue(isHeroesTabDisplayed(),"Heroes tab is NOT displayed on Navigation bar");
        WebElement heroesTabTitle = getWebElement(heroesTabLocator);
        return getTextFromWebElement(heroesTabTitle);
    }

    /**
     * Method that checks if the Gallery tab is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Gallery tab is displayed or not
     */
    public boolean isGalleryTabDisplayed(){
        log.debug("isGalleryTabDisplayed()");
        return isWebElementDisplayed(galleryTabLocator);
    }

    /**
     * Method that checks if the Gallery tab is displayed on navigation bar
     * or not, clicks on the Gallery tab, verify that after click we are on
     * the Gallery page and returns an instance of the Gallery page
     *
     * @return {GalleryPage} - an instance of the GalleryPage
     */
    public GalleryPage clickGalleryTab(){
        log.debug("clickGalleryTab()");
        Assert.assertTrue(isGalleryTabDisplayed(), "Gallery tab is NOT displayed on Navigation Bar!");
        WebElement galleryTab = getWebElement(galleryTabLocator);
        clickOnWebElement(galleryTab);
        GalleryPage galleryPage = new GalleryPage(driver);
        return galleryPage.verifyGalleryPage();
    }

    /**
     * Method that checks if the Gallery tab is displayed on
     * navigation bar or not and returns the Gallery tab title
     *
     * @return {String} - Gallery tab title
     */
    public String getGalleryTabTitle(){
        log.debug("getGalleryTabTitle()");
        Assert.assertTrue(isGalleryTabDisplayed(),"Gallery tab is NOT displayed on Navigation bar");
        WebElement galleryTabTitle = getWebElement(galleryTabLocator);
        return getTextFromWebElement(galleryTabTitle);
    }

    /**
     * Method that checks if the API tab is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if API tab is displayed or not
     */
    public boolean isAPITabDisplayed(){
        log.debug("isAPITabDisplayed()");
        return isWebElementDisplayed(apiTabLocator);
    }

    /**
     * Method that checks if the API tab is displayed on navigation bar
     * or not, clicks on the API tab, verify that after click we are on
     * the API page and returns an instance of the API page
     *
     * @return {APIPage} - an instance of the APIPage
     */
    public APIPage clickAPITab(){
        log.debug("clickAPITab()");
        Assert.assertTrue(isAPITabDisplayed(), "API tab is NOT displayed on Navigation Bar!");
        WebElement apiTab = getWebElement(apiTabLocator);
        clickOnWebElement(apiTab);
        APIPage apiPage = new APIPage(driver);
        return apiPage.verifyAPIPage();
    }

    /**
     * Method that checks if the API tab is displayed on
     * navigation bar or not and returns the API tab title
     *
     * @return {String} - API tab title
     */
    public String getAPITabTitle(){
        log.debug("getAPITabTitle()");
        Assert.assertTrue(isAPITabDisplayed(),"API tab is NOT displayed on Navigation bar");
        WebElement apiTabTitle = getWebElement(apiTabLocator);
        return getTextFromWebElement(apiTabTitle);
    }

    /**
     * Method that checks if the Practice tab is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Practice tab is displayed or not
     */
    public boolean isPracticeTabDisplayed(){
        log.debug("isPracticeTabDisplayed()");
        return isWebElementDisplayed(practiceTabLocator);
    }

    /**
     * Method that checks if the Practice tab is displayed on navigation bar
     * or not, clicks on the Practice tab, verify that after click we are on
     * the Practice page and returns an instance of the Practice page
     *
     * @return {PracticePage} - an instance of the PracticePage
     */
    public PracticePage clickPracticeTab(){
        log.debug("clickPracticeTab()");
        Assert.assertTrue(isPracticeTabDisplayed(), "Practice tab is NOT displayed on Navigation Bar!");
        WebElement practiceTab = getWebElement(practiceTabLocator);
        clickOnWebElement(practiceTab);
        PracticePage practicePage = new PracticePage(driver);
        return practicePage.verifyPracticePage();
    }

    /**
     * Method that checks if the Practice tab is displayed on
     * navigation bar or not and returns the Practice tab title
     *
     * @return {String} - Practice tab title
     */
    public String getPracticeTabTitle(){
        log.debug("getPracticeTabTitle()");
        Assert.assertTrue(isPracticeTabDisplayed(),"Practice tab is NOT displayed on Navigation bar");
        WebElement practiceTabTitle = getWebElement(practiceTabLocator);
        return getTextFromWebElement(practiceTabTitle);
    }

    /**
     * Method that checks if the Broken Link tab is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Broken Link tab is displayed or not
     */
    public boolean isBrokenLinkTabDisplayed(){
        log.debug("isBrokenLinkTabDisplayed()");
        return isWebElementDisplayed(brokenLinkTabLocator);
    }

    /**
     * Method that checks if the Broken Link tab is displayed on navigation bar
     * or not, clicks on the Broken Link tab, verify that after click we are on
     * the Broken Link page and returns an instance of the BrokenLink page
     *
     * @return {BrokenLinkPage} - an instance of the BrokenLinkPage
     */
    public BrokenLinkPage clickBrokenLinkTab(){
        log.debug("clickBrokenLinkTab()");
        Assert.assertTrue(isBrokenLinkTabDisplayed(), "Broken Link tab is NOT displayed on Navigation Bar!");
        WebElement brokenLinkTab = getWebElement(brokenLinkTabLocator);
        clickOnWebElement(brokenLinkTab);
        BrokenLinkPage brokenLinkPage = new BrokenLinkPage(driver);
        return brokenLinkPage.verifyBrokenLinkPage();
    }

    /**
     * Method that checks if the Broken Link tab is displayed on
     * navigation bar or not and returns the Broken Link tab title
     *
     * @return {String} - Broken Link tab title
     */
    public String getBrokenLinkTabTitle(){
        log.debug("getBrokenLinkTabTitle()");
        Assert.assertTrue(isBrokenLinkTabDisplayed(),"Broken Link tab is NOT displayed on Navigation bar");
        WebElement brokenLinkTabTitle = getWebElement(brokenLinkTabLocator);
        return getTextFromWebElement(brokenLinkTabTitle);
    }

    /**
     * Method that checks if the Admin tab is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Admin tab is displayed or not
     */
    public boolean isAdminTabDisplayed(){
        log.debug("isAdminTabDisplayed()");
        return isWebElementDisplayed(adminTabLocator);
    }

    /**
     * Method that checks if the Admin tab is displayed on navigation bar
     * or not, clicks on the Admin tab, verify that after click we are on
     * the Admin page and returns an instance of the Admin page
     *
     * @return {AdminPage} - an instance of the AdminPage
     */
    public AdminPage clickAdminTab(){
        log.debug("clickAdminTab()");
        Assert.assertTrue(isAdminTabDisplayed(), "Admin tab is NOT displayed on Navigation Bar!");
        WebElement adminTab = getWebElement(adminTabLocator);
        clickOnWebElement(adminTab);
        AdminPage adminPage = new AdminPage(driver);
        return adminPage.verifyAdminPage();
    }

    /**
     * Method that checks if the Admin tab is displayed on
     * navigation bar or not and returns the Admin tab title
     *
     * @return {String} - Admin tab title
     */
    public String getAdminTabTitle(){
        log.debug("getAdminTabTitle()");
        Assert.assertTrue(isAdminTabDisplayed(),"Admin tab is NOT displayed on Navigation bar");
        WebElement adminTabTitle = getWebElement(adminTabLocator);
        return getTextFromWebElement(adminTabTitle);
    }

    /**
     * Method that checks if the Profile link is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Profile link is displayed or not
     */
    public boolean isProfileLinkDisplayed(){
        log.debug("isProfileLinkDisplayed()");
        return isWebElementDisplayed(profileLinkLocator);
    }

    /**
     * Method that checks if the Profile link is displayed on navigation bar
     * or not, clicks on the Profile link, verify that after click we are on
     * the Profile page and returns an instance of the Profile page
     *
     * @return {ProfilePage} - an instance of the ProfilePage
     */
    public ProfilePage clickProfileLink(){
        log.debug("clickProfileLink()");
        Assert.assertTrue(isProfileLinkDisplayed(), "Profile link is NOT displayed on Navigation Bar!");
        WebElement profileLink = getWebElement(profileLinkLocator);
        clickOnWebElement(profileLink);
        ProfilePage profilePage = new ProfilePage(driver);
        return profilePage.verifyProfilePage();
    }

    /**
     * Method that checks if the Profile link is displayed on
     * navigation bar or not and returns the Profile link title
     *
     * @return {String} - Profile link tab title
     */
    public String getProfileLinkTitle(){
        log.debug("getProfileLinkTitle()");
        Assert.assertTrue(isProfileLinkDisplayed(),"Profile link is NOT displayed on Navigation bar");
        WebElement profileLinkTitle = getWebElement(profileLinkLocator);
        return getTextFromWebElement(profileLinkTitle);
    }

    /**
     * Method that checks if the Log-Out link is displayed on
     * navigation bar or not
     *
     * @return {boolean} - if Log Out link is displayed or not
     */
    public boolean isLogoutLinkDisplayed(){
        log.debug("isLogoutLinkDisplayed()");
        return isWebElementDisplayed(logoutLinkLocator);
    }

    /**
     * Method that checks if the Log-Out link is displayed on navigation bar
     * or not, clicks on the Log-Out link, verify that after click we are on
     * the Login page and returns an instance of the Login page
     *
     * @return {LoginPage} - an instance of the LoginPage
     */
    public LoginPage clickLogoutLink(){
        log.debug("clickLogoutLink()");
        Assert.assertTrue(isLogoutLinkDisplayed(), "Log Out link is NOT displayed on Navigation Bar!");
        WebElement logoutLink = getWebElement(logoutLinkLocator);
        clickOnWebElement(logoutLink);
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.verifyLoginPage();
    }

    /**
     * Method that checks if the Log-Out link is displayed on
     * navigation bar or not and returns the Log-Out link title
     *
     * @return {String} - Log Out link tab title
     */
    public String getLogoutLinkTitle(){
        log.debug("getLogoutLinkTitle()");
        Assert.assertTrue(isLogoutLinkDisplayed(),"Log Out link is NOT displayed on Navigation bar");
        WebElement logoutLinkTitle = getWebElement(logoutLinkLocator);
        return getTextFromWebElement(logoutLinkTitle);
    }
}
