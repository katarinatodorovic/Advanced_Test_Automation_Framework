package tests.api.Users;

import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTestClass;
import utils.DatabaseUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;

import java.util.List;

public class VerifyApiDeleteUser extends BaseTestClass {
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
     * Test that verify Delete user API
         * Create new user
         * Delete user using Delete API
         * Get list of the all usernames from the database
         * Check if there is still user that we deleted using Delete API
     */
    @Test
    public void testVerifyApiDeleteUser()  {
        // it has to be lower letters but even
        // if we use all uppercase letters it will transform into lowercase letters
        User user1 = User.createNewUniqueUser("janko");
        RestApiUtils.postUser(user1);
        String username = user1.getUsername();
        RestApiUtils.deleteUser(user1.getUsername());
        List<String> allUsernamesInDatabase = DatabaseUtils.getAllUsernames();
        boolean isUserDeleted = true;
        for (String userName : allUsernamesInDatabase){
            if (userName.equals(username)){
                isUserDeleted = false;
            }
        }
        Assert.assertTrue(isUserDeleted, "User is NOT deleted using Delete API call!");
    }
}
