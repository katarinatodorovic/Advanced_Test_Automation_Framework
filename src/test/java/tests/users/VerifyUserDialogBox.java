package tests.users;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.BaseTestClass;
import utils.PropertiesUtils;

public class VerifyUserDialogBox extends BaseTestClass {
    private final String sTestName = this.getClass().getName();
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
     * Test that verify that Add New User dialog box on
     * the User's page have correct labels
         * Go to Login page and login as admin user
         * Click on the User's tab
         * CLick on the Add New User button
         * Check if username is correct
         * Check if first name label is correct
         * Check if last name label is correct
         * Check if email label is correct
         * Check if about label is correct
         * Check if secret question label is correct
         * Check if secret answer label is correct
         * Check if password label is correct
         * Check if confirm password label is correct
     */
    @Test
    public void testVerifyUserDialogBox()  {

        log.debug(String.format("[START TEST] %s", sTestName));

        LoginPage loginPage = new LoginPage(driver).open();


        WelcomePage welcomePage = loginPage.login(sAdminUserName,sAdminPassword);

        UsersPage usersPage = welcomePage.clickUsersTab();
        AddUserDialogBox addUserDialogBox = usersPage.clickAddNewUserButton();


        String username = addUserDialogBox.getUsernameTextFieldLabel();
        String firstName = addUserDialogBox.getFirstNameTextFieldLabel();
        String lastName = addUserDialogBox.getLastNameTextFieldLabel();
        String email = addUserDialogBox.getEmailTextFieldLabel();
        String about = addUserDialogBox.getAboutTextFieldLabel();
        String secretQuestion = addUserDialogBox.getSecretQuestionTextFieldLabel();
        String secretAnswer = addUserDialogBox.getSecretAnswerTextFieldLabel();
        String password = addUserDialogBox.getPasswordTextFieldLabel();
        String confirmPassword = addUserDialogBox.getConfirmPasswordTextFieldLabel();

       SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(username, "Username");
        softAssert.assertEquals(firstName, "First Name");
        softAssert.assertEquals(lastName, "Last Name");
        softAssert.assertEquals(email, "Email");
        softAssert.assertEquals(about, "About");
        softAssert.assertEquals(secretQuestion, "Secret Question");
        softAssert.assertEquals(secretAnswer, "Secret Answer");
        softAssert.assertEquals(password, "Password");
        softAssert.assertEquals(confirmPassword, "Confirm Password");

        softAssert.assertAll("Some labels are wrong");

    }
}
