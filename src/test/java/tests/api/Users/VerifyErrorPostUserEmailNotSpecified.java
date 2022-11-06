package tests.api.Users;

import annotations.Jira;
import data.APICalls;
import data.CommonStrings;
import data.TestNGGroups;
import objects.ApiError;
import objects.User;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;
import java.util.Date;


@Jira(jiraID = "JIRA0010")
@Test(groups = {TestNGGroups.API})
public class VerifyErrorPostUserEmailNotSpecified extends BaseTestClass {
    private String sTestName = this.getClass().getName();

    private User user;
    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        user = User.createNewUniqueUser("PostUserNoEmail");
        bCreated = true;
        user.setClearEmail();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        if(bCreated){
            cleanUp();
        }
    }

    /**
     * Test that verify that we cannot create new user without an email address
     * and that:
         * Status code is: 500,
         * Error is: 'Internal Server Error',
         * Exception is: 'java.lang.IllegalArgumentException',
         * Message is: 'Email is not specified!',
         * Path is: '/api/users/add'
     */
    @Test
    public void testVerifyErrorPostUserEmailNotSpecified()  {

        int iExpectedStatusCode = 500;
        String sExpectedError = CommonStrings.getApiErrorInternalServerError();
        String sExpectedMessage = CommonStrings.getApiMessageEmailNotSpecified();
        String sExpectedException = CommonStrings.getApiIllegalArgumentException();
        String sExpectedPath = APICalls.createPostUserApiCall();

        ApiError error = RestApiUtils.postUserError(user);
        Date date = DateTimeUtils.getCurrentDateTime();
        log.info(error);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getStatus(),iExpectedStatusCode,"Wrong Status Code !");
        softAssert.assertEquals(error.getError(),sExpectedError,"Wrong Error!");
        softAssert.assertEquals(error.getMessage(),sExpectedMessage,"Wrong Message!");
        softAssert.assertEquals(error.getException(),sExpectedException,"Wrong Exception!");
        softAssert.assertEquals(error.getPath(),sExpectedPath,"Wrong Path!");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(error.getTimestamp(), date,5), "Wrong Timestamp!");
        softAssert.assertAll("Wrong Error Response Details!");

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
