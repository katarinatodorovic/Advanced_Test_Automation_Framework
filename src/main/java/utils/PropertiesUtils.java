package utils;

import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A class that has all methods and variables for getting a property file
 * and individual properties and their value from the property file
 */
public class PropertiesUtils extends LoggerUtils{
    private static final String sPropertiesPath = "common.properties";
    private static final Properties properties = loadPropertiesFile();

    /**
     * Method that takes a path to a property file as a String parameter
     * checks if the file can be found on the path specified and return that
     * property file
     *
     * @param sFilePath {String} - a path where the property file is stored e.g. "common.properties"
     *
     * @return {Properties} - property file
     */
    // load property that we specified with string as file path
    public static Properties loadPropertiesFile(String sFilePath){
        //class Properties is implemented in Java as map property equal value -> key-value
        // property=valueOfProperty
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        }catch (IOException e){
            Assert.fail(String.format("Cannot load %s file! Message: %s", sFilePath, e.getMessage()));
        }

        return properties;
    }

    /**
     * Method that returns default property file common.properties
     *
     * @return {Properties} - default property file common.properties
     */
    //load default property file common.properties which is common for all and is defined inside this class
    private static Properties loadPropertiesFile(){
        return loadPropertiesFile(sPropertiesPath);
    }

    /**
     * Method that takes the name of a specific property defined in the property file and
     * returns a value of that property defined in the property file
     *
     * @param sProperty {String} - the name of the property defined in the property file
     *
     * @return {Properties} - value of a specific property that is stored in the property file
     */
    private static String getProperty(String sProperty){

        log.trace(String.format("getProperty: %s",sProperty));
        String sResult = properties.getProperty(sProperty);
        Assert.assertNotNull(sResult, String.format("Cannot find property %s in %s file",sProperty,sPropertiesPath));
        return sResult;
    }

    /**
     * Method that returns the value of the property environment defined in the property file
     *
     * @return {String} - value of the property environment from the property file
     */
    public static String getEnvironment(){
        return getProperty("environment");
    }

    /**
     * Method that returns the value of the property localBaseUrl defined in the property file
     * which is link for an application on the local machine
     *
     * @return {String} - value of the property localBaseUrl from the property file
     */
    public static String getLocalBaseUrl(){
        return getProperty("localBaseUrl");
    }

    /**
     * Method that returns the value of the property testBaseUrl defined in the property file
     * which is link for an application on the test environment
     *
     * @return {String} - value of the property testBaseUrl from the property file
     */
    public static String getTestBaseUrl(){
        return getProperty("testBaseUrl");
    }

    /**
     * Method that returns the value of the property prodBaseUrl defined in the property file
     * which is link for an application on the production
     *
     * @return {String} - value of the property prodBaseUrl from the property file
     */
    public static String getProdBaseUrl(){
        return getProperty("prodBaseUrl");
    }

    /**
     * Method that gets the base URL based on the test environment (local, test or prod)
     * defined in the property file
     *
     * @return {String} - URL to an application on local the machine, test environment or production
     */
    public static String getBaseUrl(){
        String sEnvironment = getEnvironment().toLowerCase();
        String sBaseUrl = null;
        switch (sEnvironment){
            case "local":{
                sBaseUrl = getLocalBaseUrl();
                break;
            }
            case "test":{
                sBaseUrl = getTestBaseUrl();
                break;
            }
            case "prod":{
                sBaseUrl = getProdBaseUrl();
                break;
            }
            default:{
                Assert.fail(String.format("Cannot get BaseUrl! Environment %s is not recognised ", sEnvironment));
            }
        }
        return sBaseUrl;
    }

    /**
     * Method that returns the value of the property browser defined in the property file
     * @description - on which browser tests will be run
     *
     * @return {String} - the value of the browser property from the property file
     */
    public static String getBrowser(){
        return getProperty("browser");
    }

    /**
     * Method that returns the value of the property remote defined in the property file
     * @description - if tests will be run remotely or not
     *
     * @return {boolean} - the value of the remote property from the property file
     */
    public static boolean getRemote(){
        return Boolean.parseBoolean(getProperty("remote"));
    }

    /**
     * Method that returns the value of the headless property defined in the property file,
     * @description headless - Check if Web Browser is running without User Interface.
     * We can keep a track of it with the help of a Console or command-line interface.
     *
     * @return {boolean} - the value of the headless property from the property file
     */
    public static boolean getHeadless(){
        return Boolean.parseBoolean(getProperty("headless"));
    }

    /**
     * Method that returns the value of the locale property defined in the property file
     * which representing the default language of the application
     *
     * @return {String} - the value of locale property from the property file
     */
    public static String getLocale(){
        return getProperty("locale");
    }

    /**
     * Method that returns the value of the takeScreenshots property defined in the property file
     * @description if it is true - then it will take a screenshots of failed tests
     *
     * @return {boolean} - the value of the takeScreenshots property from the property file
     */
    public static boolean getTakeScreenshots(){
        return Boolean.parseBoolean(getProperty("takeScreenshot"));
    }

    /**
     * Method that returns the value of the property adminUserName defined in the property file
     *
     * @return {String} - the value of adminUserName property from the property file
     */
    public static String getAdminUserName(){
        return getProperty("adminUserName");
    }

    /**
     * Method that returns the value of the property adminPassword defined in the property file
     *
     * @return {String} - the value of the adminPassword property from the property file
     */
    public static String getAdminPassword(){
        return getProperty("adminPassword");
    }

    /**
     * Method that returns the value of the property endUserUsername defined in the property file
     *
     * @return {String} - the value of endUserUsername property from the property file
     */
    public static String getEndUserUsername(){
        return getProperty("endUserUsername");
    }

    /**
     * Method that returns the value of the property endUserPassword defined in the property file
     *
     * @return {String} - the value of the endUserPassword property from the property file
     */
    public static String getEndUserPassword(){
        return getProperty("endUserPassword");
    }

    /**
     * Method that returns the value of the property defaultSecretAnswer defined in the property file
     *
     * @return {String} - the value of defaultSecretAnswer property from the property file
     */
    public static String getDefaultSecretAnswer(){
        return getProperty("defaultSecretAnswer");
    }

    /**
     * Method that returns the value of the property defaultSecretQuestion defined in the property file
     *
     * @return {String} - the value of defaultSecretQuestion property from the property file
     */
    public static String getDefaultSecretQuestion(){
        return getProperty("defaultSecretQuestion");
    }

    /**
     * Method that returns the value of the property defaultAbout defined in the property file
     *
     * @return {String} - the value of defaultAbout property from the property file
     */
    public static String getDefaultAbout(){
        return getProperty("defaultAbout");
    }

    /**
     * Method that returns the value of the property defaultPassword defined in the property file
     *
     * @return {String} - the value of the defaultPassword property from the property file
     */
    public static String getDefaultPassword(){
        return getProperty("defaultPassword");
    }

    /**
     * Method that returns the value of the property rootUserName defined in the property file
     *
     * @return {String} - the value of the rootUserName property from the property file
     */
    public static String getRootUsername(){
        return getProperty("rootUsername");
    }

    /**
     * Method that returns the value of the property rootPassword defined in the property file
     *
     * @return {String} - the value of the rootPassword property from the property file
     */
    public static String getRootPassword(){
        return getProperty("rootPassword");
    }

    /**
     * Method that returns the value of the property hubUrl defined in the property file
     *
     * @return {String} - the value of the hubUrl property from the property file
     */
    public static String getHubUrl(){
        return getProperty("hubUrl");
    }

    /**
     * Method that returns the value of the property driversFolders which
     * is path to the folder where the drivers for different browsers are stored
     *
     * @return {String} - the path to the drivers folder
     */
    public static String getDriversFolder(){
        return getProperty("driversFolder");
    }


    /**
     * Method that returns the value of the property screenshotFolder which
     * is path to the folder where the screenshot would be stored
     *
     * @return {String} - the path to the screenshots folder
     */
    public static String getScreenshotsFolder(){
        return getProperty("screenshotsFolder");
    }

    /**
     * Method that returns the value of the property resourcesJavaFolder which
     * is path to the folder where the Java resources files are stored
     *
     * @return {String} - the path to the resources Java folder
     */
    public static String getResourcesJavaFolder(){
        return getProperty("resourcesJavaFolder");
    }

    /**
     * Method that returns the value of the property imagesFolder which
     * is path to the folder where the images resources files are stored
     *
     * @return {String} - the path to the images folder
     */
    public static String getImagesFolder(){
        return getProperty("imagesFolder");
    }

    /**
     * Method that returns the value of the property extentReportsFolder which
     * is path to the folder where the reports of the test results are stored
     *
     * @return {String} - the path to the report folder
     */
    public static String getExtentReportsFolder(){
        return getProperty("extentReportsFolder");
    }

    /**
     * Method that returns the value of the property local Data Source Url defined in the property file
     * which is link for an application's database on the local machine
     *
     * @return {String} - value of the property localDataSourceUrl from the property file
     */
    public static String getLocalDataSourceUrl(){
        return getProperty("localDataSourceUrl");
    }

    /**
     * Method that returns the value of the property test Data Source Url defined in the property file
     * which is link for an application's database on the test environment
     *
     * @return {String} - value of the property testDataSourceUrl from the property file
     */
    public static String getTestDataSourceUrl(){
        return getProperty("testDataSourceUrl");
    }

    /**
     * Method that returns the value of the property prod Data Source Url defined in the property file
     * which is link for an application's database on the production
     *
     * @return {String} - value of the property prodDataSourceUrl from the property file
     */
    public static String getProdDataSourceUrl(){
        return getProperty("prodDataSourceUrl");
    }

    /**
     * Method that gets the database base URL based on the test environment (local, test or prod)
     * defined in the property file
     *
     * @return {String} - URL to an application's database on local the machine, test environment or production
     */
    public static String getDataSourceUrl(){
        String sEnvironment = getEnvironment().toLowerCase();
        String sDataSourceUrl = null;
        switch (sEnvironment){
            case "local":{
                sDataSourceUrl = getLocalDataSourceUrl();
                break;
            }
            case "test":{
                sDataSourceUrl = getTestDataSourceUrl();
                break;
            }
            case "prod":{
                sDataSourceUrl = getProdDataSourceUrl();
                break;
            }
            default:{
                Assert.fail(String.format("Cannot get Database Source URL! Environment %s is not recognised ", sEnvironment));
            }
        }
        return sDataSourceUrl;
    }

    /**
     * Method that returns the database Driver from property file
     * @description - In case we need this if communication with database
     * is not established just with jar
     *
     * @return {String} - the database Driver
     */
    public static String getDatabaseDriver(){
        return getProperty("databaseDriver");
    }

}
