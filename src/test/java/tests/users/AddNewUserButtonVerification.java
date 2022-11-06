package tests.users;

import data.TestNGGroups;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddUserDialogBox;
import pages.LoginPage;
import pages.UsersPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;

@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.SANITY,TestNGGroups.LOGIN, TestNGGroups.BROKEN})
public class AddNewUserButtonVerification extends BaseTestClass {

    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private String sAdminUserName;
    private String sAdminPassword;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
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
     * Test that checks if Add New User button on the Users Page works
     *
     * 1. Log in as admin
     * 2. Go to the Users Page
     * 3. Click on the Add New User button
     * 4. Click on the Cancel button
     * 5. Logout
     */
    @Test
    public void testAddNewUserButtonVerification()  {

        log.debug(String.format("[START TEST] %s", sTestName));
        LoginPage loginPage = new LoginPage(driver).open();

        WelcomePage welcomePage = loginPage.login(sAdminUserName,sAdminPassword);
        UsersPage usersPage = welcomePage.clickUsersTab();

        AddUserDialogBox addUserDialogBox = usersPage.clickAddNewUserButton();

        // The two line below that are commented out are bad practice
        // It is the example how to get a stale element reference
        // with this line usersPage.clickLogoutLink(); we call an instance that is defined at the beginning
        // then we moved to addUserDialog Box and go back again to usersPage
       // addUserDialogBox.clickOnCancelButton();
      //  usersPage.clickLogoutLink();

        // test was failing without this delay
        DateTimeUtils.wait(Time.DEMONSTRATION);
        usersPage = addUserDialogBox.clickOnCancelButton();
        usersPage.clickLogoutLink();
    }
}
