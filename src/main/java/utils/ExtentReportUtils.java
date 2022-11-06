package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.nio.charset.StandardCharsets;

/**
 * Class for logs managing
 */
public class ExtentReportUtils extends LoggerUtils{

    private static final String extentReportBaseFolder = System.getProperty("user.dir") +  PropertiesUtils.getExtentReportsFolder();

    /**
     * Method that creates an Extent Report folder using suite name
     *
     * @param sSuiteName {String} - name of the suite that we are running
     *
     * @return {String} - Extent Report folder path
     */
    public static String getExtentReportFolder(String sSuiteName){
        return extentReportBaseFolder + sSuiteName + "\\";
    }

    /**
     * Method that creates an Extent name using suite name and add date time stamp
     *
     * @param sSuiteName {String} - name of the suite that we are running
     *
     * @return {String} - Extent Report name
     */
    public static String getExtentReportName(String sSuiteName){
        return sSuiteName.replace(" ", "_") + "_" + DateTimeUtils.getDateTimeStamp();
    }

    /**
     * Method that create an Extent Report path using suite name
     *
     * @param sSuiteName {String} - name of the suite that we are running
     *
     * @return {String} - Extent Report path
     */
    public static String getExtentReportFilePath(String sSuiteName){
        return getExtentReportFolder(sSuiteName) + getExtentReportName(sSuiteName) + ".html";
    }

    /**
     * Method that gets an Extent Report files folder using suite name
     *
     * @param sSuiteName {String} - name of the suite that we are running
     *
     * @return {String} - Extent Report files folder
     */
    public static String getExtentReportFilesFolder(String sSuiteName){
        return getExtentReportFolder(sSuiteName) + getExtentReportName(sSuiteName) + "\\";
    }

    /**
     * Method that create an Extent Report
     *
     * @param sSuiteName {String} - name of the suite that we are running
     *
     * @return {ExtentReports} an instance of the ExtentReports
     */
    public static ExtentReports createExtentReportInstance(String sSuiteName){
        String sExtentReportsPath = getExtentReportFilePath(sSuiteName);
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(sExtentReportsPath);
        extentSparkReporter.config().setEncoding(StandardCharsets.UTF_8.toString());
        extentSparkReporter.config().setReportName(getExtentReportName(sSuiteName) + " Report");
        extentSparkReporter.config().setDocumentTitle(getExtentReportName(sSuiteName) + " Results");
        extentSparkReporter.config().setTheme(Theme.STANDARD);

        ExtentReports extentReport = new ExtentReports();
        extentReport.setSystemInfo("Environment", PropertiesUtils.getBaseUrl());
        extentReport.setSystemInfo("Browser", PropertiesUtils.getBrowser());
        extentReport.setSystemInfo("Headless", String.valueOf(PropertiesUtils.getHeadless()));
        extentReport.setSystemInfo("Remote", String.valueOf(PropertiesUtils.getRemote()));
        extentReport.attachReporter(extentSparkReporter);
        return extentReport;
    }
}
