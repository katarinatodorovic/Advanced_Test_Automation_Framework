package tests.register;

import data.CommonStrings;
import data.TestNGGroups;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.RegisterPage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;

@Test(groups = {TestNGGroups.REGRESSION, TestNGGroups.LOGIN, TestNGGroups.REGISTER, TestNGGroups.BROKEN})
public class  SuccessfulRegister extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private User user;
    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        user = User.createNewUniqueUser("SuccessfulRegister");
        bCreated = true;
    }
    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        tearDown(driver,testResult);
        if(bCreated){
            cleanUp();
        }
    }
    /**
     * Test that check if we are successfully registered new user that we created in before method
         * Go to the Login page
         * Click on the Create new account link
         * Fill the Register page with user data we created in before method
         * Set current date and time as the Created at time
         * Check if we got successful register message
         * Get user from DB using GET API call
         * Compare the user we created from the data in the database
     */
    @Test
    public void testSuccessfulRegister(){

        String sExpectedRegisterSuccessMessage = CommonStrings.getRegisterSuccessMessage();


        log.info(String.format("[START TEST] %s", sTestName));
        LoginPage loginPage = new LoginPage(driver).open();
        log.info("Created USER: " + user);

        RegisterPage registerPage = loginPage.clickCreateAccountLink();
        loginPage = registerPage.registerUser(user);
        bCreated = true;
        user.setCreatedAt(DateTimeUtils.getCurrentDateTime());
        log.info("Created User" + user);

        String sMessage = loginPage.getSuccessMessage();
        Assert.assertEquals(sMessage, sExpectedRegisterSuccessMessage, "Wrong success message!!!");

        User saveUser = RestApiUtils.getUser(user.getUsername());
        System.out.println(saveUser);
        log.info("Saved USER: " + saveUser);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(saveUser.getEmail(), user.getEmail(), "Email is NOT Correct!!!!");
        softAssert.assertEquals(saveUser.getFirstName(), user.getFirstName(), "FirstName is NOT Correct!!!!");
        softAssert.assertEquals(saveUser.getLastName(), user.getLastName(), "LastName is NOT Correct!!!!");
        softAssert.assertEquals(saveUser.getAbout(), user.getAbout(), "About is NOT Correct!!!!");
        softAssert.assertEquals(saveUser.getSecretQuestion(), user.getSecretQuestion(), "SecretQuestion is NOT Correct!!!!");
        softAssert.assertEquals(saveUser.getSecretAnswer(), user.getSecretAnswer(), "SecretAnswer is NOT Correct!!!!");
        softAssert.assertEquals(saveUser.getHeroCount(), user.getHeroCount(), "HeroCount is NOT Correct!!!!");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(saveUser.getCreatedAt(),user.getCreatedAt()),"Created At Date is not correct!");
        softAssert.assertAll("Wrong User Details are saved in database for user: " + user.getUsername() );

        Assert.assertTrue(RestApiUtils.checkIfUserExist(user.getUsername()));
    }

    private void cleanUp(){
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(user.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Cleaning up failed! Message: " + e.getMessage());
        }
    }
}
