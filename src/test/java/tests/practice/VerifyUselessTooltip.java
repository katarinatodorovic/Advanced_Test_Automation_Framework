package tests.practice;

import annotations.Jira;
import data.CommonStrings;
import data.TestNGGroups;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PracticePage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.RestApiUtils;

@Jira(jiraID = "JIRA0011")
@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.MOUSE})
public class VerifyUselessTooltip extends BaseTestClass {

    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private User user;
    private boolean bCreated = false;


    @BeforeMethod
    public void setUpTest(ITestContext testContext){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});

        user = User.createNewUniqueUser("UselessTooltip");
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
     * Test that verifies useless tooltip
     *
     */
    @Test
    public void testVerifyUselessTooltip()  {

        String sExpectedUselessToolTipText = CommonStrings.getUselessTooltipText();

        LoginPage loginPage = new LoginPage(driver).open();


        WelcomePage welcomePage = loginPage.login(user.getUsername(),user.getPassword());

        PracticePage practicePage = welcomePage.clickPracticeTab();

        Assert.assertFalse(practicePage.isUselessTooltipDisplayed(), "Useless tooltip shouldn't be displayed before mouse hover!");
        String sUselessToolTipText = practicePage.getUselessTooltipText();

        Assert.assertEquals(sUselessToolTipText,sExpectedUselessToolTipText, "Wrong Useless Tooltip text!");


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
