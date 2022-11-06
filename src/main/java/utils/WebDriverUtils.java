package utils;

import data.Time;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Assert;
import java.time.Duration;

public class WebDriverUtils extends LoggerUtils{

    /**
     * Method that:
     * 1. checks which browser (chrome, firefox or  edge) is defined in the property file,
     * 2. checks if browser is headless and set the window size
     * 3. checks if test are running remote or not
         * If running remote, then make a new instance of the remote WebDriver and get
         * the remote driver from the path specified, if it is not, then set up a
         * browser locally and set path to that driver.
     * 4. create a driver for the browser specified. In case specified browser
     * is not supported or path to driver cannot be found the error will be thrown.
     * 5. default waits are defined:
         * default wait for setting up a driver is defined and is 1-second,
         * default wait for page to load and is 3-second and
         * driver wait for execution of an Asynchronous JS script which is 10-seconds.
     * 6. maximize Browser - this part is not executed when execution is headless
     * 7. finally an instance of a web driver is created
     *
     * @return {WebDriver} -the WebDriver instance
     */
    public static WebDriver setupDriver(){
        WebDriver driver = null;

        String sBrowser = PropertiesUtils.getBrowser();
        boolean bRemote = PropertiesUtils.getRemote();
        boolean bHeadless = PropertiesUtils.getHeadless();
        String sHubUrl= PropertiesUtils.getHubUrl();
        // WebDriverManager cannot in realtime download missing driver, but it can if it is run before suite
        // the problem is partially resolved by using fat jar from https://github.com/bonigarcia/webdrivermanager/releases
        // and that .jar file should be copied to all selenium nodes
        //String sDriverFolder = PropertiesUtils.getDriversFolder();
        // Instead of standard method way of implementing and running tests on different drivers
        // We will be using WebDriverManager that Boni Garcia created and that will resolve problem
        // with outdated drivers automatically
//        String sPathDriverChrome = sDriverFolder + "chromedriver.exe";
//        String sPathDriverFirefox = sDriverFolder + "geckodriver.exe";
//        String sPathDriverEdge = sDriverFolder + "msedgedriver.exe";

        String sRemote = "";

        if(bRemote){
            sRemote = "Remote";
        }

        log.debug(String.format("SetUp + %s Driver(%s, is browser instance headless: %b)",sRemote, sBrowser,bHeadless));
        try{
            switch (sBrowser){
                case "chrome":{
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(bHeadless);
                    //browser open with this windows size regardless if it is headless or not
                    options.addArguments("--window-size=1600x900");
                    WebDriverManager.chromedriver().setup();
                    if (bRemote){
                        // We will be using WebDriverManager and the line below is commented-out
                      //  RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                        // Every path that is passed to this method is recognised as a local path
                        // and driver that is set as local don't have this method in its class
                        // We will be using WebDriverManager and the two lines below are commented-out
                     //   remoteWebDriver.setFileDetector(new LocalFileDetector());
                     //   driver = remoteWebDriver;
                        driver = WebDriverManager.chromedriver().capabilities(options).remoteAddress(sHubUrl).create();
                    }else {
                        // We will be using WebDriverManager and the line below is commented-out
                     //   System.setProperty("webdriver.chrome.driver", sPathDriverChrome);
                        driver = new  ChromeDriver(options);
                    }
                    break;
                }
                case "firefox":{
                    FirefoxOptions options = new FirefoxOptions();
                    options.setHeadless(bHeadless);
                    options.addArguments("--window-size=1600x900");
                    WebDriverManager.firefoxdriver().setup();

                    if (bRemote){
                        // We will be using WebDriverManager and the three lines below are commented-out
                       // RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                       // remoteWebDriver.setFileDetector(new LocalFileDetector());
                     //   driver = remoteWebDriver;
                        driver = WebDriverManager.firefoxdriver().capabilities(options).remoteAddress(sHubUrl).create();
                    }else {
                        // We will be using WebDriverManager and the line below is commented-out
                     //   System.setProperty("webdriver.gecko.driver", sPathDriverFirefox);
                        driver = new FirefoxDriver(options);
                    }
                    break;
                }
                case "edge":{
                    EdgeOptions options = new EdgeOptions();
                    options.setHeadless(bHeadless);
                    options.addArguments("--window-size=1600x900");
                    WebDriverManager.edgedriver().setup();

                    if (bRemote){
                        // We will be using WebDriverManager and the three lines below are commented-out
                       // RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL(sHubUrl), options);
                       // remoteWebDriver.setFileDetector(new LocalFileDetector());
                      //  driver = remoteWebDriver;
                        driver = WebDriverManager.edgedriver().capabilities(options).remoteAddress(sHubUrl).create();
                    }else {
                        // We will be using WebDriverManager and the line below is commented-out
                       // System.setProperty("webdriver.msedgedriver.driver", sPathDriverEdge);
                        driver = new EdgeDriver(options);
                    }
                    break;
                }
                default:{
                    Assert.fail(String.format("Cannot create driver! Browser %s is not recognised!", sBrowser));
                }
            }
        } catch (Exception e){
            Assert.fail(String.format("Cannot create driver! Path to browser %s driver is not recognised", sBrowser));
        }
        // We will be using WebDriverManager and the lines below are commented-out
//        catch (MalformedURLException e){
//            //Here problem occurred with  <scope>test</scope> from testNG maven settings
//            //.fail() was not recognised
//            Assert.fail(String.format("Cannot create driver! Path to browser %s driver is not recognised", sBrowser));
//        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Time.IMPLICIT_TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Time.PAGE_LOAD_TIMEOUT));
        // Driver wait for execution of Asynchronous JS script
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Time.ASYNC_SCRIPT_TIMEOUT));

        // Maximize Browser - this part is not executed when execution is headless
        // It doesn't function
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Method that change the default implicit wait to desired number od seconds
     *
     * @param driver {WebDriver} - WebDriver
     * @param timeout {int} - timeout in seconds that we want to wait
     */
    public static void setImplicitWait(WebDriver driver, int timeout){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    /**
     * Method that return the SessionId number
     *
     * @param driver {WebDriver} - WebDriver
     *
     * @return {SessionID} - SessionID
     */
    public static SessionId getSessionID(WebDriver driver){
        return ((RemoteWebDriver) driver).getSessionId();
    }

    /**
     * Method that takes the WebDriver instance and check if the driver
     * session ID is null or not and return boolean. Basically, managing
     * the problematic scenario in case of the remote WebDriver when the
     * session ID is not null but the driver is quited
     *
     * @param driver {WebDriver} - WebDriver
     *
     * @return {boolean} - if session ID is null or not
     */
    public static boolean hasDriverQuit(WebDriver driver){
        if(driver != null){
            //In case of remote web diver it could occur that
            //session ID became null but driver is not quited
            //Error session ID is null but driver is not null
            //
            return getSessionID(driver) == null;
        }else {
            return true;
        }
    }

    /**
     * Takes {WebDriver} and terminate the WebDriver instance
     *
     * @param driver {WebDriver} - WebDriver
     */
    public static void quitDriver(WebDriver driver){
        if(!hasDriverQuit(driver)){
            driver.quit();
        }
    }
}
