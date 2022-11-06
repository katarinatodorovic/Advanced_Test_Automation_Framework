package tests.login;

import annotations.Jira;
import data.CommonStrings;
import data.TestNGGroups;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.PropertiesUtils;

//Because here is one test one class group can be defined on class level

@Jira(jiraID = "JIRA0001")
@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.SANITY,TestNGGroups.LOGIN,
        TestNGGroups.DEMO, TestNGGroups.BROKEN})
public class SuccessfulLoginLogout extends BaseTestClass {

    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    // first way how to handle JIRA implementation
    public String jiraID = "JIRA0001";
    private String sAdminUserName;
    private String sAdminPassword;

    @BeforeMethod
    public void setUpTest(ITestContext testContext){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});
        // 2 way how to handle JIRA implementation
        testContext.setAttribute(sTestName + ".jira", "JIRA0001");
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

        loginPage.typeUsername(sAdminUserName);
        loginPage.typePassword(sAdminPassword);
        Assert.fail("PADANCIJA!!!");
        WelcomePage welcomePage = loginPage.clickOnLoginButtonVerifyWelcomePage();
        loginPage = welcomePage.clickLogoutLink();

        Assert.assertFalse(loginPage.isErrorMessageDisplayed(),
                "Error Message should NOT be displayed");
        String sSuccessMessage = loginPage.getSuccessMessage();
        Assert.assertEquals(sSuccessMessage, sExpectedLogoutSuccessMessage,
                "Wrong Logout Success Message is displayed");

    }
}
