package listeners;

import annotations.Jira;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReportUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.ScreenshotUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * The class that handles tests based on test results
 */
public class TestListener extends LoggerUtils implements ITestListener {

    // We don't need to always create a Jira, only when a specific suite is executed or
    // a specific tests and that is why we have this variable that can be changed based
    // on our needs for crating a Jira
    private static boolean bUpdateJira = false;

    private static final boolean bListenerTakeScreenshot = PropertiesUtils.getTakeScreenshots();
    private static String sExtentReportName;
    private static String sExtentReportFolder;
    private static String sExtentReportFilesFolder;
    private static ExtentReports extentReport = null;

    // this is how it is handled problem with the parallelization and how to make it thread safe
    // extent test it is now thread safe
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
   // private static ITestContext context;


    /**
     * @description - in ITestContext we can add some attribute which we want to store in
     * a map. Map key is a String and attribute is object of any type. So we can use ITestContext
     * to pass WebDriver instance, but the problem with ITestContext is that the ITestContext
     * is common for all tests from the same TestNG group, it is not for each test individually.
     * ITestContext is created when a whole group of tests is started to execute,
     * i.e. when a test suite is launched. Here we also check if we need to take a screenshot
     * and if we need to update Jira
     */
    @Override
    public void onStart(ITestContext context) {
        String sSuiteName = context.getSuite().getName();
        extentReport = ExtentReportUtils.createExtentReportInstance(sSuiteName);
        log.info("[SUITE STARTED] " + sSuiteName);
        context.setAttribute("listenerTakeScreenshot", bListenerTakeScreenshot);
        bUpdateJira = getUpdateJira(context);
        sExtentReportName = ExtentReportUtils.getExtentReportName(sSuiteName);
        sExtentReportFolder = ExtentReportUtils.getExtentReportFolder(sSuiteName);
        sExtentReportFilesFolder = ExtentReportUtils.getExtentReportFilesFolder(sSuiteName);
        // the commented out line can't be here (below), because it catches the different time between when suite is created and
        // when data for the report is created
        // than delay occurs between these two times which is about 2 seconds and then
        // the images were not shown at the report and that is why this line is now at the beginning
        // extentReport = ExtentReportUtils.createExtentReportInstance(sSuiteName);
        // because of the parallelization we have to make it thread safe
        // so every test now have its own data
    }

