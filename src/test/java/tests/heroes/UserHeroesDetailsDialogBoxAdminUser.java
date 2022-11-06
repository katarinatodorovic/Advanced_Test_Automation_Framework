package tests.heroes;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.BaseTestClass;
import utils.PropertiesUtils;

public class UserHeroesDetailsDialogBoxAdminUser extends BaseTestClass {
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
     * Test that checks if User Heroes Details in User Heroes Dialog box for admin user are correct
         * Login as admin
         * Go to the Users tab
         * Click on the Hero Count link for admin user
         * Check if User Heroes Dialog Box Column Name is correct
         * Check if User Heroes Dialog Box Column Class is correct
         * Check if User Heroes Dialog Box Column Level is correct
         * Check if User Heroes Dialog Box Hero's Name is correct
         * Check if User Heroes Dialog Box Hero's Class is correct
         * Check if User Heroes Dialog Box Hero's Level is correct
     */
    @Test
    public void testUserHeroesDetailsDialogBoxAdminUser()  {

        log.debug(String.format("[START TEST] %s", sTestName));

        LoginPage loginPage = new LoginPage(driver).open();
        WelcomePage welcomePage = loginPage.login(sUsername,sUserPassword);


        UsersPage usersPage = welcomePage.clickUsersTab();
        log.info(usersPage.isUserPresentInUsersTable(sUsername));

        UserHeroesDialogBox userHeroesDialogBox = usersPage.clickHeroCountLinkInUsersTable(sUsername);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals( userHeroesDialogBox.getColumnNameText(), "Name", "The Column Name doesn't have correct displayed name!");
        softAssert.assertEquals( userHeroesDialogBox.getClassColumnText(), "Class", "The Column Class doesn't have correct displayed name!");
        softAssert.assertEquals( userHeroesDialogBox.getLevelColumnText(), "Level", "The Column Level doesn't have correct displayed name!");
        softAssert.assertEquals( userHeroesDialogBox.getHeroesNameText(), "test", "The Hero's Name isn't correctly displayed!");
        softAssert.assertEquals( userHeroesDialogBox.getHeroesClassText(), "Warrior", "The Hero's Class isn't correctly displayed!");
        softAssert.assertEquals(  userHeroesDialogBox.getHeroesLevelText(), "70", "The Hero's Level isn't correctly displayed!");

        softAssert.assertAll("Hero's details for Admin User are not displayed correctly!");
        usersPage = userHeroesDialogBox.clickOnCloseButton();

    }
}
