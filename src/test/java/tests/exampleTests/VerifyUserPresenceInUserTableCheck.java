package tests.exampleTests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.BaseTestClass;
import utils.PropertiesUtils;

public class VerifyUserPresenceInUserTableCheck extends BaseTestClass {
    private final String sTestName = this.getClass().getName();
    private WebDriver driver;
    private String sUsername;
    private String sUserPassword;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        //It is better to login here because is better test to fail in setup
        //and our test will be skipped that means that problem is with setup and not with our test
        sUsername = PropertiesUtils.getEndUserUsername();
        sUserPassword = PropertiesUtils.getEndUserPassword();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        tearDown(driver,testResult);
    }

    /**
     * Test that verify if end user is present in the users table
     * Go to the Login page
     * Login as End User
     * Check if End user is present in Users table
     */
    @Test
    public void testVerifyUserPresenceInUserTableCheck()  {

        log.debug(String.format("[START TEST] %s", sTestName));

        LoginPage loginPage = new LoginPage(driver).open();
        WelcomePage welcomePage = loginPage.login(sUsername,sUserPassword);


        UsersPage usersPage = welcomePage.clickUsersTab();
        usersPage.search(sUsername);
        boolean isUserPresent = usersPage.isUserPresentInUsersTable(sUsername);
        Assert.assertTrue(isUserPresent, "User is not present in the Users table");

    }
}
