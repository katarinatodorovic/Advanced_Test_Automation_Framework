package tests.login;

import data.CommonStrings;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.RestApiUtils;

public class NewlyCreatedUserCanLogin extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private User user;
    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        user = User.createNewUniqueUser("NewlyUserCanLogin");
        log.info(user);
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
     * Test that check if we can successfully log in and logout:
     * 1. Grab the LoginPage
     * 2. Check if there is no message that we are successfully logged out
     * 3. Check if there is no error message for invalid username or password
     * 4. Type Admin username
     * 5. Type Admin password
     * 6. Click on Login button, return and verify that we are on Welcome page
     * 7. Click on Log out link
     * 8. Check if there is no error message for invalid username or password
     * 9. Check if there is message that we are successfully logged out
     * 10. Check if that message is what we expected
     */
    @Test
    public void testSuccessfulLoinLogout()  {

        String sExpectedLogoutSuccessMessage = CommonStrings.getLogoutSuccessMessage();

        log.debug(String.format("[START TEST] %s", sTestName));
        LoginPage loginPage = new LoginPage(driver).open();
        Assert.assertFalse(loginPage.isSuccessMessageDisplayed(),
                "Success Message should NOT be displayed");
        Assert.assertFalse(loginPage.isErrorMessageDisplayed(),
                "Error Message should NOT be displayed");

        loginPage.typeUsername(user.getUsername());
        loginPage.typePassword(user.getPassword());

        WelcomePage welcomePage = loginPage.clickOnLoginButtonVerifyWelcomePage();
        loginPage = welcomePage.clickLogoutLink();

        Assert.assertFalse(loginPage.isErrorMessageDisplayed(),
                "Error Message should NOT be displayed");
        String sSuccessMessage = loginPage.getSuccessMessage();
        Assert.assertEquals(sSuccessMessage, sExpectedLogoutSuccessMessage,
                "Wrong Logout Success Message is displayed");

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
