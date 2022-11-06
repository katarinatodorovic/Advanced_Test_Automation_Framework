package tests.heroes;

import objects.Hero;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;

import java.util.Date;

public class NewHeroUIVerification extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private String sAdminUserName;
    private String sAdminPassword;
    private User savedUser;
    private Hero hero;
    private boolean bCreated = false;

    private int numOfHeroesBefore;
    private String heroClass;
    private Integer heroLevel;
    Date createdAt;


    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        sAdminUserName = PropertiesUtils.getAdminUserName();
        sAdminPassword = PropertiesUtils.getAdminPassword();
        savedUser = RestApiUtils.getUser(sAdminUserName);
        numOfHeroesBefore = savedUser.getHeroCount();
        heroClass = Hero.createRandomHeroClass();
        heroLevel = Hero.createRandomHeroLevel();
        hero.setCreatedAt(DateTimeUtils.getCurrentDateTime());
        createdAt = hero.getCreatedAt();
        hero = new Hero("HeroUIVerify",heroClass,heroLevel);
        RestApiUtils.postHero(hero);
        bCreated = true;
        log.info(hero);
        hero.setCreatedAt(RestApiUtils.getHero(hero.getHeroName()).getCreatedAt());
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        tearDown(driver,testResult);
        if(bCreated){
            cleanUp();
        }
    }
    /**
     * Test that checks if User Heroes Details in User Heroes Dialog box for admin user are correct
         * Login as admin
         * Go to the Users tab
         * Check if Heroes details are correct in users table
         * Check if user is present in the users table
         * Check if hero's class, level and type in the DB and on the UI are correct
     */
    @Test
    public void testNewHeroUIVerification()  {

        log.debug(String.format("[START TEST] %s", sTestName));

        LoginPage loginPage = new LoginPage(driver).open();
        WelcomePage welcomePage = loginPage.login(sAdminUserName,sAdminPassword);
        UsersPage usersPage = welcomePage.clickUsersTab();
        Assert.assertEquals(hero.getHeroName(),"HeroUIVerify","Hero Doesn't exist!");
        User newUser = RestApiUtils.getUser(savedUser.getUsername());
        Assert.assertEquals(newUser.getHeroCount(),numOfHeroesBefore + 1, "Hero count isn't right!" );
        UserHeroesDialogBox userHeroesDialogBox = usersPage.clickHeroCountLinkInUsersTable(sAdminUserName);
        Assert.assertTrue(userHeroesDialogBox.isHeroPresentInUserHeroesTable("HeroUIVerify"));
        userHeroesDialogBox.clickOnCloseButton();


        HeroesPage heroesPage = welcomePage.clickHeroesTab();
        Assert.assertTrue(heroesPage.isHeroPresentInHeroesTable(hero.getHeroName()));
        Hero savedHero = RestApiUtils.getHero(hero.getHeroName());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(savedHero.getHeroClass(), heroClass, "Hero Class is NOT Correct");
        softAssert.assertEquals(savedHero.getHeroLevel(), heroLevel, "Hero Level is NOT Correct");
        softAssert.assertEquals(savedHero.getUsername(), hero.getUsername(), "Username is NOT Correct");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(savedHero.getCreatedAt(), createdAt),"Created At Date is not correct!");
        softAssert.assertAll("Hero details for Hero " + hero.getHeroName() + " are NOT correct!");

    }
    private void cleanUp(){
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteHero(hero.getHeroName());
        } catch (AssertionError | Exception e) {
            log.error("Cleaning up failed! Message: " + e.getMessage());
        }
    }
}
