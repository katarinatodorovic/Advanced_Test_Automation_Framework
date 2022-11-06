package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that can read any JS file. For this project, mainly it is used
 * for reading drag and drop JS file because selenium doesn't have proper
 * implementation of drag and drop action
 */
public class JavaScriptUtils extends LoggerUtils{

    private static final String dragAndDropFileName = "drag_and_drop.js";


    /**
     * Method for reading JS file
     *
     * @param jsFileName {String} - name of JS file that needs to be read
     *
     * @return {String} - file in form of a String
     */
    private static String loadJavaScriptFile(String jsFileName){
        String sFilePath = PropertiesUtils.getResourcesJavaFolder() + jsFileName;
        String sJavaScript = "";
        String line;

        try {
            BufferedReader input = new BufferedReader(new FileReader(sFilePath));
            // so we don't get out of memory by reading line by line, we use StringBuilder
            StringBuilder builder = new StringBuilder();
            while ((line = input.readLine()) != null){
                // we want lines to be separated by space and not by new line
                builder.append(line).append(" ");
            }
            sJavaScript = builder.toString();
        } catch (IOException e) {
            Assert.fail(String.format("Cannot load file from: %s location!Message: %s", sFilePath, e.getMessage()));
        }
        return sJavaScript;
    }

    /**
     * Method that load drag and drop JS file
     *
     * @return {String} - file in form of a String
     */
    private static String loadDragAndDropFile(){
        return loadJavaScriptFile(dragAndDropFileName);
    }

    /**
     * Method that simulate drag and drop action
     *
     * @param driver {WebDriver} - an instance of the WebDriver
     * @param sourceLocator {String} - locator of the element that we want to drag
     * @param destinationLocator {String} - locator of the element that we want to drop
     */
    public static void simulateDragAndDrop(WebDriver driver, String sourceLocator, String destinationLocator){
        log.trace("simulateDragAndDrop()");
        String sJavaScript = loadDragAndDropFile() + String.format("DndSimulator.simulate('%s', '%s');",sourceLocator,destinationLocator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(sJavaScript);
    }
}
