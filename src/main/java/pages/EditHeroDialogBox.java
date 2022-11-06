package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class EditHeroDialogBox extends BasePageCLass{

    private final String editHeroDialogBoxString = "//div[@id='editHeroModal']";

    //WebElements
    @FindBy(id="editHeroModal")
    private WebElement editHeroDialogBox;
    @FindBy(xpath="//h4[contains(@class,'modal-title')]")
    private WebElement editHeroDialogBoxTitle;
    @FindBy(xpath=editHeroDialogBoxString +"button[contains(@class,'btn-default')]")
    private WebElement closeButton;

    @FindBy(id="editHeroSubmitButton")
    private WebElement saveButton;

    @FindBy(id="editName")
    private WebElement editNameTextField;

    @FindBy(id="editLevel")
    private WebElement editLevelTextField;

    @FindBy(id="editType")
    private WebElement editHeroClassDropDownList;

    @FindBy(xpath="//form[@id='edit-hero-form']//input[@id='editName']/preceding-sibling::label")
    private WebElement editNameTextFieldLabel;

    @FindBy(xpath="//form[@id='edit-hero-form']//input[@id='editLevel']/preceding-sibling::label")
    private WebElement editLevelTextFieldLabel;

    @FindBy(xpath="//form[@id='edit-hero-form']//select[@id='editType']/preceding-sibling::label")
    private WebElement editClassFieldLabel;

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public EditHeroDialogBox(WebDriver driver) {
        super(driver);
    }

    /**
     * Method that checks if Edit Hero dialog box is open on the Heroes page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Edit Hero dialog box is visible on the Heroes page
     */
    private boolean isEditHeroDialogBoxOpened(int timeout){
        return isWebElementVisible(editHeroDialogBox, timeout);
    }

    /**
     * Method that checks if Edit Hero dialog box is closed on the Heroes page
     *
     * @param timeout {int} - timeout in seconds
     *
     * @return {boolean} - if Edit Hero dialog box is closed the Heroes page
     */
    private boolean isEditHeroDialogBoxClosed(int timeout){
        return isWebElementInvisible(editHeroDialogBox, timeout);
    }

    /**
     * Method that waits until Edit Hero dialog box is loaded and
     * checks if Edit Hero dialog box is open on the Heroes page
     *
     * @return {EditHeroDialogBox} - an instance of Edit Hero Dialog Box
     */
    public EditHeroDialogBox verifyEditHeroDialogBox(){
        log.debug("verifyEditHeroDialogBox()");
        waitUntilPageIsReady(Time.TIME_SHORTER);
        Assert.assertTrue(isEditHeroDialogBoxOpened(Time.TIME_SHORTER), "Edit Hero Dialog Box is not opened");
        return this;
    }

    /**
     * Method that checks when we are on Edit Hero Dialog Box,
     * the page title is displayed
     *
     * @return {boolean} - if Edit Hero Dialog Box title is displayed or not
     */
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed");
        return isWebElementDisplayed(editHeroDialogBoxTitle);
    }

    /**
     * Method that checks if the Edit Hero Dialog Box title is displayed on
     * Edit Hero Dialog Box and gets Edit Hero Dialog Box title
     *
     * @return {String} - Edit Hero Dialog Box title
     */
    public String getPageTitle(){
        log.debug("getPageTitle()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title is NOT displayed on Edit Hero Dialog Box");
        return getTextFromWebElement(editHeroDialogBoxTitle);
    }

    /**
     * Method that checks if the Close button is displayed on the Edit Hero dialog box
     *
     * @return {boolean} - if Close button is displayed or not
     */
    public boolean isCloseButtonDisplayed(){
        log.debug("isCloseButtonDisplayed");
        return isWebElementDisplayed(closeButton);
    }

    /**
     * Method that checks if the Close button is displayed,
     * gets the Close button title and returns text as String
     *
     * @return {String} - text that is being displayed on Close button
     */
    public String getCloseButtonTitle(){
        log.debug("getCloseButtonTitle()");
        Assert.assertTrue(isCloseButtonDisplayed(),
                "Close button is NOT displayed on the Edit Hero dialog box!");
        return getValueFromWebElement(closeButton);
    }

    /**
     * Method that checks if the Close button is displayed on the Edit Hero dialog box,
     * clicks on the Close button, checks if Edit Hero dialog box is closed
     */
    private void clickCloseButton(){
        Assert.assertTrue(isCloseButtonDisplayed(),
                "Close Button is NOT displayed on the Edit Hero Dialog Box!");
        clickOnWebElement(closeButton);
        Assert.assertTrue(isEditHeroDialogBoxClosed(Time.TIME_SHORTER), "Edit Hero Dialog Box is not closed");
    }

    /**
     * Method that checks if the Close button is displayed on the Edit Hero dialog box,
     * clicks on the Close button, checks if Edit Hero dialog box is closed, and returns
     * an instance of the Heroes Page
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage clickOnCloseButtonToHeroesPage() {
        log.debug("clickOnCloseButtonToHeroesPage()");
        clickCloseButton();
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    /**
     * Method that checks if the Save button is displayed on the Edit Hero dialog box
     *
     * @return {boolean} - if Save button is displayed or not
     */
    public boolean isSaveButtonDisplayed(){
        log.debug("isSaveButtonDisplayed");
        return isWebElementDisplayed(saveButton);
    }

    /**
     * Method that checks if the Save button is displayed,
     * gets the Save button title and returns text as String
     *
     * @return {String} - text that is being displayed on Save button
     */
    public String getSaveButtonTitle(){
        log.debug("getSaveButtonTitle()");
        Assert.assertTrue(isSaveButtonDisplayed(),
                "Save button is NOT displayed on the Edit Hero dialog box!");
        return getValueFromWebElement(saveButton);
    }

    /**
     * Method that checks if the Save button is displayed on the Edit Hero dialog box,
     * clicks on the Save button, checks if Edit Hero dialog box is closed
     */
    private void clickSaveButton(){
        Assert.assertTrue(isSaveButtonDisplayed(),
                "Save button is NOT displayed on the Edit Hero Dialog Box!");
        clickOnWebElement(saveButton);
        Assert.assertTrue(isEditHeroDialogBoxClosed(Time.TIME_SHORTER), "Edit Hero Dialog Box is not closed");
    }

    /**
     * Method that checks if the Save button is displayed on the Edit Hero dialog box,
     * clicks on the Save button, checks if Edit Hero dialog box is closed, and returns
     * an instance of the HeroesPage
     *
     * @return {HeroesPage} - an instance of the HeroesPage
     */
    public HeroesPage clickOnSaveButtonToHeroesPage() {
        log.debug("clickOnSaveButtonToHeroesPage()");
        clickSaveButton();
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    /**
     * Method that checks if the Edit Name text field is Visible on
     * Edit Hero Dialog Box and returns true if it is Visible
     *
     * @return {boolean} - if the Edit Name text field is Visible or not
     */
    public boolean isEditNameTextFieldVisible(){
        log.debug("isEditNameTextFieldVisible");
        return isWebElementVisible(editNameTextField);
    }

    /**
     * Method that checks if the Edit Name text field is Visible. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Edited Name in the Edit Name field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sEditName {String} - the Edited Name that we want to type
     *
     * @return {EditHeroDialogBox} - an instance of the Edit Hero Dialog Box
     */
    public EditHeroDialogBox typeEditedName(String sEditName){
        log.debug(String.format("typeEditedName (%s)", sEditName));
        Assert.assertTrue(isEditNameTextFieldVisible(),
                "Edit Name Text Field is NOT Visible on the Edit Hero Dialog Box!");
        editNameTextField.getText();
        clearAndTypeTextToWebElement(editNameTextField, sEditName);
        return this;
    }

    /**
     * Method that checks if the Edit Name text field is Visible and returns
     * the text entered as the Edited Name in the Edit Name text field
     *
     * @return {String} - the Edited Name typed in the text field
     */
    public String getEditedName(){
        log.debug("getEditedName()");
        Assert.assertTrue(isEditNameTextFieldVisible(),
                "Edit Name Text Field is NOT Visible on the Edit Hero Dialog Box!");
        return getValueFromWebElement(editNameTextField);
    }

    /**
     * Method that checks when we are on Edit Hero Dialog Box,
     * the Edit Name Text Field Label is visible
     *
     * @return {boolean} - if Edit Label is visible on Edit Name Text Field or not
     */
    public boolean isEditNameTextFieldLabelVisible(){
        log.debug("isEditNameTextFieldLabelVisible");
        return isWebElementVisible(editNameTextFieldLabel);
    }

    /**
     * Method that checks if Edit Name Text Field Label is visible on
     * Edit Hero Dialog Box and gets Edit Name Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getEditNameTextFieldLabel(){
        log.debug("getEditNameTextFieldLabel()");
        Assert.assertTrue(isEditNameTextFieldLabelVisible(),"Edit Name Label is NOT visible on Edit Hero Dialog Box");
        return getTextFromWebElement(editNameTextFieldLabel);
    }

    /**
     * Method that checks if the Edit Level text field is displayed on
     * Edit Hero Dialog Box and returns true if it is displayed
     *
     * @return {boolean} - if the Edit Level text field is displayed or not
     */
    public boolean isEditLevelTextFieldDisplayed(){
        log.debug("isEditLevelTextFieldDisplayed");
        return isWebElementDisplayed(editLevelTextField);
    }

    /**
     * Method that checks if the Edit Level text field is displayed. If it is not true
     * Assert will fail the test here with message, if it is true then it will
     * clear the text box and enter the Edited Level in the Edit Level field,
     * @description - if there is no Assert and when the test failed, it breaks
     * with no such element exception, and this approach is better for triage
     *
     * @param sEditedLevel {String} - the Edited Level that we want to type
     *
     * @return {EditHeroDialogBox} - an instance of the EditHeroDialogBox
     */
    public EditHeroDialogBox typeEditedLevel(String sEditedLevel){
        log.debug(String.format("typeEditedLevel (%s)", sEditedLevel));
        Assert.assertTrue(isEditLevelTextFieldDisplayed(),
                "Edit Level Text Field is NOT displayed on the Edit Hero Dialog Box!");
        editLevelTextField.getText();
        clearAndTypeTextToWebElement(editLevelTextField, sEditedLevel);
        return this;
    }

    /**
     * Method that checks if the Edit Level text field is displayed and returns
     * the text chosen as the Edited Level in the Edit Level text field
     *
     * @return {String} - the Edited Level chosen in the text field
     */
    public String getEditedLevel(){
        log.debug("getEditedLevel()");
        Assert.assertTrue(isEditLevelTextFieldDisplayed(),
                "Edit Level Text Field is NOT displayed on the Edit Hero Dialog Box!");
        return getValueFromWebElement(editLevelTextField);
    }

    /**
     * Method that checks when we are on Edit Hero Dialog Box,
     * the Edit Level Text Field Label is visible
     *
     * @return {boolean} - if Edit Label is visible on the Edit Level Text Field or not
     */
    public boolean isEditLevelTextFieldLabelVisible(){
        log.debug("isEditLevelTextFieldLabelVisible");
        return isWebElementVisible(editLevelTextFieldLabel);
    }

    /**
     * Method that checks if Edit Level Text Field Label is visible on
     * Edit Hero Dialog Box and gets Edit Level Text Field label
     *
     * @return {String} - Text Field label
     */
    public String getEditLevelTextFieldLabel(){
        log.debug("getEditLevelTextFieldLabel()");
        Assert.assertTrue(isEditLevelTextFieldLabelVisible(),"Edit Level Label is NOT visible on Edit Hero Dialog Box");
        return getTextFromWebElement(editLevelTextFieldLabel);
    }

    /**
     * Method that checks when we are on Edit Hero Dialog Box,
     * the Edit Class Field Label is visible
     *
     * @return {boolean} - if Label is visible on Edit Class Field or not
     */
    public boolean isEditClassFieldLabelVisible(){
        log.debug("isEditClassFieldLabelVisible");
        return isWebElementVisible(editClassFieldLabel);
    }

    /**
     * Method that checks if Edit Class Field Label is visible on
     * Edit Hero Dialog Box and gets Edit Class Field label
     *
     * @return {String} - Text Field label
     */
    public String getEditClassFieldLabel(){
        log.debug("getEditClassFieldLabel()");
        Assert.assertTrue(isEditClassFieldLabelVisible(),"Edit Class Label is NOT visible on Edit Hero Dialog Box");
        return getTextFromWebElement(editClassFieldLabel);
    }

    /**
     * Method that checks if Edit Hero class Drop Down list is Displayed on
     * Edit Hero Dialog Box
     *
     * @return {boolean} - if Edit Hero class is displayed on Edit Hero Dialog Box
     */
    public boolean isEditHeroClassDropDownListDisplayed(){
        log.debug("isEditHeroClassDropDownListDisplayed()");
        return isWebElementDisplayed(editHeroClassDropDownList);
    }

    /**
     * Method that returns text of Edit Hero class that is firstly selected by default
     * on Edit Hero Dialog Box
     *
     * @return {String} - text from Edit Hero class that is firstly selected by default
     */
    public String getSelectedEditHeroClass(){
        log.debug("getSelectedEditHeroClass()");
        Assert.assertTrue(isEditHeroClassDropDownListDisplayed(),"Edit Hero Class DropDown list is NOT displayed on Edit Hero Dialog Box!");
        return getFirstSelectedOptionOnWebElement(editHeroClassDropDownList);
    }

    /**
     * Method that check if Edit Hero class option is present in drop down list on
     * Edit Hero Dialog Box
     *
     * @param sEditHeroClass {String} - class to check if is present in drop down
     *
     * @return {boolean} - if class is present or not
     */
    public boolean isEditHeroClassOptionPresent(String sEditHeroClass){
        log.debug("isEditHeroClassOptionPresent()");
        Assert.assertTrue(isEditHeroClassDropDownListDisplayed(),"Edit Hero Class DropDown list is NOT displayed on Edit Hero Dialog Box!");
        return isOptionPresentOnWebElement(editHeroClassDropDownList,sEditHeroClass);
    }

    /**
     * Method that selects specified Edited Hero class and return an instance of
     * Edit Hero Dialog Box
     *
     * @param sEditedHeroClass {String} - class to select from drop down
     *
     * @return {EditHeroDialogBox} - an instance of Edit Hero Dialog Box
     */
    public EditHeroDialogBox selectEditedHeroClass(String sEditedHeroClass){
        log.debug("selectEditHeroClass() " + sEditedHeroClass );
        Assert.assertTrue(isEditHeroClassOptionPresent(sEditedHeroClass),"Edit Hero Class DropDown option " + sEditedHeroClass + " is NOT present on DropDown list!");
        selectOptionOnWebElement(editHeroClassDropDownList, sEditedHeroClass);
        return this;
    }
}
