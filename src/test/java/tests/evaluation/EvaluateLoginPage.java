package tests.evaluation;

import data.TestNGGroups;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import tests.BaseTestClass;

@Test(groups = {TestNGGroups.EVALUATION})
public class EvaluateLoginPage  extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
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
     * Test that check if every element is displayed on the Login page
     * It checks if there is:
         * Username Text Field
         * Password Text Field
         * Login Button
         * Create Account Link
         * Reset Password
     */
    @Test
    public void testEvaluateLoginPage(){

        log.debug(String.format("[START TEST] %s", sTestName));
        LoginPage loginPage = new LoginPage(driver).open();
        // Here is better to use the SoftAssert because if there is one element missing on the page
        // with classic assert - Assert.assertTrue (loginPage.isUsernameTextFieldDisplayed(),
        // "Username Text Field NOT displayed on Login Page");, the test will break on missing element
        // But with SoftAssert it will check all elements and then test will fail on element that missing

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue (loginPage.isUsernameTextFieldDisplayed(),
                "Username Text Field is NOT displayed on Login Page");
        softAssert.assertTrue (loginPage.isPasswordTextFieldDisplayed(),
                "Password Text Field is NOT displayed on Login Page");
        softAssert.assertTrue (loginPage.isLoginButtonDisplayed(),
                "Login Button is NOT displayed on Login Page");
        softAssert.assertTrue (loginPage.isCreateAccountLinkDisplayed(),
                "Create Account Link is NOT displayed on Login Page");
        softAssert.assertTrue (loginPage.isResetPasswordLinkDisplayed(),
                "Reset Password is NOT displayed on Login Page");
        softAssert.assertAll("At least one Web Element is NOT displayed on Login Page! Locator(s) changed?");
   }
}

