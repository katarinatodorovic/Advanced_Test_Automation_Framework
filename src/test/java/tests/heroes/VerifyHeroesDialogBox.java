package tests.heroes;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.BaseTestClass;
import utils.PropertiesUtils;

public class VerifyHeroesDialogBox extends BaseTestClass {
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
     * Test that verify if the Heroes Dialog Box have the labels as expected
         * Go to the Login page
         * Log as admin
         * Click on the Heroes tab
         * Click on the Add New Hero button
         * Check if Name label is correct
         * Check if Level label is correct
         * Check if Class label is correct
     */
    @Test
    public void testVerifyHeroesDialogBox()  {

        log.debug(String.format("[START TEST] %s", sTestName));

        LoginPage loginPage = new LoginPage(driver).open();


        WelcomePage welcomePage = loginPage.login(sAdminUserName,sAdminPassword);

        HeroesPage heroesPage = welcomePage.clickHeroesTab();
        AddHeroDialogBox addHeroDialogBox = heroesPage.clickAddNewHeroButton();

        String name = addHeroDialogBox.getNameTextFieldLabel();
        String level = addHeroDialogBox.getLevelTextFieldLabel();
        String classLabel = addHeroDialogBox.getClassFieldLabel();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(name, "Name");
        softAssert.assertEquals(level, "Level");
        softAssert.assertEquals(classLabel, "Class");
        softAssert.assertAll("Some labels are wrong");


    }
}
