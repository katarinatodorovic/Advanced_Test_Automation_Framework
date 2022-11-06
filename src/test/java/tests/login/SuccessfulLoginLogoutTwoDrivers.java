package tests.login;

import annotations.Jira;
import data.TestNGGroups;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PracticePage;
import pages.UsersPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.PropertiesUtils;

@Jira(jiraID = "JIRA0002")

@Test(groups = {TestNGGroups.REGRESSION, TestNGGroups.LOGIN, TestNGGroups.DEMO})
public class SuccessfulLoginLogoutTwoDrivers extends BaseTestClass {

   //what to do when we have two drivers and how to handle them in case when we have two users that
   // are exchanging messages back and forth example

        private String sTestName = this.getClass().getName();
        private WebDriver driver1;
        private WebDriver driver2;
        private String sAdminUserName;
        private String sAdminPassword;

        @BeforeMethod
        public void setUpTest(ITestContext testContext){
            log.info(String.format("[SETUP TEST] %s", sTestName));
            driver1 = setUpDriver();
            driver2 = setUpDriver();
            testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver1,driver2});
            //It is better to login here because is better test to fail in setup
            //and our test will be skipped that means that problem is with setup and not with our test
            sAdminUserName = PropertiesUtils.getAdminUserName();
            sAdminPassword = PropertiesUtils.getAdminPassword();
        }
        @AfterMethod(alwaysRun = true)
        public void tearDown(ITestResult testResult){
            log.info(String.format("[END TEST] %s", sTestName));
            tearDown(driver1,testResult,1);
            tearDown(driver2,testResult,2);
        }
    /**
     * Test example how to handle test with multiple driver instances
     */
        @Test
        public void testSuccessfulLoinLogoutTwoDrivers()  {

            log.debug(String.format("[START TEST] %s", sTestName));

            LoginPage loginPage1 = new LoginPage(driver1).open();
            LoginPage loginPage2 = new LoginPage(driver2).open();

            WelcomePage welcomePage1 = loginPage1.login(sAdminUserName,sAdminPassword);
            WelcomePage welcomePage2 = loginPage2.login(sAdminUserName,sAdminPassword);

            UsersPage usersPage1 = welcomePage1.clickUsersTab();
            PracticePage practicePage2 = welcomePage2.clickPracticeTab();

            Assert.fail("Nema!");
   }
}
