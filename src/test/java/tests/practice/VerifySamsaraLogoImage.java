package tests.practice;

import annotations.Jira;
import data.TestNGGroups;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PracticePage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.RestApiUtils;
import utils.ScreenshotUtils;

@Jira(jiraID = "JIRA0015")
@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.MOUSE})
public class VerifySamsaraLogoImage extends BaseTestClass {
    private final String sTestName = this.getClass().getName();
    private WebDriver driver;
    private User user;
    private boolean bCreated = false;


    @BeforeMethod
    public void setUpTest(ITestContext testContext){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});

        user = User.createNewUniqueUser("SamsaraImage");
        RestApiUtils.postUser(user);
        bCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
        log.info(user);
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
     * Test that verify if profile image is correct
         * Go to login page and login as newly created user
         * Click on the Practice link
         * Take Samsara logo image and save as SamsaraLogoActual.png
         * Check if saved Samsara logo image and image taken by the test same
     */
    @Test
    public void testVerifySamsaraLogoImage() {

        String samsaraLogoImageFile = "SamsaraLogo.png";
        LoginPage loginPage = new LoginPage(driver).open();
        WelcomePage welcomePage = loginPage.login(user.getUsername(),user.getPassword());

        PracticePage practicePage = welcomePage.clickPracticeTab();
        practicePage.saveSamsaraLogoImageSnapShotAS("SamsaraLogoActual.png");
        boolean areImagesSimilar = ScreenshotUtils.compareScreenshotWithImage(practicePage.getSamsaraLogoImageSnapShotAS().getImage(), samsaraLogoImageFile, 5, true);
        Assert.assertTrue(areImagesSimilar, "Samsara Logo Image is NOT correct!");
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
