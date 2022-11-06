package tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.ScreenshotUtils;
import utils.WebDriverUtils;

public abstract class BaseTestClass extends LoggerUtils {

    /**
     * Method that returns an instance of the WebDriver created in the WebDriver class
     *
     * @return WebDriver - an instance of the WebDriver
     */
    protected WebDriver setUpDriver(){
        log.debug("SetUpDriver");
        return WebDriverUtils.setupDriver();
    }

    /**
     * Method that quits created WebDriver instance
     *
     * @param driver {WebDriver}
     */
    protected void quitDriver(WebDriver driver){
        log.debug("quitDriver");
        WebDriverUtils.quitDriver(driver);
    }

    /**
     * Method that gets test status (passed,skipped or failed),
     * checks if that status is failed, checks if getTakeScreenshots()
     * is set to true and taking screenshots from test listener is set to false,
     * get session number (e.g. when we have two users - session 1 for the first
     * user and session 2 for the second user), check if it is greater
     * than 0 and add that number to name of the screenshot,
     * take a screenshot of that test and quit the WebDriver instance
     * @description - if we execute only one test then screenshot will be taken from
     * BaseTestClass and if suite is executed then screenshot will be taken from TestListener
     *
     * @param driver {WebDriver}
     * @param testResult {testResult} - get status of test (FAILED)
     * @param session {integer} - number of session
     *
     */
    private void ifFailed(WebDriver driver, ITestResult testResult, int session){
        if (testResult.getStatus() == ITestResult.FAILURE){
            if(PropertiesUtils.getTakeScreenshots() && !getListenerTakeScreenshot(testResult)){
                log.info("Screenshot from BaseTestClass!");
                String sTestName = testResult.getTestClass().getName();
                String sScreenshot = sTestName;
                if (session > 0){
                    sScreenshot = sScreenshot + "." + session;
                }
                ScreenshotUtils.takeScreenshot(driver,sScreenshot);
            }
        }
        WebDriverUtils.quitDriver(driver);
    }

    /**
     * Method that tears down test setup and quits the WebDriver instance
     * @description - see tearDown()
     *
     * @param driver {WebDriver}
     * @param testResult {testResult} - get status of test (FAILED)
     */
    protected void tearDown(WebDriver driver, ITestResult testResult){
        tearDown(driver,testResult,0);
    }

    /**
     * Method that tears down test setup, check if test failed,
     * get session number (e.g. when we have two users - session 1
     * for the first user and session 2 for the second user)
     * that is passed as parameter, make abs value of parameter,
     * check if it is greater than 0 and add that number to name of the screenshot.
     * Try to take a screenshot. If for some reason tear down
     * of the test failed, we will catch that error or exception,
     * so we don't have false negative due to unabillity to tear
     * down test setup and finally quits the WebDriver instance
     *
     * @param driver {WebDriver}
     * @param testResult {testResult} - get status of test (FAILED)
     * @param session {integer} - number of session
     */
    protected void tearDown(WebDriver driver, ITestResult testResult, int session){
        String sTestName = testResult.getTestClass().getName();
        log.debug(String.format("tearDown: %s", sTestName));
        session = Math.abs(session);
        String sSessionName = sTestName;
        if (session > 0){
            sSessionName = sSessionName + "." + session;
        }
        log.debug(String.format("tearDown: %s", sSessionName));
        try {
            ifFailed(driver,testResult,session);
        }
        catch (AssertionError | Exception e){
            log.error(String.format(" Exception occurred in teardown (%s)! Message: %s",sSessionName,e.getMessage()));
        }
        finally {
            quitDriver(driver);
        }
    }

    /**
     * Method that quits the WebDriver instance
     *
     * @param driver {WebDriver}
     */
    protected void tearDown(WebDriver driver){
        log.debug("tearDown()");
            quitDriver(driver);
    }

    /**
     * Method that indirectly checks if property takeScreenshot from common.properties is
     * set to true or not
     *
     * @param result {ITestResult} - ITestResult which will be passed from test
     *
     * @return {boolean} - if property takeScreenshot is true or false
     */
    private boolean getListenerTakeScreenshot(ITestResult result){
        try {
            return (boolean) result.getTestContext().getAttribute("listenerTakeScreenshot");
        } catch (Exception e){
            return false;
        }
    }
}
