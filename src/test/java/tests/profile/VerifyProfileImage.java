package tests.profile;

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
import pages.ProfilePage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.RestApiUtils;
import utils.ScreenshotUtils;

@Jira(jiraID = "JIRA0013")
@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.IMAGE})
public class VerifyProfileImage extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private User user;
    private boolean bCreated = false;


    @BeforeMethod
    public void setUpTest(ITestContext testContext){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});

        user = User.createNewUniqueUser("ProfileImage");
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
         * Click on the Profile link
         * Take profile image and save as ProfileImageActual.png
         * Check if saved Profile image and image taken by the test same
     */
    @Test
    public void testVerifyProfileImage() {

        String sProfileImageFile = "ProfileImage.png";
        LoginPage loginPage = new LoginPage(driver).open();
        WelcomePage welcomePage = loginPage.login(user.getUsername(),user.getPassword());

        ProfilePage profilePage = welcomePage.clickProfileLink();
        profilePage.saveProfileImageSnapShotAS("ProfileImageActual.png");

        boolean areImagesSimilar = ScreenshotUtils.compareScreenshotWithImage
                (profilePage.getProfileImageSnapShot(), sProfileImageFile, 5, true);
        Assert.assertTrue(areImagesSimilar, "Profile Image is NOT correct!");

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
