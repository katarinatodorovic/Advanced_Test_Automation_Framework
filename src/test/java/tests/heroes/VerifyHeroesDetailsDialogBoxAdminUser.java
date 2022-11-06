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

public class VerifyHeroesDetailsDialogBoxAdminUser extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private String sUsername;
    private String sUserPassword;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        //It is better to login here because is better test to fail in setup
        //and our test will be skipped that means that problem is with setup and not with our test
        sUsername = PropertiesUtils.getAdminUserName();
        sUserPassword = PropertiesUtils.getAdminPassword();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        tearDown(driver,testResult);
    }


    /**
     * Test that checks if the Hero's details Dialog box shows the correct information for the specific user
         * Login as admin
         * Go to the Heroes tab
         * Click on the Hero's Details Icon In the Heroes Table for User "dastee"
         * Check if Name label is correct
         * Check if Class label is correct
         * Check if Level label is correct
         * Check if User label is correct
         * Check if Created at label is correct
         * Check if Hero's name is correct
         * Check if Hero's class is correct
         * Check if Hero's level is correct
         * Check if Hero's User is correct
         * Check if Hero's Created at is correct
     */
    @Test
    public void testVerifyHeroesDetailsDialogBoxAdminUser()  {

        log.debug(String.format("[START TEST] %s", sTestName));

        LoginPage loginPage = new LoginPage(driver).open();
        WelcomePage welcomePage = loginPage.login(sUsername,sUserPassword);


        HeroesPage heroesPage = welcomePage.clickHeroesTab();
        HeroDetailsDialogBox heroDetailsDialogBox = heroesPage.clickHeroDetailsIconInHeroesTable("dastee");
        SoftAssert softAssert1 = new SoftAssert();
        softAssert1.assertEquals(heroDetailsDialogBox.getHeroNameDetailText(),"Name:","Name Detail isn't correct!");
        softAssert1.assertEquals(heroDetailsDialogBox.getHeroClassDetailText(),"Class:","Class Detail isn't correct!");
        softAssert1.assertEquals(heroDetailsDialogBox.getHeroLevelDetailText(),"Level:","Level Detail isn't correct!");
        softAssert1.assertEquals(heroDetailsDialogBox.getHeroUserDetailText(),"User:","User Detail isn't correct!");
        softAssert1.assertEquals(heroDetailsDialogBox.getHeroCreatedAtDetailText(),"Created at:","CreatedAt Detail isn't correct!");
        softAssert1.assertAll("Some Details properties are not correct!");

        SoftAssert softAssert2 = new SoftAssert();
        softAssert2.assertEquals(heroDetailsDialogBox.getHeroNameText(),"dastee","Hero Name isn't correct!");
        softAssert2.assertEquals(heroDetailsDialogBox.getHeroClassText(),"Guardian","Hero Class isn't correct!");
        softAssert2.assertEquals(heroDetailsDialogBox.getHeroLevelText(),"12","Hero Level isn't correct!");
        softAssert2.assertEquals(heroDetailsDialogBox.getHeroUserText(),"admin","Hero User isn't correct!");
        softAssert2.assertEquals(heroDetailsDialogBox.getHeroCreatedAtText(),"27.06.2022. 00:38","Hero CreatedAt isn't correct!");
        softAssert2.assertAll("Some hero properties are not correct!");
    }
}
