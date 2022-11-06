package pages;

import data.Time;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import utils.JavaScriptUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.WebDriverUtils;
import java.time.Duration;
import java.util.List;

public abstract class BasePageCLass extends LoggerUtils {

    protected WebDriver driver;

    private final By pageLocator = By.tagName("body");

    /**
     * A constructor which is used by other (children) classes
     * and initialize PageFactory
     *
     * @param driver {WebDriver} - WebDriver
     */
    public BasePageCLass(WebDriver driver) {
        // Because of parallelization every test needs to have his own driver instance
        Assert.assertFalse(WebDriverUtils.hasDriverQuit(driver),
                "Driver instance has quit!");
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    /**
     * Method that uses a base URL defined in common.properties
     * and a String parameter and return the full link for a page.
     * As a String parameter, can be passed one of the variables
     * defined in PageUrlPaths class
     *
     * @param sPath {String} - String that is being added to base URL
     *              for a specific page e.g. "/login"
     *
     * @return {String} - full link for a specific page
     */
    protected String getPageUrl(String sPath) {
        log.trace(String.format("getPageUrl( %s )", sPath));
        return PropertiesUtils.getBaseUrl() + sPath;
    }

    /**
     * Method that returns current URL
     *
     * @return {String} - current URL
     */
    protected String getCurrentUrl() {
        log.trace("getCurrentUrl()");
        return driver.getCurrentUrl();
    }

    /**
     * Method that checks if URL has changed or if URL contains a specified
     * String that is being passed as a parameter, and is waiting for that
     * change until a timeout has expired
     *
     * @param url {String} - part of some URL
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if URL contains String or not
     */
    protected boolean waitForUrlChange(String url, int timeout) {
        log.trace(String.format("waitForUrlChange: %s, %s", url, timeout));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        // mark variable and right click, Show Action Content if
        // we want to see which type is some variable
        return wait.until(ExpectedConditions.urlContains(url));
    }

    /**
     * Method that opens URL which is being passed as a String parameter
     *
     * @param url {String} - URL we want to open
     */
    protected void openUrl(String url){
        log.trace(String.format("getPageUrl( %s )", url));
        driver.get(url);
    }

    /**
     * Method that checks if URL has exact String that is being passed as
     * a parameter and is waiting until timeout has expired
     *
     * @param url {String} - URL that we want to check
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if URL is the same as String which is passed or not
     */
    protected boolean waitForUrlChangeToExactUrl(String url, int timeout) {
        log.trace(String.format("waitForUrlChange: %s, %s", url, timeout));
        WebDriverWait wait = new WebDriverWait((driver), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Method that checks if page loading status has been completed and
     * is waiting for that status until timeout has expired
     *
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if page loading has been completed or not
     */
    protected boolean waitUntilPageIsReady(int timeout) {
        log.trace(String.format("waitUntilPageIsReady: %d", timeout));
        WebDriverWait wait = new WebDriverWait((driver), Duration.ofSeconds(timeout));
        // wait until some condition is true or false is not implemented
        // this is why JavascriptExecutor was implemented
        // casting driver into JS executor
        // JavascriptExecutor js = (JavascriptExecutor) this.driver;
        // boolean result = js.executeScript("return document.readyState").equals("complete");
        // new wait faction that we created
        return wait.until(driver -> ((JavascriptExecutor) driver).executeScript
                ("return document.readyState").equals("complete"));
    }

    /**
     * Method that returns a WebElement based on
     * a locator that is being passed as a parameter
     *
     * @param locator {locator} - locator of the WebElement
     *
     * @return {WebElement} - WebElement
     */
    protected WebElement getWebElement(By locator) {
        log.trace(String.format("getWebElement (%s)", locator));
        return driver.findElement(locator);
    }

    /**
     * Method that returns a WebElement based on a locator that
     * is being passed as a parameter and waits for that element
     * to be present until a timeout has expired
     *
     * @param locator - locator of the WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {WebElement} - WebElement
     */
    protected WebElement getWebElement(By locator, int timeout) {
        log.trace(String.format("getWebElement (%s, wait for the element to be present for: %s)", locator, timeout));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Method that returns the WebElement based on a locator that
     * is being passed as parameter and wait for that element
     * to be present until timeout has expired
     *
     * @param locator - locator of the WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     * @param pollingTime {int} - the frequency (seconds) to check the success or
     *                    failure of a specified condition before throwing
     *                    an “ElementNotVisibleException” exception
     *
     * @return {WebElement} - WebElement
     */
    protected WebElement getWebElement(By locator, int timeout, int pollingTime) {
        log.trace(String.format("getWebElement (%s, wait for element " +
                "to be present for: %s, polling time:%s)", locator, timeout, pollingTime));
        // Introducing use of a fluent wait
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class);
        return wait.until(driver -> driver.findElement(locator));
    }

    /**
     * Method that gets a WebElement that is nested inside the other element
     *
     * @param element {WebElement} - the WebElement that contains an element we want to get
     * @param locator - locator of the WebElement that we want
     *
     * @return {WebElement} - the WebElement that we want
     */
    protected WebElement getNestedWebElement(WebElement element, By locator){
        log.trace(String.format("getNestedWebElement() (%s, %s)", element, locator));
        return element.findElement(locator);
    }

    /**
     * Method that gets a WebElement that is nested inside the other element
     * and wait for that element to be present
     *
     * @param element {WebElement} - the WebElement that contains an element we want to get
     * @param locator - locator of the WebElement that we want
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {WebElement} - the WebElement that we want
     */
    protected WebElement getNestedWebElement(WebElement element, By locator, int timeout) {
        log.trace(String.format("getNestedWebElement (%s, wait for element " +
                "to be present for: %s, locator: %s)", locator, timeout, locator));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element,locator));
    }

    /**
     * Method that gets WebElements inside WebElement and returns a list
     * of WebElements
     *
     * @param element {WebElement} - the WebElement that contains the elements we want to get
     * @param locator - locator of the WebElements
     *
     * @return {List} - List of WebElements
     */
    protected List<WebElement> getNestedWebElements(WebElement element, By locator){
        log.trace(String.format("getNestedWebElements() (%s, %s)", element, locator));
        return element.findElements(locator);
    }

    /**
     * Method that returns a WebElement after checking
     * if that element is clickable or not
     *
     * @param element {WebElement} - the WebElement that we want to check
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {WebElement} - WebElement
     */
    protected WebElement  waitForWebElementToBeClickable(WebElement element,int timeout){
        log.trace(String.format("waitForWebElementToBeClickable (%s, wait for element to " +
                                 "be clickable for: %s)", element, timeout));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));

    }

    /**
     * Method that waits for WebElement to be visible by specifying element locator
     * and returns that element if it was visible on the page; for display negation
     * can be used !isWebElementDisplayed but !isWebElementVisible negation can't be used
     *
     * @param locator - locator of the WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {WebElement} - WebElement
     */
    protected WebElement waitForWebElementToBeVisible(By locator, int timeout){
        log.trace(String.format("waitForWebElementToBeVisible %s, for: %s", locator,timeout));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Method that waits for WebElement to be visible by specifying the WebElement
     * and returns that element if it was visible on the page; for display negation
     * CAN be used !isWebElementDisplayed but !isWebElementVisible negation can't be used
     *
     * @param element {WebElement} - WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {WebElement} - WebElement
     */
    protected WebElement waitForWebElementToBeVisible(WebElement element, int timeout){
        log.trace(String.format("waitForWebElementToBeVisible %s, for: %s", element,timeout));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * The method that returns text from the first selected option in the dropdown
     *
     * @param element {WebElement} - element from which we want to get text of
     *                               selected option
     * @return {String} - text from selected option
     */
    protected String getFirstSelectedOptionOnWebElement(WebElement element){
        log.trace(String.format("getFirstSelectedOptionOnWebElement %s", element));
        Select options = new Select(element);
        WebElement selectedOption = options.getFirstSelectedOption();
        return selectedOption.getText();
    }

    /**
     * Method that check if specific option is present in the Drop-down menu, and
     * return true if it is present and otherwise false
     *
     * @param element {WebElement} - element to check selected option
     * @param option {String} - option ti check if is present
     *
     * @return {boolean} - if option present or not
     */
    protected boolean isOptionPresentOnWebElement(WebElement element, String option){
        log.trace(String.format("isOptionPresentOnWebElement %s, option: %s", element, option));
        Select options = new Select(element);
        List<WebElement> listOptions = options.getOptions();
        for(WebElement e: listOptions){
            if(getValueFromWebElement(e).equals(option)){
                    return true;
            }
        }
        return false;
    }

    /**
     * Method that select option from drop-down menu
     *
     * @param element {WebElement} - element in which we want to select option
     * @param option {String} - option ti select
     */
    protected void selectOptionOnWebElement(WebElement element, String option){
        log.trace(String.format("selectOptionOnWebElement %s, option: %s", element, option));
        Select options = new Select(element);
        options.selectByValue(option);
    }


    /**
     * Method that returns a WebElement after checking
     * if that element is selected or not
     *
     * @param element {WebElement} - the WebElement that we want to select
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {WebElement} - WebElement
     */
    protected boolean  waitForWebElementToBeSelected(WebElement element,int timeout){
        log.trace(String.format("waitForWebElementToBeSelected (%s, wait for element to " +
                "be clickable for: %s)", element, timeout));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeSelected(element));

    }

    /**
     * Method that tries to wait for a WebElement to be selected by specifying
     * the WebElement and if it can't then catch an Exception and return false
     *
     * @param element {WebElement} - WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean isWebElementSelected(WebElement element, int timeout){
        log.trace(String.format("isWebElementSelected (%s, %s)", element, timeout));
        try {
            return waitForWebElementToBeSelected(element,timeout);
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Method that tries ti check if a WebElement is selected by specifying
     * the WebElement and if it isn't then catch an Exception and return false
     *
     * @param element {WebElement} - WebElement
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean isWebElementSelected(WebElement element){
        log.trace(String.format("isWebElementSelected (%s)", element));
        try {
            return element.isSelected();
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Method that waits for WebElement to be invisible by specifying locator of the element
     * and returns boolean if it was invisible on the page or not
     *
     * @param locator - locator of the WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean waitForWebElementToBeInvisible(By locator, int timeout){
        log.trace(String.format("waitForWebElementToBeInvisible %s, for: %s", locator,timeout));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Method that waits for WebElement to be invisible by specifying the WebElement
     * and returns boolean if it was invisible on the page or not
     *
     * @param element {WebElement} - WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean waitForWebElementToBeInvisible(WebElement element, int timeout){
        log.trace(String.format("waitForWebElementToBeInvisible %s, for: %s", element,timeout));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Method that waits for a default time on a WebElement to be visible by specifying
     * WebElement locator and returns boolean if it was visible on the page or not
     *
     * @param locator - locator of the WebElement
     *
     * @return {boolean} - if WebElement was visible on the page or not
     */
    protected boolean isWebElementVisible(By locator){
        return  isWebElementVisible(locator, Time.TIME_SHORTER);
    }

    /**
     * Method that tries to wait for a WebElement to be visible by specifying the
     * WebElement locator and if it can't then catch an Exception and return false
     *
     * @param locator - locator of the WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if WebElement was visible on the page or not
     */
    protected boolean isWebElementVisible(By locator, int timeout){
        log.trace(String.format("isWebElementVisible %s, for: %s", locator,timeout));

        try {
            waitForWebElementToBeVisible(locator,timeout);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Method that waits for a default time on a WebElement to be visible by specifying
     * the WebElement and returns boolean if it was visible on the page or not
     *
     * @param element {WebElement} - WebElement
     *
     * @return {boolean} - if WebElement was visible on the page or not
     */
    protected boolean isWebElementVisible(WebElement element){
        return  isWebElementVisible(element, Time.TIME_SHORTER);
    }

    /**
     * Method that tries to wait for a WebElement to be visible by specifying
     * the WebElement and if it can't then catch an Exception and return false
     *
     * @param element {WebElement} - WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean isWebElementVisible(WebElement element, int timeout){
        log.trace(String.format("isWebElementVisible %s, for: %s", element,timeout));

        try {
            waitForWebElementToBeVisible(element,timeout);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Method that waits for a default time on a WebElement to be invisible by specifying
     * WebElement locator and returns boolean if it was invisible on the page or not
     *
     * @param locator - locator of the WebElement
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean isWebElementInvisible(By locator){
        return  isWebElementInvisible(locator, Time.TIME_SHORTER);
    }

    /**
     * Method that tries to wait for a WebElement to be invisible by specifying
     * the WebElement and if it can't then catch an Exception and return false
     *
     * @param locator - locator of the WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean isWebElementInvisible(By locator, int timeout){
        log.trace(String.format("isWebElementInvisible %s, for: %s", locator,timeout));

        try {
            waitForWebElementToBeVisible(locator,timeout);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Method that waits for a default time on a WebElement to be visible by specifying
     * the WebElement and returns boolean if it was invisible on the page or not
     *
     * @param element {WebElement} - WebElement
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean isWebElementInvisible(WebElement element){
        return  isWebElementInvisible(element, Time.TIME_SHORTER);
    }

    /**
     * Method that tries to wait for a WebElement to be invisible by specifying
     * the WebElement and if it can't then catch an Exception and return false
     *
     * @param element {WebElement} - WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean isWebElementInvisible(WebElement element, int timeout){
        log.trace(String.format("isWebElementInvisible %s, for: %s", element,timeout));
        try {
            waitForWebElementToBeInvisible(element,timeout);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Method that checks if a WebElement is displayed or not by specifying the WebElement
     * locator. This method doesn't break if an element is not displayed, it returns boolean,
     * so we know where the problem occurred; for display negation can be used !isWebElementDisplayed
     * but !isWebElementVisible negation can't be used
     *
     * @param locator - locator of the WebElement
     *
     * @return {boolean} - if element is displayed or not
     */
    protected boolean isWebElementDisplayed(By locator){
        log.trace(String.format("isWebElementDisplayed %s", locator));
        // if element is not displayed catch will catch exception and will return false
        // this is ok for negative test case
        try {
            WebElement webElement = getWebElement(locator);
            return webElement.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Method that checks if a WebElement is displayed or not by specifying the WebElement
     * This method doesn't break if an element is not displayed, it returns boolean, so we
     * know where the problem occurred
     *
     * @param element {WebElement} - WebElement
     *
     * @return {boolean} - if element is displayed or not
     */
    protected boolean isWebElementDisplayed(WebElement element){
        log.trace(String.format("isWebElementDisplayed %s", element));
        // if element is not displayed catch will catch exception and will return false
        // this is ok for negative test case
        try {
            return element.isDisplayed();
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Method that checks if a WebElement is displayed, first wait for that
     * element default time, then check if element is displayed or not,
     * wait for specified time and return true or false depending on result
     * @description - if we need longer wait for an element to be displayed,
     * default implicit wait is modified
     *
     * @param element {WebElement} - WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if WebElement was displayed on the page or not
     */
    protected boolean isWebElementDisplayed(WebElement element, int timeout){
       log.trace(String.format("isWebElementDisplayed (%s, %s)", element, timeout));
       WebDriverUtils.setImplicitWait(driver,timeout);
       boolean bResult = isWebElementDisplayed(element);
       WebDriverUtils.setImplicitWait(driver,Time.IMPLICIT_TIMEOUT);
       return bResult;
    }

    /**
     * Method that checks if a nested WebElement is displayed or not by specifying
     * the WebElement that contains WebElement we want to check. This method doesn't
     * break if an element is not displayed, it returns boolean, so we
     * know where the problem occurred
     *
     * @param element {WebElement} - the WebElement that contains an element we want to check
     * @param locator - locator of the WebElement that we want
     *
     * @return {boolean} - if WebElement was displayed on the page or not
     */
    protected boolean isNestedWebElementDisplayed(WebElement element, By locator){
        log.trace(String.format("isNestedWebElementDisplayed (%s, %s)", element, locator));
        try {
            WebElement nestedElement = element.findElement(locator);
            return nestedElement.isDisplayed();
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    /**
     * Method that tries to wait for a WebElement to be clickable by specifying
     * the WebElement and if it can't then catch an Exception and return false
     *
     * @param element {WebElement} - WebElement
     * @param timeout {int} - a number of seconds that driver will wait
     *
     * @return {boolean} - if WebElement was clickable on the page or not
     */
    protected boolean isWebElementEnabled(WebElement element, int timeout){
        log.trace(String.format("isWebElementEnabled (%s, %s)", element, timeout));
        try {
           waitForWebElementToBeClickable(element,timeout);
           return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Method that checks if a WebElement was enabled or not
     *
     * @param element {WebElement} - WebElement
     *
     * @return {boolean} - if WebElement was invisible on the page or not
     */
    protected boolean isWebElementEnabled(WebElement element){
        log.trace(String.format("isWebElementEnabled (%s)", element));
        return  element.isEnabled();
    }

    /**
     * Methods that enter the content in the text field
     *
     * @param element {WebElement} - text field in which we want to type something
     * @param text {String} - text that we want to type
     */
    protected void typeTextToWebElement(WebElement element, String text){
        log.trace(String.format("typeTextToWebElement (%s, %s)", element, text));
        element.sendKeys(text);
    }

    /**
     * Method that clears the text field if it is not empty
     * and then enter a content in the text field
     *
     * @param element {WebElement} - an element in which we want to type something
     * @param text {String} - text that we want to type
     */
    protected void clearAndTypeTextToWebElement(WebElement element, String text){
        log.trace(String.format("clearAndTypeTextToWebElement (%s, %s)", element, text));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Method that gets the text from a WebElement
     *
     * @param element {WebElement} - an element from whom we want text
     *
     * @return {String} - text from a WebElement
     */
    protected String getTextFromWebElement(WebElement element){
        log.trace(String.format("getTextFromWebElement (%s)", element));
        return element.getText();
    }

    /**
     * Method that gets content of an attribute from a WebElement
     *
     * @param element {WebElement} - an element from whom we want content of an attribute
     * @param attribute {String} - specific attribute
     *
     * @return {String} - content of an attribute
     */
    protected String getAttributeFromWebElement(WebElement element, String attribute){
        log.trace(String.format("getAttributeFromWebElement (%s, %s)", element, attribute));
        return element.getAttribute(attribute);
    }

    /**
     * Method that gets a value from a WebElement
     *
     * @param element {WebElement} - an element from whom we want a value to get
     *
     * @return {String} - value from WebElement
     */
    protected String getValueFromWebElement(WebElement element){
        log.trace(String.format("getValueFromWebElement (%s)", element));
        return getAttributeFromWebElement(element,"value");
    }

    /**
     * Method that gets a value from element using the JS Executor
     * when value is hidden, or it is hard to get
     *
     * @param element {WebElement} - an element from whom we want a value to get
     *
     * @return {String} - value from WebElement
     */
    protected String getValueFromWebElementJS(WebElement element){
        // when value is not seen, and it is not entered in the field dynamically
        // what can be done by selenium an ordinary user can, but what a JS does, it
        // does not mean that an ordinary user can
        // this is just a workaround for options that selenium do not have
        // and that scenario must be done manually e.g. drag and drop
        // to see if user really can do it
        log.trace(String.format("getValueFromWebElementJS (%s)", element));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].value",element);
    }

    /**
     * Method that clicks on a WebElement
     *
     * @param element {WebElement}- an element to click on
     */
    protected void clickOnWebElement(WebElement element){
        log.trace(String.format("clickOnWebElement (%s)", element));
        element.click();
    }

    /**
     * Method that waits for an element to be clickable and
     * then clicks on that WebElement
     *
     * @param element {WebElement} - an element to click on
     * @param timeout {int} - a number of seconds that driver will wait
     */
    protected void clickOnWebElement(WebElement element, int timeout){
        log.trace(String.format("clickOnWebElement (%s, and wait for an " +
                "element to click for: %s)", element, timeout));
        WebElement webElement = waitForWebElementToBeClickable(element, timeout);
        webElement.click();
    }

    /**
     * Method that clicks on a WebElement using the JS Executor
     *
     * @param element {WebElement} - an element from whom we want to get a value
     */
    protected void clickOnWebElementJS(WebElement element){
        log.trace(String.format("clickOnWebElement (%s)", element));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return arguments[0].value",element);
    }

    /**
     * Method that selects an option from the drop-down menu using locator of that
     * drop-down menu and a value of option that we want to select
     *
     * @param locator - locator of the WebElement
     * @param dropDownOption {String} - value of dropdown option that we want to select
     *
     */
    protected void selectWebElementFromDropDown(By locator, String dropDownOption){
        log.trace(String.format("selectWebElementFromDropDown (%s, %s)", locator, dropDownOption));
        Select selectElementFromDropdown = new Select(driver.findElement(locator));
        selectElementFromDropdown.selectByValue(dropDownOption);
    }

    /**
     * Method that selects an option from the drop-down menu using
     * drop-down menu WebElement and a value of option that we want to select
     *
     * @param element {WebElement} - an drop-down menu
     * @param dropDownOption {String} - value of dropdown option that we want to select
     */
    protected void selectWebElementFromDropDown(WebElement element, String dropDownOption){
        log.trace(String.format("selectWebElementFromDropDown (%s, %s)", element, dropDownOption));
        Select selectElementFromDropdown = new Select(element);
        selectElementFromDropdown.selectByValue(dropDownOption);
    }

    /**
     * Method that is for moving mouse to specified WebElement
     *
     * @param element {WebElement} - element to move mouse on
     */
    protected void moveMouseToWebElement(WebElement element){
        log.trace("moveMouseToWebElement() " + element);
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    /**
     * Method that is for dragging an image from one location to another
     * @see - selenium has a BUG, and it can't do drag and drop action when we use HTML5
     * https://bugs.chromium.org/p/chromedriver/issues/detail?id=2695
     * usual solution is to use JS drag_and_drop_helper.js (https://gist.github.com/rcorreia/2362544),
     * but it is not ideal approach because some action that can be done
     * with helper class can't be done by user the other way is to use other
     * class that apply the same approach as the user
     * https://github.com/Photonios/JS-DragAndDrop-Simulator
     *
     * @param source {WebElement} - element from which we are moving
     * @param destination  {WebElement} - element to move to
     */
    protected void doDragAndDrop(WebElement source, WebElement destination){
        log.trace(String.format("doDragAndDrop (%s, %s)", source, destination));
        Actions action = new Actions(driver);
        action.dragAndDrop(source,destination).perform();
    }

    /**
     * Method that is for dragging an image from one location to another
     * @see - selenium has a BUG, and it can't do drag and drop action when we use HTML5
     * https://bugs.chromium.org/p/chromedriver/issues/detail?id=2695
     * usual solution is to use JS drag_and_drop_helper.js (https://gist.github.com/rcorreia/2362544),
     * but it is not ideal approach because some action that can be done
     * with helper class can't be done by user the other way is to use other
     * class that apply the same approach as the user
     * https://github.com/Photonios/JS-DragAndDrop-Simulator
     *
     * @param source {String} - element from which we are moving
     * @param destination  {String} - element to move to
     */
    protected void doDragAndDropJS(String source, String destination){
        log.trace(String.format("doDragAndDropJS (%s, %s)", source, destination));
        JavaScriptUtils.simulateDragAndDrop(driver,source,destination);
    }

    /**
     * Method that clicks on point location
     *
     * @param location {Point} - Point location
     *
     */
    protected void clickOnLocation(Point location){
        log.trace(String.format("clickOnLocation() %s", location));

        // we need new element of the whole page because selenium can click only on that page and that is page body
        Actions action = new Actions(driver);
        WebElement page = getWebElement(pageLocator);

        // Coordinates of first point on the body of the page are (0,0)
        // and we need to click in the middle of the page, and then it moves us there
        // then we calculate based on that new point where it moves us which is
        // the middle of the page where we really want to click
        // (this method requires a trial and error technique in order to
        // find the best solution for our case - click on element when we provide that element location
        // because selenium doesn't have implemented method to click on location of the element)
        int xOffset = location.getX() - page.getSize().getWidth() / 2;
        int yOffset = location.getY() - page.getSize().getHeight() / 2;
        // Now it should click on that element location
        action.moveToElement(page,xOffset, yOffset).click().build().perform();
    }

    /**
     * Method that clicks on Point location using JS
     * (it cast driver into JS executor)
     *
     * @param location {Point} - Point location
     *
     */
    protected void clickOnLocationJS(Point location){
        log.trace(String.format("clickOnLocationJS() %s", location));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int x = location.getX();
        int y = location.getY();
        js.executeScript("element = document.elementFromPoint(" + x + ", " + y + "); element.click;");

    }

}