    @Override
    public void onFinish(ITestContext context) {
        String sSuiteName = context.getSuite().getName();
        log.info("[SUITE FINISHED] " + sSuiteName);
        if (extentReport != null) {
            extentReport.flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String sTestName = result.getTestClass().getName();
        log.info("[TEST STARTED] " + sTestName);
        // this is how we deal with parallelization and make it thread safe
        ExtentTest test = extentReport.createTest(sTestName);
        extentTest.set(test);
        String jiraID = "";
        Jira jira = getJiraDetails(result);
        if (jira != null){
            jiraID = jira.jiraID();
        }

        // when we group tests by the package
        String sPackage = result.getTestClass().getRealClass().getPackage().getName();
        test.assignCategory(sPackage);
        // when we group tests by the TestNG groups
//        String[] groups = result.getMethod().getGroups();
//        for (String group : groups){
//            test.assignCategory(group);
//        }
        test.info(jiraID);
    }

   /**
    *  Here we check if we need to update Jira and based on results
    *  we get from test we populate jira with details like JiraID,
    *  assign an owner, get info about browser, environment and version
    *  and all that can be done via jira-client that needs to be implemented
    *  but here that step is missing - we don't have Jira linked for this practice application
    */
    @Override
    public void onTestSuccess(ITestResult result) {
        String sTestName = result.getTestClass().getName();
        log.info("[TEST SUCCESSES] " + sTestName);

        if(bUpdateJira){
            Jira jira = getJiraDetails(result);
            if (jira!= null){
                String jiraID = jira.jiraID();
                String owner = jira.owner();
                String browser = PropertiesUtils.getBrowser();
                String environment = PropertiesUtils.getEnvironment();
                String appVersion = "version";
                log.info("Jira ID: " + jiraID);
                log.info("Jira Owner: " + owner);
                }
            //Using jira-client update Jira TestCase
        }
        // if test is passing
        String sText = "<b> Test " + sTestName + " Passed</b>";
        Markup markup = MarkupHelper.createLabel(sText, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS, markup);
    }

    /**
     * Here we need an instance of WebDriver (getWebDriverInstance, getWebDriverInstances),
     * so we get screenshot of failed test and, it won't collide especially if test run
     * in parallel. Here we also check if we need to update Jira and get subject from result
     * and description - all that can be done via jira-client that needs to be implemented
     * but here that step is missing - we don't have Jira linked for this practice application
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String sTestName = result.getTestClass().getName();
        log.info("[TEST FAILED] " + sTestName);

        if(bListenerTakeScreenshot) {
            WebDriver[] drivers = getWebDriverInstances(result);
            // if test have more than one driver
            if (drivers != null) {
                for (int i = 0; i < drivers.length; i++) {
                    String sScreenshotName = sTestName;
                    if (drivers.length > 1) {
                        // i + 1 because array starts from 0 and a driver from 1,2,...
                        sScreenshotName = sScreenshotName + "." + (i + 1);
                    }

                    String sRelativeScreenshotPath = takeAndCopyScreenshot(drivers[i], sScreenshotName);
                    // if we have more than one driver then we need to determine which session took the screenshot
                    String sSession = "";
                    if(drivers.length > 1){
                        sSession = "(Session " + (i+1) + ")";
                    }
                    if (sRelativeScreenshotPath != null) {
                        extentTest.get().fail("Screenshot of failure" +sSession + " (click on thumbnail to expand)", MediaEntityBuilder.createScreenCaptureFromPath(sRelativeScreenshotPath).build());
                    } else {
                        extentTest.get().fail("Screenshot of failure, session: " + sSession +  " could NOT be captured!");
                    }
                }
            } else {
                extentTest.get().fail("Screenshot of failure could NOT be captured!");
            }
        }

        if (bUpdateJira){
            Jira jira = getJiraDetails(result);
            String sBugSubject = sTestName + "_" + result.getThrowable().getMessage();
            String sBugDescription = Arrays.toString(result.getThrowable().getStackTrace());
            //Using jira-client
        }
        String sFailedTestErrorLog = createFailedTestErrorLog(result);

        extentTest.get().fail(sFailedTestErrorLog);

        // if test is failing
        String sText = "<b> Test " + sTestName + " Failed</b>";
        Markup markup = MarkupHelper.createLabel(sText, ExtentColor.RED);
        extentTest.get().log(Status.FAIL, markup);
    }

    /**
     * Method that gets number of skipped tests
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        String sTestName = result.getTestClass().getName();
        log.info("[TEST SKIPPED] " + sTestName);
        // if test is skipped
        String sText = "<b> Test " + sTestName + " Skipped</b>";
        Markup markup = MarkupHelper.createLabel(sText, ExtentColor.ORANGE);
        extentTest.get().log(Status.SKIP, markup);
    }
/**
 * @description - Method that is commented out gets a WebDriver instance from ITestResult,
 * this way of handling driver instance is ok if we need to quickly adapt to existing test
 * framework but the disadvantage is that our driver instance should be named as "driver"
 * and not anything else like "newDriver", "driver2" etc... declaration of driver variable
 * can't be private and can only be public, which is not best practise. In other two methods
 * thad handling WebDriver - getWebDriverInstance and getWebDriverInstances it is important
 * that attribute be unique testContext.setAttribute()
 */
//    private static WebDriver getWebDriverInstance(ITestResult result){
//        WebDriver driver = null;
//        try {
//             driver = (WebDriver) result.getTestClass().getRealClass().
//                     getDeclaredField("driver").get(result.getInstance());
//        } catch (IllegalAccessException | NoSuchFieldException e){
//            log.error(String.format("Cannot get the Driver Instance for test: %s! Message: %s",
//                    result.getTestClass().getName(), e.getMessage()));
//        }
//        return driver;
//    }

    /**
     * Method that gets a WebDriver instance from test and return that instance
     * @description - we get WebDriver instance from ITestResult and need that
     * instance so our listener can work properly
     */
    private static WebDriver getWebDriverInstance(ITestResult result){
        String sTestName = result.getTestClass().getName();
        String sDriverName = sTestName + ".driver";
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute(sDriverName);
        if(driver == null){
            log.error(String.format("Cannot get the Driver Instance for test: %s!",
                    result.getTestClass().getName()));
        }
        return driver;
    }

    /**
     *  Method that handles situation when we have more than one instance of
     *  the WebDriver in our test, and store them as array
     */
    private static WebDriver[] getWebDriverInstances(ITestResult result){
        String sTestName = result.getTestClass().getName();
        String sDriverNames = sTestName + ".drivers";
        WebDriver[] drivers = (WebDriver[]) result.getTestContext().getAttribute(sDriverNames);
        if(drivers == null){
            log.error(String.format("Cannot get the Driver Instance(s) for tests: %s!",
                    result.getTestClass().getName()));
        }
        return drivers;
    }

    /**
     * Method that try to get the Jira details from test annotation from test results
     * and return that details
     * @description - this way we don't need to rely only on tester to write jiraID as it is expected
     * which can cause an error
     *
     * @param result {ITestResult}- ITestResult
     *
     * @return {Jira} - Jira details
     */
    private static Jira getJiraDetails(ITestResult result){
        String sTestName = result.getTestClass().getName();
        Jira jira = null;
        try {
            jira = result.getTestClass().getRealClass().getAnnotation(Jira.class);

        } catch (Exception e){
            log.error(String.format("Cannot get Jira details for test: %s! Message: %s",
                    result.getTestClass().getName(), e.getMessage()));
        }
        return jira;
    }

    /**
     * We want to pass info from xml file that is monitored by the listener about
     * whether we want to update Jira when suite is executed or not; and the
     * best way to do it is via parameter <parameter name="updateJira" value="true"/>
     * which we will be added to xml file (test suite) and will be picked up by the listener
     */
    private static boolean getUpdateJira(ITestContext context){
        String sSuiteName = context.getSuite().getName();
        String sUpdateJira = context.getCurrentXmlTest().getParameter("updateJira");
        if(sUpdateJira == null){
            log.warn(String.format("Parameter 'updateJira' is not set in %s suite",sSuiteName));
            return false;
        } else {
            if(!sUpdateJira.equalsIgnoreCase("true") && !sUpdateJira.equalsIgnoreCase("false")){
                log.warn(String.format("Parameter 'updateJira' in %s suite is not recognised as boolean value!",sSuiteName));
                return false;
            }
        }
        boolean bUpdateJira = Boolean.parseBoolean(sUpdateJira);
        log.info("Update Jira: " + bUpdateJira);
        return bUpdateJira;
    }

    /**
     * Method that creates String and test error log
     */
    private static String createFailedTestErrorLog(ITestResult result){
         String sExceptionMessage = result.getThrowable().getMessage();

        String sExceptionStackTrace = Arrays.toString(result.getThrowable().getStackTrace());
        // "dfjdsldsjkfg","dfkjfsogjfi" it returns a String array that are separated by comma
        // and because it is in html format then we make every String to start at the new line
        sExceptionStackTrace = sExceptionStackTrace.replaceAll(",", "<br>");
        return "<font color=red><b>" + sExceptionMessage + "</b>" +
                "<details><summary>" + "\nClick to see details" + "</font></summary>" + sExceptionStackTrace + "</details> \n";
    }

    /**
     * The method that returns relative path to screenshot, and then it copies path to Extent report
     */
    private static String takeAndCopyScreenshot(WebDriver driver, String sTestName){
         String sSourcePath = ScreenshotUtils.takeScreenshot(driver, sTestName);
         if (sSourcePath == null){
             return null;
         }
         File srcScreenshot = new File(sSourcePath);
         String sScreenshotName = srcScreenshot.getName();
         String sDestinationPath = sExtentReportFilesFolder + sScreenshotName;
         File dstScreenshot = new File(sDestinationPath);
        try {
            FileUtils.copyFile(srcScreenshot,dstScreenshot);
        } catch (IOException e) {
            log.warn(String.format("Screenshot %s could NOT be copied in the folder %s, Message: "),sScreenshotName,sExtentReportFilesFolder,e.getMessage());
            return null;
        }
      //  return sExtentReportFilesFolder  + sScreenshotName;
        return sExtentReportName + "/" + sScreenshotName;

    }
}
