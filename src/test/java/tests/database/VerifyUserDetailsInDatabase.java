package tests.database;

import objects.DatabaseUser;
import objects.User;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DatabaseUtils;
import utils.DateTimeUtils;
import utils.RestApiUtils;

import java.util.Date;

public class VerifyUserDetailsInDatabase extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private boolean bCreated = false;
    private User user;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        user = User.createNewUniqueUser("DataBaseTest");
        RestApiUtils.postUser(user);
        bCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        if(bCreated){
            cleanUp();
        }
    }

    /**
     * Test that creates the new user and verify that details
     * are correctly written in database, it checks:
         * Username
         * First Name
         * Last Name
         * Password
         * Email
         * About
         * Secret Question
         * Secret Answer
         * Date when user is created at
     */
    @Test
    public void testVerifyUserDetailInDatabase()  {

        String username = user.getUsername();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String password = user.getPassword();
        String email = user.getEmail();
        String about = user.getAbout();
        String secretQuestion = user.getSecretQuestion();
        String secretAnswer = user.getSecretAnswer();
        Date createdAt = user.getCreatedAt();


        DatabaseUser DBUser = DatabaseUtils.getDatabaseUser(user.getUsername());
        String dbUserUsername = DBUser.getUsername();
        String dbUserFirstName = DBUser.getFirstName();
        String dbUserLastName = DBUser.getLastName();
        String dbUserPassword = DBUser.getPassword();
        String dbUserEmail = DBUser.getEmail();
        String dbUserAbout = DBUser.getUsername();
        String dbUserSecretQuestion = DBUser.getUsername();
        String dbUserSecretAnswer = DBUser.getUsername();
        Date dbUserCreatedAt = DBUser.getCreated();


        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(username, dbUserUsername, "Username is NOT Correct!!!!");
        softAssert.assertEquals(firstName, dbUserFirstName, "First name is NOT Correct!!!!");
        softAssert.assertEquals(lastName, dbUserLastName, "Last name is NOT Correct!!!!");
        softAssert.assertEquals(password, dbUserPassword, "Password is NOT Correct!!!!");
        softAssert.assertEquals(email, dbUserEmail, "Email is NOT Correct!!!!");
        softAssert.assertEquals(about, dbUserAbout, "About is NOT Correct!!!!");
        softAssert.assertEquals(secretQuestion, dbUserSecretQuestion, "SecretQuestion is NOT Correct!!!!");
        softAssert.assertEquals(secretAnswer, dbUserSecretAnswer, "SecretAnswer is NOT Correct!!!!");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(createdAt,dbUserCreatedAt),"Created At Date is not correct!");

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
