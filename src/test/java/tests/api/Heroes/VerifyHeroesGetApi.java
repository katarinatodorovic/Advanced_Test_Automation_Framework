package tests.api.Heroes;

import objects.Hero;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;

public class VerifyHeroesGetApi extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User userVerify;
    private Hero heroVerify;
    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        userVerify = User.createNewUniqueUser("VerifyHeroGetApi");
        RestApiUtils.postUser(userVerify);
        heroVerify = Hero.createNewUniqueHero(userVerify, "HeroGETApi");
        heroVerify.setCreatedAt(DateTimeUtils.getCurrentDateTime());
        RestApiUtils.postHero(heroVerify);
        bCreated = true;
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
     * Test that verify if GET API call return correct details
         * Create unique User in before test
         * Create unique Hero in before test
         * Get Hero detail using GET API call
         * Check if Hero details are correct as created in before test
         * (hero's name, hero's class, hero's level, hero's username, hero's created at)
     */
    @Test
    public void testVerifyHeroesGetApi()  {
        String heroName = heroVerify.getHeroName();
        Hero heroGETApi = RestApiUtils.getHero(heroName);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(heroVerify.getHeroName(),heroGETApi.getHeroName(), "Wrong Hero NAME!");
        softAssert.assertEquals(heroVerify.getHeroClass(),heroGETApi.getHeroClass(), "Wrong Hero CLASS!");
        softAssert.assertEquals(heroVerify.getHeroLevel(),heroGETApi.getHeroLevel(), "Wrong Hero LEVEL!");
        softAssert.assertEquals(heroVerify.getUsername(),heroGETApi.getUsername(), "Wrong Hero USERNAME!");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(heroVerify.getCreatedAt(),heroGETApi.getCreatedAt()), "Wrong Hero CREATED AT!");

        softAssert.assertAll("Wrong Error Response Details!");

    }
    private void cleanUp(){
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(userVerify.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Cleaning up failed! Message: " + e.getMessage());
        }
    }
}
