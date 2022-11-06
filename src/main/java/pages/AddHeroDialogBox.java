package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AddHeroDialogBox extends BasePageCLass{

    private final String addHeroDialogBoxString = "//div[@id='addHeroModal']";

    @FindBy(id="addHeroModal")
    private WebElement addHeroDialogBox;
    @FindBy(xpath="//h4[@class='modal-title']")
    private WebElement addHeroDialogBoxTitle;

    @FindBy(xpath=addHeroDialogBoxString +"//button[contains(@class,'btn-default')]")
    private WebElement cancelButton;

    @FindBy(id="submitButton")
    private WebElement saveButton;

    @FindBy(id="name")
    private WebElement nameTextField;

    @FindBy(id="level")
    private WebElement levelTextField;

    @FindBy(id="type")
    private WebElement heroClassDropDownList;

    @FindBy(xpath="//div[@class='form-group']//input[@id='name']/preceding-sibling::label")
    private WebElement nameTextFieldLabel;

    @FindBy(xpath="//div[@class='form-group']//input[@id='level']/preceding-sibling::label")
    private WebElement levelTextFieldLabel;

    @FindBy(xpath="//div[@class='form-group']//select[@id='type']/preceding-sibling::label")
    private WebElement classFieldLabel;

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public AddHeroDialogBox(WebDriver driver) {
        super(driver);
        log.trace("new AddHeroDialogBox()");
    }

    /**
     * Method that checks if Add Hero dialog box is open on the Heroes page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Add Hero dialog box is visible on the Heroes page
     */
    public boolean isAddHeroDialogBoxOpened(int timeout){
        return isWebElementVisible(addHeroDialogBox, timeout);
    }

    /**
     * Method that checks if Add Hero dialog box is closed on the Heroes page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Add Hero dialog box is closed the Heroes page
     */
    public boolean isAddHeroDialogBoxClosed(int timeout){
        return isWebElementDisplayed(addHeroDialogBox, timeout);
    }

    /**
     * Method that wait until Add Hero dialog box is loaded and
     * checks if Add Hero dialog box is open on the Heroes page
     *
     * @return {AddHeroDialogBox} - an instance of AddHeroDialogBox
     */
    public AddHeroDialogBox verifyAddHeroDialogBox(){
        log.debug("verifyAddHeroDialogBox()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isAddHeroDialogBoxOpened(Time.TIME_SHORTER), "Add Hero Dialog Box is not opened");
        return this;

    }

    /**
     * Method that checks when we are on Add Hero Dialog Box,
     * the page title is displayed
     *
     * @return {boolean} - if Add Hero Dialog Box title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(addHeroDialogBoxTitle);
    }

    /**
     * Method that checks if the Add Hero Dialog Box title is displayed on
     * Add Hero Dialog Box and gets Add Hero Dialog Box title
     *
     * @return {String} - Add Hero Dialog Box title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on Add Hero Dialog Box");
        return getTextFromWebElement(addHeroDialogBoxTitle);
    }

    /**
     * Method that checks if the Cancel button is displayed on the Add Hero dialog box
     *
     * @return {boolean} - if Cancel button is displayed or not
     */
    public boolean isCancelButtonDisplayed(){
        log.debug("isCancelButtonDisplayed");
        return isWebElementDisplayed(cancelButton);
    }

    /**
     * Method that checks if the Cancel button is displayed,
     * gets the Cancel button title and return text as String
     *
     * @return {String} - text that is being displayed on Cancel button
     */
    public String getCancelButtonTitle(){
        log.debug("getCancelButtonTitle()");
        Assert.assertTrue(isCancelButtonDisplayed(),
                "Cancel button is NOT displayed on the Add Hero dialog box!");
        return getValueFromWebElement(cancelButton);
    }

    /**
     * Method that checks if the Cancel button is displayed on the Add Hero dialog box,
     * clicks on the Cancel button, checks if Add Hero dialog box is closed, and returns
     * an instance of the Heroes page
     *
     * @return {HeroPage} - an instance of the HeroesPage
     */
    public HeroesPage clickOnCancelButtonToHeroes() {
        log.debug("clickOnCancelButton()");
        Assert.assertTrue(isCancelButtonDisplayed(),
                "Cancel Button is NOT displayed on the Add Hero Dialog Box!");
        clickOnWebElement(cancelButton);
        Assert.assertTrue(isAddHeroDialogBoxClosed(Time.TIME_SHORTER), "Add Hero Dialog Box is not closed");
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    /**
     * Method that checks if the Save button is displayed on the Add Hero dialog box
     *
     * @return {boolean} - if Save button is displayed or not
     */
    public boolean isSaveButtonDisplayed(){
        log.debug("isSaveButtonDisplayed");
        return isWebElementDisplayed(saveButton);
    }

    /**
     * Method that checks if the Save button is enabled on the Add Hero dialog box
     *
     * @return {boolean} - if Save button is enabled or not
     */
    public boolean isSaveButtonEnabled(){
        log.debug("isSaveButtonEnabled");
        Assert.assertTrue(isSaveButtonDisplayed(), "Save Button is NOT displayed on Add Hero Dialog box!");
        return isWebElementEnabled(saveButton);
    }

    /**
     * Method that checks if the Save button is displayed,
     * gets the Save button title and return text as String
     *
     * @return {String} - text that is being displayed on Save button
     */
    public String getSaveButtonTitle(){
        log.debug("getSaveButtonTitle()");
        Assert.assertTrue(isSaveButtonDisplayed(),
                "Save button is NOT displayed on the Add Hero dialog box!");
        return getValueFromWebElement(saveButton);
    }

    /**
     * Method that checks if the Save button is enabled on the Add Hero dialog box,
     * clicks on the Save button, checks if Add Hero dialog box is closed, and returns
     * an instance of the Heroes page
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage clickOnSaveButton() {
        log.debug("clickOnSaveButton()");
        Assert.assertTrue(isSaveButtonEnabled(),
                "Save Button is NOT enabled on the Add Hero Dialog Box!");
        clickOnWebElement(saveButton);
        Assert.assertFalse(isAddHeroDialogBoxClosed(Time.TIME_SHORTER), "Add Hero Dialog Box is not closed");
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    /**
     * Method that checks if the Name text field is Visible on
     * Add Hero Dialog Box and returns true if it is Visible
     *
     * @return {boolean} - if the Name text field is Visible or not
     */
    public boolean isNameTextFieldVisible(){
        log.debug("isNameTextFieldVisible");
        return isWebElementVisible(nameTextField);
    }

    /**
     * Method that checks if the Name text field is Visible. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Name in the Name field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sName {String} - the Name that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddHeroDialogBox typeName(String sName){
        log.debug(String.format("typeName (%s)", sName));
        Assert.assertTrue(isNameTextFieldVisible(),
                "Name Text Field is NOT Visible on the Add Hero Dialog Box!");
        nameTextField.getText();
        clearAndTypeTextToWebElement(nameTextField, sName);
        return this;
    }

    /**
     * Method that checks if the Name text field is Visible and returns
     * the text entered as the Name in the Name text field
     *
     * @return {String} - the Name typed in the text field
     */
    public String getName(){
        log.debug("getName()");
        Assert.assertTrue(isNameTextFieldVisible(),
                "Name Text Field is NOT Visible on the Add Hero Dialog Box!");
        return getValueFromWebElement(nameTextField);
    }

    /**
     * Method that checks if the Level text field is displayed on
     * Add Hero Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Level text field is displayed or not
     */
    public boolean isLevelTextFieldDisplayed(){
        log.debug("isLevelTextFieldDisplayed");
        return isWebElementDisplayed(levelTextField);
    }

    /**
     * Method that checks if the Level text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Level in the Level field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sLevel {String} - the Level that we want to type
     *
     * @return {AddUserDialogBox} - an instance of the AddUserDialogBox
     */
    public AddHeroDialogBox typeLevel(String sLevel){
        log.debug(String.format("typeLevel (%s)", sLevel));
        Assert.assertTrue(isLevelTextFieldDisplayed(),
                "Level Text Field is NOT displayed on the Add Hero Dialog Box!");
        levelTextField.getText();
        clearAndTypeTextToWebElement(levelTextField, sLevel);
        return this;
    }

    /**
     * Method that checks if the Level text field is displayed and returns
     * the text entered as the Level in the Level text field
     *
     * @return {String} - the Level typed in the text field
     */
    public String getLevel(){
        log.debug("getLevel()");
        Assert.assertTrue(isLevelTextFieldDisplayed(),
                "Level Text Field is NOT displayed on the Add Hero Dialog Box!");
        return getValueFromWebElement(levelTextField);
    }

    /**
     * Method that checks when we are on Add Hero Dialog Box,
     * the Name Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Name Text Field or not
     */
    public boolean isNameTextFieldLabelVisible(){
        log.debug("isNameTextFieldLabelVisible");
        return isWebElementVisible(nameTextFieldLabel);
    }

    /**
     * Method that checks if Name Text Field Label is visible on
     * Add Hero Dialog Box and gets Name Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getNameTextFieldLabel(){
        log.debug("getNameTextFieldLabel()");
        Assert.assertTrue(isNameTextFieldLabelVisible(),"Name Label is NOT visible on Add Hero Dialog Box");
        return getTextFromWebElement(nameTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add Hero Dialog Box,
     * the Level Text Field Label is visible
     *
     * @return {boolean} - if Label is visible on Level Text Field or not
     */
    public boolean isLevelTextFieldLabelVisible(){
        log.debug("isLevelTextFieldLabelVisible");
        return isWebElementVisible(levelTextFieldLabel);
    }

    /**
     * Method that checks if Level Text Field Label is visible on
     * Add Hero Dialog Box and gets Level Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getLevelTextFieldLabel(){
        log.debug("getLevelTextFieldLabel()");
        Assert.assertTrue(isLevelTextFieldLabelVisible(),"Level Label is NOT visible on Add Hero Dialog Box");
        return getTextFromWebElement(levelTextFieldLabel);
    }

    /**
     * Method that checks when we are on Add Hero Dialog Box,
     * the Class Field Label is visible
     *
     * @return {boolean} - if Label is visible on Class Field or not
     */
    public boolean isClassFieldLabelVisible(){
        log.debug("isClassFieldLabelVisible");
        return isWebElementVisible(classFieldLabel);
    }

    /**
     * Method that checks if Class Field Label is visible on
     * Add Hero Dialog Box and gets Class Field label
     *
     * @return {String} - Text Field label
     */
    public String getClassFieldLabel(){
        log.debug("getClassFieldLabel()");
        Assert.assertTrue(isClassFieldLabelVisible(),"Class Label is NOT visible on Add Hero Dialog Box");
        return getTextFromWebElement(classFieldLabel);
    }

    /**
     * Method that checks if Hero class Drop Down list is Displayed on
     * Add Hero Dialog Box
     *
     * @return {boolean} - if Hero class is displayed on Add Hero Dialog Box
     */
    public boolean isHeroClassDropDownListDisplayed(){
        log.debug("isHeroClassDropDownListDisplayed()");
        return isWebElementDisplayed(heroClassDropDownList);
    }

    /**
     * Method that returns text of Hero class that is firstly selected by default
     * on Add Hero Dialog Box
     *
     * @return {String} - text from Hero class that is firstly selected by default
     */
    public String getSelectedHeroClass(){
        log.debug("getSelectedHeroClass()");
        Assert.assertTrue(isHeroClassDropDownListDisplayed(),"Hero Class DropDown list is NOT displayed on Add Hero Dialog Box!");
        return getFirstSelectedOptionOnWebElement(heroClassDropDownList);
    }

    /**
     * Method that check if Hero class option is present in drop down list on
     * Add Hero Dialog Box
     *
     * @param sHeroClass {String} - class to check if is present in drop down
     *
     * @return {boolean} - if class is present or not
     */
    public boolean isHeroClassOptionPresent(String sHeroClass){
        log.debug("isHeroClassOptionPresent()");
        Assert.assertTrue(isHeroClassDropDownListDisplayed(),"Hero Class DropDown list is NOT displayed on Add Hero Dialog Box!");
        return isOptionPresentOnWebElement(heroClassDropDownList,sHeroClass);
    }

    /**
     * Method that selects specified Hero class and return an instance of
     * Add Hero Dialog Box
     *
     * @param sHeroClass {String} - class to select from drop down
     *
     * @return {AddHeroDialogBox} - an instance of AddHeroDialogBox
     */
    public AddHeroDialogBox selectedHeroClass(String sHeroClass){
        log.debug("selectedHeroClass() " + sHeroClass );
        Assert.assertTrue(isHeroClassOptionPresent(sHeroClass),"Hero Class DropDown option " + sHeroClass + " is NOT present on DropDown list!");
        selectOptionOnWebElement(heroClassDropDownList, sHeroClass);
        return this;
    }

}
