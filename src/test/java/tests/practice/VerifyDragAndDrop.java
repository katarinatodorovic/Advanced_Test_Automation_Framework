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

@Jira(jiraID = "JIRA0012")
@Test(groups = {TestNGGroups.REGRESSION,TestNGGroups.MOUSE})
public class VerifyDragAndDrop extends BaseTestClass {

    private String sTestName = this.getClass().getName();
    private WebDriver driver;
    private User user;
    private boolean bCreated = false;


    @BeforeMethod
    public void setUpTest(ITestContext testContext){
        log.info(String.format("[SETUP TEST] %s", sTestName));
        driver = setUpDriver();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});

        user = User.createNewUniqueUser("DragAndDrop");
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
     * Test that verify drag and drop area on the Practice page
     * 1. Go to Login page, login with newly created user
     * 2. Click on the Practice Page tab
     * 3. Check if image is present in the drag area
     * 4. Check if image is NOT present in te drop area
     * 5. Check if drag and drop message "You have successfully performed drag&drop action!" is NOT present
     * 6. Move image
     * 7. Check if image is NOT present in the drag area
     * 8. Check if image is present in te drop area
     * 9. Check if drag and drop message "You have successfully performed drag&drop action!" is present
     * 10. Check if message text is correct
     */
    @Test
    public void testVerifyDragAndDrop()  {

        String sExpectedDragAndDropMessage = CommonStrings.getDragAndDropMessage();

        LoginPage loginPage = new LoginPage(driver).open();


        WelcomePage welcomePage = loginPage.login(user.getUsername(),user.getPassword());

        PracticePage practicePage = welcomePage.clickPracticeTab();

        Assert.assertTrue(practicePage.isImagePresentInDragArea(),"Draggable Image is NOT present in drag area before Drag and Drop!");
        Assert.assertFalse(practicePage.isImagePresentInDropArea(), "Draggable Image should NOT be present in drop area before Drag and Drop!");
        Assert.assertFalse(practicePage.isDragAndDropMessageDisplayed(), "Drag and Drop message should NOT be present before Drag and Drop!");

        // there is selenium bug for HTLM5 which is not yet fixed
        practicePage.dragAndDropImage();

        Assert.assertFalse(practicePage.isImagePresentInDragArea(),"Draggable Image should NOT be present in drag area after Drag and Drop!");
        Assert.assertTrue(practicePage.isImagePresentInDropArea(), "Draggable Image should be present in drop area after Drag and Drop!");
        Assert.assertTrue(practicePage.isDragAndDropMessageDisplayed(), "Drag and Drop message should be present after Drag and Drop!");

        String sDragAndDropMessage = practicePage.geDragAndDropMessageText();
        Assert.assertEquals(sDragAndDropMessage,sExpectedDragAndDropMessage, "Wrong Drag and Drop Message!");

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
