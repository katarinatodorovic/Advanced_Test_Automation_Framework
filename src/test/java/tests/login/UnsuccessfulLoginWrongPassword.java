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
import tests.BaseTestClass;
import utils.PropertiesUtils;

@Jira(jiraID = "JIRA0003")

@Test(groups = {TestNGGroups.REGRESSION, TestNGGroups.LOGIN, TestNGGroups.DEMO,TestNGGroups.BROKEN})
public class UnsuccessfulLoginWrongPassword  extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private String sAdminUserName;
    private String sAdminPassword;

    @BeforeMethod
    public void setUpTest(ITestContext testContext){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});
        //It is better to login here because is better test to fail in setup
        //and our test will be skipped that means that problem is with setup and not with our test
        sAdminUserName = PropertiesUtils.getAdminUserName();
        sAdminPassword = PropertiesUtils.getAdminPassword() + "!";
    }
    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        tearDown(driver,testResult);
        if(testResult.getStatus() == ITestResult.FAILURE){
            //catch  screenshot
        }
    }

    /**
     * Test that checks if we are unable to login and if error message is
     * displayed when password is wrong
         * 1. Grab the LoginPage
         * 2. Type Admin username
         * 3. Type Admin wrong password
         * 4. Click on Login button, return and verify that we are still on Login page
         * 5. Check if error message for invalid username or password is present
     */
    @Test
    public void testUnsuccessfulLoginWrongPassword(){

        String sExpectedLoginErrorMessage = CommonStrings.getLoginErrorMessage();

        log.debug(String.format("[START TEST] %s", sTestName));
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.typeUsername(sAdminUserName);;
        loginPage.typePassword(sAdminPassword);

        loginPage = loginPage.clickOnLoginButtonVerifyLoginPage();

        String sErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(sErrorMessage, sExpectedLoginErrorMessage,
                "Wrong credentials - Login Error Message is displayed");
    }
}
