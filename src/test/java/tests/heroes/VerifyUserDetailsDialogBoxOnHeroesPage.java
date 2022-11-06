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

public class VerifyUserDetailsDialogBoxOnHeroesPage extends BaseTestClass {
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
     * Test that checks if the User's details Dialog box on Heroes Page has the correct information for the Admin user
         * Login as admin
         * Go to the Heroes tab
         * Click on the User's link In the Heroes Table for Admin User
         * Check if User's Username Detail Text is correct
         * Check if User's First Name Detail Text is correct
         * Check if User's Last Name Detail Text is correct
         * Check if User's About Detail Text is correct
         * Check if User's Created at Text label is correct
         * Check if User's Hero's Username is correct
         * Check if User's Hero's First Name is correct
         * Check if User's Hero's Last Name is correct
         * Check if User's Hero's About is correct
         * Check if User's Hero's Created at is correct
     */
    @Test
    public void testVerifyUserDetailsDialogBoxOnHeroesPage()  {

        log.debug(String.format("[START TEST] %s", sTestName));

        LoginPage loginPage = new LoginPage(driver).open();
        WelcomePage welcomePage = loginPage.login(sUsername,sUserPassword);


        HeroesPage heroesPage = welcomePage.clickHeroesTab();
        heroesPage.searchHero("test");
        UserDetailsDialogBox userDetailsDialogBox = heroesPage.clickUserLinkInHeroesTable("test");
        SoftAssert softAssert1 = new SoftAssert();
        softAssert1.assertEquals(userDetailsDialogBox.getUserUsernameDetailText(),"Username:","Username Detail isn't correct!");
        softAssert1.assertEquals(userDetailsDialogBox.getUserFirstNameDetailText(),"First Name:","First Name Detail isn't correct!");
        softAssert1.assertEquals(userDetailsDialogBox.getUserLastNameDetailText(),"Last Name:","Last Name Detail isn't correct!");
        softAssert1.assertEquals(userDetailsDialogBox.getUserAboutDetailText(),"About:","About Detail isn't correct!");
        softAssert1.assertEquals(userDetailsDialogBox.getUserCreatedAtDetailText(),"Created at:","Created At Detail isn't correct!");
        softAssert1.assertAll("Some Details properties are not correct!");

        SoftAssert softAssert2 = new SoftAssert();
        softAssert2.assertEquals(userDetailsDialogBox.getUserUsernameText(),"admin","User Username isn't correct!");
        softAssert2.assertEquals(userDetailsDialogBox.getUserFirstNameText(),"Test","User First Name isn't correct!");
        softAssert2.assertEquals(userDetailsDialogBox.getUserLastNameText(),"Admin","User Last Name isn't correct!");
        softAssert2.assertEquals(userDetailsDialogBox.getUserAboutText(),"About Me Text","User About isn't correct!");
        softAssert2.assertEquals(userDetailsDialogBox.getUserCreatedAtText(),"10.10.2017. 10:45","User CreatedAt isn't correct!");
        softAssert2.assertAll("Some hero properties are not correct!");
    }
}
