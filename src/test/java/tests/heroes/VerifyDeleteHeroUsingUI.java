package tests.heroes;

import data.CommonStrings;
import data.TestNGGroups;
import objects.Hero;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.BaseTestClass;
import utils.RestApiUtils;

@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.SANITY,TestNGGroups.HEROES})
public class VerifyDeleteHeroUsingUI extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;
    private Hero hero;
    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();

        user = User.createNewUniqueUser("DeleteHero");
        RestApiUtils.postUser(user);
        bCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());

        hero = Hero.createNewUniqueHero(user, "HeroDelete");
        RestApiUtils.postHero(hero);
        hero.setCreatedAt(RestApiUtils.getHero(hero.getHeroName()).getCreatedAt());
        user.addHero(hero);
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
         * Login as newly created user
         * Go to heroes tab
         * Search for newly created hero
         * Click on th e Delete Hero ICon in the Heroes table
         * Check if "Successfully deleted" message is present
         * Check if hero is still exist in DB using GET API call
         * Verify hero is not present on the UI
     */
    @Test
    public void testVerifyDeleteHeroUsingUI()  {
        String sExpectedDeleteMessage = CommonStrings.getDeleteHeroMessage(
                hero.getHeroName(),hero.getHeroClass(),hero.getHeroLevel().toString());
        log.info(sExpectedDeleteMessage);

        log.debug(String.format("[START TEST] %s", sTestName));
        LoginPage loginPage = new LoginPage(driver).open();

        WelcomePage welcomePage = loginPage.login(user);

        HeroesPage heroesPage = welcomePage.clickHeroesTab();

        //search for heroes click on delete button

        heroesPage.searchHero(hero.getHeroName());

        DeleteHeroDialogBox deleteHeroDialogBox = heroesPage.clickDeleteHeroIconInHeroesTable(hero.getHeroName());
        String sDeleteHeroMessage = deleteHeroDialogBox.getDeleteHeroMessage();
        Assert.assertEquals(sDeleteHeroMessage, sExpectedDeleteMessage, "Wrong Delete Hero Message!");

        heroesPage = deleteHeroDialogBox.clickOnDeleteButtonToHeroesPage();

        Assert.assertFalse(RestApiUtils.checkIfHeroExist(hero.getHeroName()),String.format("Hero %s is NOT deleted from table!",hero.getHeroName()));

        User savedUser = RestApiUtils.getUser(user.getUsername());
        int numberOfHeroes = savedUser.getHeroCount();
        Assert.assertEquals(numberOfHeroes,0, "User's Hero is NOT deleted!");



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
