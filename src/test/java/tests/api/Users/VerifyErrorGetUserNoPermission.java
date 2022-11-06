package tests.api.Users;

import annotations.Jira;
import data.APICalls;
import data.CommonStrings;
import data.TestNGGroups;
import objects.ApiError;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;
import java.util.Date;

@Jira(jiraID = "JIRA0008")
@Test(groups = {TestNGGroups.API})
public class VerifyErrorGetUserNoPermission extends BaseTestClass {
    private final String testName = this.getClass().getName();
    private String username;
    private String sUserPassword;


    @BeforeMethod
    public void setUpTest(){
        log.info(String.format("[SETUP TEST] %s", testName));
        username = PropertiesUtils.getEndUserUsername();
        sUserPassword = PropertiesUtils.getEndUserPassword();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult){
        log.info(String.format("[END TEST] %s", testName));
    }

    /**
     * Test that verify that user with no permissions gets:
         * Status code: 403,
         * Error='Forbidden',
         * Exception='null',
         * Message='Access is denied'
         * Path='/api/users/findByUsername/user'
         * and that that date matches
     */
    @Test
    public void testVerifyErrorGetUserNoPermission()  {

         int iExpectedStatusCode = 403;
         String sExpectedError = CommonStrings.getApiErrorForbidden();
         String sExpectedMessage = CommonStrings.getApiMessageAccessDenied();
         String sExpectedPath = APICalls.createGetUserApiCall(username);

         ApiError error = RestApiUtils.getUserError(username,username,sUserPassword);
         Date date = DateTimeUtils.getCurrentDateTime();
         log.info(error);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getStatus(),iExpectedStatusCode,"Wrong Status Code !");
        softAssert.assertEquals(error.getError(),sExpectedError,"Wrong Error!");
        softAssert.assertEquals(error.getMessage(),sExpectedMessage,"Wrong Message!");
        softAssert.assertEquals(error.getPath(),sExpectedPath,"Wrong Path!");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(error.getTimestamp(), date,5), "Wrong Timestamp!");
        softAssert.assertAll("Wrong Error Response Details!");

    }
}
