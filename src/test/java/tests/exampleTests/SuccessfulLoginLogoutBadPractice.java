//package tests.login;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

//public class SuccessfulLoginLogoutBadPractice extends BaseTestClass {
    //example what to do in case we have multiple test in one class
    //problem with screen shoots and listeners and driver in case of parallelization
// It is not the best situation if it is parallelization in question to
    //private WebDriver driver = setUpDriver(); set as class variable because every test need his own
    //instance of driver if one test fail in this situation every other test will fail because don't have
    //his own instance of driver
    //there is problem wit situation if tst fail before rear down od driver
//finally without catch is better for debuging because it will show fail in catch even dough
    //test is failed on different line
    //disadvantage is screenshot of failed tests
// NoSuchSessionException: invalid session id is error that is thrown when driver instance is close
//before other test is executed

 //   WebDriver driver;
  /*  @BeforeMethod
    public void setUpTest(){
        log.debug("[SETUP TEST]");
        driver = setUpDriver();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult){
        if(testResult.getStatus() == ITestResult.FAILURE){
            //catch  screenshot
        }
    }

     @Test
    public void testSuccessfulLoinLogoutA() throws InterruptedException {
        WebDriver driver = setUpDriver();
        boolean bSuccess = false;
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open();
            Thread.sleep(5000);
            bSuccess = true;
        } finally {
            tearDown(driver,bSuccess);
        }
    }
    @Test
    public void testSuccessfulLoinLogoutB() throws InterruptedException {
        WebDriver driver = setUpDriver();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        Thread.sleep(5000);
        tearDown(driver,);
    }
    protected void tearDown(WebDriver driver, boolean bSuccess){
        //
        if (bSuccess){
            //and listener will save
          //  takeScreenShot(driver, pathToStoreScreenshots);
        }
        quitDriver(driver);
    }
}
    protected void tearDown(WebDriver driver, ITestResult testResult){
        String sTestName = testResult.getTestClass().getName();
        log.debug("tearDown(%s)",sTestName);
         try{
           if(testResult.getStatus() == ITestResult.FAILURE){
               log.warn("Test %s has failed!",sTestName);
               //take a screenshot
           }
        }catch (AssertionError | Exception e){
             log.error(String.format("Exception occurred in tearDown (%s)! Message: %s",sTestName, e.getMessage()));
         }
         finally {
             quitDriver(driver);
         }

    }
*/