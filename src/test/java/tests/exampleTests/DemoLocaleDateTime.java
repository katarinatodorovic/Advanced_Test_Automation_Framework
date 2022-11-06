package tests.exampleTests;

import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;
import java.util.Date;

public class DemoLocaleDateTime extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private String sAdminUserName;
    private String sAdminPassword;
    private User user;
    private boolean bCreated = false;


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
        if(bCreated){
            cleanUp();
        }
    }
    /**
     * Test that checks localize date time in format
     * EEEE - full name of the day
     * dd - starts with 0 if day is single digit e.g. 05
     * MMMM - full name of the month
     * yyyy - year as 4-digit number
     * hh:mm - 12 digit format of the time e.g. 12:45 am / 12:45 pm
     * a - am / pm for the two periods of the day
     * z - time zone
     */
    @Test
    public void testDemoLocaleDateTime()  {
        Date date = DateTimeUtils.getCurrentDateTime();
        log.info(DateTimeUtils.getLocalizedDateTime(date,"EEEE, dd-MMMM-yyyy hh:mm a z", "s r"));
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
