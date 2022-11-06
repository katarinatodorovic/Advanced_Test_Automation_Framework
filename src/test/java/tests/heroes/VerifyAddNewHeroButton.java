package tests.heroes;

import data.TestNGGroups;
import data.Time;
import objects.Hero;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;

@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.SANITY,TestNGGroups.HEROES})
public class VerifyAddNewHeroButton extends BaseTestClass {
    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;
    private Hero hero;
    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        user = User.createNewUniqueUser("AddNewHero");
        RestApiUtils.postUser(user);
        bCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());

        hero = Hero.createNewUniqueHero(user, "NewHero");
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
     * Test that verify that we can add new hero using Add Hero Dialog Box on the Heroes page
         * Go to the Login page and login as newly created user
         * Go to the Heroes page and click Add New Hero Button
         * Type the Hero's name
         * Type the Hero's level
         * Type the Hero's class
         * Click on the save button
         * Set hero's created at time to current time
         * Use API call Get to check if details in DB are correct
         * Check if hero's details are correct using User's information
     */
    @Test
    public void testVerifyAddNewHeroButton()  {

        log.debug(String.format("[START TEST] %s", sTestName));
        LoginPage loginPage = new LoginPage(driver).open();

        WelcomePage welcomePage = loginPage.login(user);

        HeroesPage heroesPage = welcomePage.clickHeroesTab();
        AddHeroDialogBox addHeroDialogBox = heroesPage.clickAddNewHeroButton();


        addHeroDialogBox.typeName(hero.getHeroName());
        addHeroDialogBox.typeLevel(hero.getHeroLevel().toString());
        addHeroDialogBox.selectedHeroClass(hero.getHeroClass());

        DateTimeUtils.wait(Time.DEMONSTRATION);
        heroesPage = addHeroDialogBox.clickOnSaveButton();
        hero.setCreatedAt(DateTimeUtils.getCurrentDateTime());
        user.addHero(hero);
        log.info(user);

        Hero savedHero = RestApiUtils.getHero(hero.getHeroName());
        SoftAssert softAssert1 = new SoftAssert();
        softAssert1.assertEquals(savedHero.getHeroClass(), hero.getHeroClass(), "Hero Class is NOT Correct");
        softAssert1.assertEquals(savedHero.getHeroLevel(), hero.getHeroLevel(), "Hero Level is NOT Correct");
        softAssert1.assertEquals(savedHero.getUsername(), hero.getUsername(), "Username is NOT Correct");
        softAssert1.assertTrue(DateTimeUtils.compareDateTimes(savedHero.getCreatedAt(),hero.getCreatedAt()),"Created At Date is not correct!");
        softAssert1.assertAll("Hero details for Hero " + hero.getHeroName() + " are NOT correct!");

        User savedUser = RestApiUtils.getUser(user.getUsername());
        log.info(savedUser);

        Hero usersHero = savedUser.getHero(hero.getHeroName());

        SoftAssert softAssert2 = new SoftAssert();
        softAssert2.assertEquals(usersHero.getHeroClass(), hero.getHeroClass(), "Hero Class is NOT Correct");
        softAssert2.assertEquals(usersHero.getHeroLevel(), hero.getHeroLevel(), "Hero Level is NOT Correct");
        softAssert2.assertEquals(usersHero.getUsername(), hero.getUsername(), "Username is NOT Correct");
        softAssert2.assertTrue(DateTimeUtils.compareDateTimes(usersHero.getCreatedAt(),hero.getCreatedAt()),"Created At Date is not correct!");
        softAssert2.assertAll("Hero details for Hero " + hero.getHeroName() + " are NOT correct!");

    }

    private void cleanUp(){
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(user.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Cleaning up failed! Message: " + e.getMessage());
        }
    }
}
