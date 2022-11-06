package tests.users;

import annotations.Jira;
import data.TestNGGroups;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;

@Jira(jiraID = "JIRA0005")
@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.SANITY,TestNGGroups.USERS,
        TestNGGroups.DEMO})
public class VerifyUserDetails extends BaseTestClass {
    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private User user;
    private boolean bCreated = false;


    @BeforeMethod
    public void setUpTest(ITestContext testContext){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});

        user = User.createNewUniqueUser("UserDetails");
        RestApiUtils.postUser(user);
        bCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
        log.info(user);
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult){
        log.info(String.format("[END TEST] %s", sTestName));
        tearDown(driver,testResult);
        if(bCreated){
            cleanUp();
        }
    }

    /**
     * Test that verify that the details that are in the User
     * Details Dialog Box same as we created
     */
    @Test
    public void testVerifyUserDetails()  {

        log.debug(String.format("[START TEST] %s", sTestName));

        LoginPage loginPage = new LoginPage(driver).open();


        WelcomePage welcomePage = loginPage.login(user);
        UsersPage usersPage = welcomePage.clickUsersTab();
        // because it returns the new instance of User page
        // we again perform assignment
        usersPage = usersPage.search(user.getUsername());

        UserDetailsDialogBox userDetailsDialogBox = usersPage.clickUserDetailsIconInUsersTable(user.getUsername());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(userDetailsDialogBox.getUserUsernameText(),user.getUsername(),"User Username isn't correct!");
        softAssert.assertEquals(userDetailsDialogBox.getUserFirstNameText(),user.getFirstName(),"User First Name isn't correct!");
        softAssert.assertEquals(userDetailsDialogBox.getUserLastNameText(),user.getLastName(),"User Last Name isn't correct!");
        softAssert.assertEquals(userDetailsDialogBox.getUserAboutText(),user.getAbout(),"User About isn't correct!");

        softAssert.assertTrue(DateTimeUtils.compareDateTimes(userDetailsDialogBox.getUserParsedCreatedAtText(),
                user.getCreatedAt(),120) ,"User CreatedAt isn't correct!");
        softAssert.assertAll("Some User properties are not correct!");
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
