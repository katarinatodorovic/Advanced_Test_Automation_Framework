package tests.exampleTests;

import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTestClass;
import utils.PropertiesUtils;


public class TestUserClass extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private String sAdminUserName;
    private String sAdminPassword;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        sAdminUserName = PropertiesUtils.getAdminUserName();
        sAdminPassword = PropertiesUtils.getAdminPassword();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        tearDown(driver,testResult);
    }
    /**
     * Example how to handle teardown when we have two sessions/
     * two users
     */
    @Test
    public void testUserClass()  {
        User user1 = User.createNewUniqueUser("Trla");
        User user2 = User.createNewUniqueUser("Baba");

        log.info(user1);
        log.info(user2  );
    }
}
