package tests.login;

import data.CommonStrings;
import data.TestNGGroups;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.BaseTestClass;
import utils.PropertiesUtils;

@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.SANITY,TestNGGroups.LOGIN, TestNGGroups.BROKEN})
public class VerifyNavigationForAdminUser extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private String sAdminUserName;
    private String sAdminPassword;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        //It is better to login here because is better test to fail in setup
        //and our test will be skipped that means that problem is with setup and not with our test
        sAdminUserName = PropertiesUtils.getAdminUserName();
        sAdminPassword = PropertiesUtils.getAdminPassword();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        tearDown(driver,testResult);
    }
    /**
     * Test that login Admin user and verify if:
     * 1. every tab title is displayed
     * (Samsara, Home, Users, Heroes, Gallery, API, Practice, Broken Link, Admin, Profile, Log Out)
     * 2. check if tab title is correct
     * 3. clicks on the tab and verify if appropriate page is opened
     * 4. check if page title is displayed
     * 5. checks if page title is correct
     */
    @Test
    public void testVerifyNavigationForAdminUser()  {

        log.debug(String.format("[START TEST] %s", sTestName));
        LoginPage loginPage = new LoginPage(driver).open();

        WelcomePage welcomePage = loginPage.login(sAdminUserName,sAdminPassword);
        //HomePage homePage = new HomePage(driver);

        SoftAssert softAssert = new SoftAssert();
        //Samsara
        softAssert.assertTrue (welcomePage.isSamsaraLogoDisplayed(), "Samsara logo is NOT displayed on navigation bar");
        softAssert.assertNotNull (welcomePage.clickSamsaraLogo(), "Welcome page is NOT showing when we click on the Samsara logo");
        softAssert.assertNotNull (welcomePage.verifyWelcomePage(), "Welcome page can't be verified when we click on Samsara logo!");
        softAssert.assertTrue (welcomePage.isPageTitleDisplayed(), "Samsara logo is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getPageTitle(), CommonStrings.getLoginPageTitle(),"Login page title is NOT the same!");

        //Home
        softAssert.assertTrue (welcomePage.isHomeTabDisplayed(),"Home tab is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getHomeTabTitle(), CommonStrings.getHomeTabTitle(), "Home tab title isn't the same as expected!");
        HomePage homePage = welcomePage.clickHomeTab();
        softAssert.assertNotNull (homePage,"Home page is NOT showing when we click on the Home tab!");
        softAssert.assertNotNull (homePage.verifyHomePage(),"Home page can't be verified!");
        softAssert.assertTrue (homePage.isPageTitleDisplayed(),"Home page title is NOT displayed!");
        softAssert.assertEquals (homePage.getPageTitle(), CommonStrings.getHomePageTitle(),"Home page title is NOT the same as expected!");

        //Users
        softAssert.assertTrue (welcomePage.isUsersTabDisplayed(),"Users tab is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getUsersTabTitle(), CommonStrings.getUsersTabTitle(), "Users tab title isn't the same as expected!");
        UsersPage usersPage = welcomePage.clickUsersTab();
        softAssert.assertNotNull (usersPage,"Users page is NOT showing when we click on the Users tab!");
        softAssert.assertNotNull (usersPage.verifyUsersPage(),"Users page can't be verified!");
        softAssert.assertTrue (welcomePage.isPageTitleDisplayed(),"Users page title is NOT displayed!");
        softAssert.assertEquals (welcomePage.getPageTitle(), CommonStrings.getUsersPageTitle(),"Users page title is NOT the same as expected!");

        //Heroes
        softAssert.assertTrue (welcomePage.isHeroesTabDisplayed(),"Heroes tab is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getHeroesTabTitle(), CommonStrings.getHeroesTabTitle(), "Heroes tab title isn't the same as expected!");
        HeroesPage heroesPage = welcomePage.clickHeroesTab();
        softAssert.assertNotNull (heroesPage,"Heroes page is NOT showing when we click on the Heroes tab!");
        softAssert.assertNotNull (heroesPage.verifyHeroesPage(),"Heroes page can't be verified!");
        softAssert.assertTrue (welcomePage.isPageTitleDisplayed(),"Heroes page title is NOT displayed!");
        softAssert.assertEquals (welcomePage.getPageTitle(), CommonStrings.getHeroesPageTitle(),"Heroes page title is NOT the same as expected!");

        //Gallery
        softAssert.assertTrue (welcomePage.isGalleryTabDisplayed(),"Gallery tab is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getGalleryTabTitle(), CommonStrings.getGalleryTabTitle(), "Gallery tab title isn't the same as expected!");
        GalleryPage galleryPage = welcomePage.clickGalleryTab();
        softAssert.assertNotNull (galleryPage,"Gallery page is NOT showing when we click on the Gallery tab!");
        softAssert.assertNotNull (galleryPage.verifyGalleryPage(),"Gallery page can't be verified!");
        softAssert.assertTrue (welcomePage.isPageTitleDisplayed(),"Gallery page title is NOT displayed!");
        softAssert.assertEquals (welcomePage.getPageTitle(), CommonStrings.getGalleryPageTitle(),"Gallery page title is NOT the same as expected!");

        //API
        softAssert.assertTrue (welcomePage.isAPITabDisplayed(),"API tab is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getAPITabTitle(), CommonStrings.getAPITabTitle(), "API tab title isn't the same as expected!");
        APIPage apiPage = welcomePage.clickAPITab();
        softAssert.assertNotNull (apiPage,"API page is NOT showing when we click on the API tab!");
        softAssert.assertNotNull (apiPage.verifyAPIPage(),"API page can't be verified!");
        softAssert.assertTrue (welcomePage.isPageTitleDisplayed(),"API page title is NOT displayed!");
        softAssert.assertEquals (welcomePage.getPageTitle(), CommonStrings.getAPIPageTitle(),"API page title is NOT the same as expected!");

        //Practice
        softAssert.assertTrue (welcomePage.isPracticeTabDisplayed(),"Practice tab is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getPracticeTabTitle(), CommonStrings.getPracticeTabTitle(), "Practice tab title isn't the same as expected!");
        PracticePage practicePage = welcomePage.clickPracticeTab();
        softAssert.assertNotNull (practicePage,"Practice page is NOT showing when we click on the Practice tab!");
        softAssert.assertNotNull (practicePage.verifyPracticePage(),"Practice page can't be verified!");
        softAssert.assertTrue (welcomePage.isPageTitleDisplayed(),"Practice page title is NOT displayed!");
        softAssert.assertEquals (welcomePage.getPageTitle(), CommonStrings.getPracticePageTitle(),"Practice page title is NOT the same as expected!");

        //Broken Link
        softAssert.assertTrue (welcomePage.isBrokenLinkTabDisplayed(),"Broken Link tab is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getBrokenLinkTabTitle(), CommonStrings.getBrokenLinkTabTitle(), "Broken Link tab title isn't the same as expected!");
        BrokenLinkPage brokenLinkPage = welcomePage.clickBrokenLinkTab();
        softAssert.assertNotNull (brokenLinkPage,"Broken Link page is NOT showing when we click on the Broken Link tab!");
        softAssert.assertNotNull (brokenLinkPage.verifyBrokenLinkPage(),"Broken Link page can't be verified!");
        softAssert.assertTrue (welcomePage.isPageTitleDisplayed(),"Broken Link page title is NOT displayed!");
        softAssert.assertEquals (welcomePage.getPageTitle(), CommonStrings.getBrokenLinkPageTitle(),"Broken Link page title is NOT the same as expected!");

        driver.navigate().back();

        //Admin
        softAssert.assertTrue (welcomePage.isAdminTabDisplayed(),"Admin tab is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getAdminTabTitle(), CommonStrings.getAdminTabTitle(), "Admin tab title isn't the same as expected!");
        AdminPage adminPage = welcomePage.clickAdminTab();
        softAssert.assertNotNull (adminPage,"Admin page is NOT showing when we click on the Admin tab!");
        softAssert.assertNotNull (adminPage.verifyAdminPage(),"Admin page can't be verified!");
        softAssert.assertTrue (welcomePage.isPageTitleDisplayed(),"Admin page title is NOT displayed!");
        softAssert.assertEquals (welcomePage.getPageTitle(), CommonStrings.getAdminPageTitle(),"Admin page title is NOT the same as expected!");

        //Profile
        softAssert.assertTrue (welcomePage.isProfileLinkDisplayed(),"Profile link is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getProfileLinkTitle(), CommonStrings.getProfileLinkTitle(), "Profile link title isn't the same as expected!");
        ProfilePage profilePage = welcomePage.clickProfileLink();
        softAssert.assertNotNull (profilePage,"Profile page is NOT showing when we click on the Profile link!");
        softAssert.assertNotNull (profilePage.verifyProfilePage(),"Profile page can't be verified!");
        softAssert.assertTrue (welcomePage.isPageTitleDisplayed(),"Profile page title is NOT displayed!");
        softAssert.assertEquals (welcomePage.getPageTitle(), CommonStrings.getProfilePageTitle(),"Profile page title is NOT the same as expected!");

        //Logout
        softAssert.assertTrue (welcomePage.isLogoutLinkDisplayed(),"Logout link is NOT displayed on navigation bar!");
        softAssert.assertEquals (welcomePage.getLogoutLinkTitle(), CommonStrings.getLogoutLinkTitle(), "Logout link title isn't the same as expected!");
        welcomePage.clickLogoutLink();
        softAssert.assertNotNull (loginPage,"Login Page page is NOT showing when we click on the Logout link!");
        softAssert.assertNotNull (loginPage.verifyLoginPage(),"Login page can't be verified!");

        softAssert.assertAll
                ("At least one Web Element is NOT displayed on Login Page! Locator(s) changed?");
    }
}
