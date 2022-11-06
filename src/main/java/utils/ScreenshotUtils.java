package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtils extends LoggerUtils{

    private static final String sScreenshotPath = System.getProperty("user.dir") + PropertiesUtils.getScreenshotsFolder();
    private static final String sImagesPath = PropertiesUtils.getImagesFolder();

    /**
     * Method that create and return screenshot path where screenshots are stored
     *
     * @return - full path to the screenshot that have been taken
     */
    private static String createScreenshotPath(String sScreenshotName){
        return sScreenshotPath + sScreenshotName + "_" +
                DateTimeUtils.getDateTimeStamp() + ".png";
    }

    /**
     * Method that creates screenshot path check if driver instance is still existing,
     * take screenshot, save screenshot as file, try to store that screenshot in
     * the destination directory, catch an error if screenshot couldn't be stored
     * and return screenshot name
     *
     * @param driver {WebDriver} - WebDriver
     * @param sTestName - name of test that is being executed
     *
     * @return {String} - screenshot name
     */
    public static String takeScreenshot(WebDriver driver, String sTestName){
        log.trace(String.format("takeScreenshot (%s)",sTestName));

        if(WebDriverUtils.hasDriverQuit(driver)){
            log.warn(String.format("Screenshot for test %s couldn't be taken! " +
                    "Driver instance has quit",sTestName));
             return null;
        }
        // This two lines are not in use, this is solution when we have multiple drivers
        // instances, but it doesn't give us any information about which instance
        // created particular screenshot
        // String sessionId = WebDriverUtils.getSessionID(driver).toString();
       // String screenshotName = sTestName + "." + sessionId;
        String pathToFile = createScreenshotPath(sTestName);

        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

        File destFile = new File(pathToFile);
        // We don't want our test to fail because couldn't manage to get a screenshot
        // that's why we catch exception
        try {
            FileUtils.copyFile(srcFile,destFile);
            log.info(String.format("Screenshot for test %s is successfully saved: %s",sTestName,pathToFile));
        }catch (IOException e){
            log.warn(String.format("Screenshot for test %s couldn't be saved in file %s. " +
                    "Message: %s",sTestName,pathToFile,e.getMessage()));
        }
            return pathToFile;
    }

    /**
     * Method that take screenshot (that later can be used as a reference image)
     * of the whole screen and store it into a file
     *
     * @param driver {WebDriver} - WebDriver
     *
     * @return - screenshot as BufferedImage
     */
    public static BufferedImage takeScreeShot(WebDriver driver){
        log.trace("takeScreenshot ()");
        // check if driver instance is still alive
        if (WebDriverUtils.hasDriverQuit(driver)){
            Assert.fail("Screenshot could not be taken! Driver instance has quit!");
        }
        // take a whole screen screenshot
        TakesScreenshot screenShot = ((TakesScreenshot) driver);
        BufferedImage fullScreen = null;

        // try to store taken image into file
        try {
            fullScreen = ImageIO.read(screenShot.getScreenshotAs(OutputType.FILE));
        } catch (IOException e) {
            Assert.fail("Screenshot could not be taken! Message: " + e.getMessage());
        }
        return fullScreen;
    }

    /**
     * Method that take screenshot of element provided as parameter
     * and returns a screenshot of that element
     *
     * @param driver {WebDriver} - WebDriver
     * @param element - element that we need to take screenshot of
     *
     * @return - screenshot as BufferedImage
     */

    //selenium returns coordinates of upper left corner
    // of browser and when it clicks, it clicks in the middle of window.
    // (0,0) are the coordinates of upper left corner of active window
    // and returns the size of image, no matter what is the size of our
    // browser window
    public static BufferedImage takeScreenShotOfWebElement(WebDriver driver, WebElement element){
        log.trace(String.format("takeScreeShotOfWebElement (%s)", element));
        // check if driver instance is still alive
        if (WebDriverUtils.hasDriverQuit(driver)){
            Assert.fail(String.format("Screenshot of WebElement %s could not be taken! Driver instance has quit!", element));
        }
        BufferedImage subImage = null;
        try {
            // take a whole screen screenshot
            BufferedImage fullScreen = takeScreeShot(driver);
            // get location of target element
            Point elementLocation = element.getLocation();
            // get target element dimension
            Dimension elementDimension = element.getSize();
            // return target image from whole screen image
            // selenium has a problem if scale and layout of our screen is not set ot 100% in display settings
            subImage = fullScreen.getSubimage(elementLocation.getX(), elementLocation.getY(),elementDimension.getWidth(), elementDimension.getHeight());
        } catch (Exception e) {
            Assert.fail(String.format("Screenshot of sub-image from full screen image of WebElement %s could not be taken!", element));
        }
        return subImage;
    }

    /**
     * Method that take screenshot of element using AShot
     * and returns a screenshot of that element (AShot checks if driver has quit)
     *
     * @param driver {WebDriver} - WebDriver
     * @param element - element that we need to take screenshot of
     *
     * @return - Screenshot
     */
    public static Screenshot takeScreenShotOfWebElementAS(WebDriver driver, WebElement element){
        log.trace(String.format("takeScreeShotOfWebElementAS (%s)", element));
        AShot shot = new AShot();
        return shot.takeScreenshot(driver,element);
    }

    /**
     * Method that save screenshot of element using AShot
     *
     * @param screenshot {Screenshot} - Screenshot
     * @param sPathToFile {String} - path where file would be saved
     *
     */
    public static void saveScreenShotOfWebElementAS(Screenshot screenshot, String sPathToFile){
        log.trace(String.format("saveScreenShotOfWebElementAS (%s)", sPathToFile));
        BufferedImage image = screenshot.getImage();
        saveBufferedImage(image, sPathToFile);
    }

    /**
     * Method that load images that we saved as BufferedImage,
     * and we use as a model for comparison
     *
     * @param sImageFile {String} - name of image file
     *
     * @return {BufferedImage} - return model image as BufferedImage
     */
    private static BufferedImage loadBufferedImage(String sImageFile) {
        log.trace(String.format("loadBufferedImage (%s)", sImageFile));
        String sImagePath = sImagesPath + sImageFile;
        File imageFile = new File(sImagePath);
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            Assert.fail(String.format("Cannot load image from file: %s. Message: ", sImageFile,e.getMessage()));
        }
        return image;
    }

    /**
     * Method that compares two images and returns true if they are the same
     *
     * @param actualImage {BufferedImage} - actual image taken by the test
     * @param expectedImage {BufferedImage} - image that we expected to see
     * @param threshold {int} - num of pixel that can be tolerated for difference between two images
     * @param makeDiff {boolean} - if we want to make an image
     *
     * @return - true if two images are the same
     */
    public static boolean compareTwoImages(BufferedImage actualImage, BufferedImage expectedImage, int threshold, boolean makeDiff){
        log.trace(String.format("compareTwoImages, threshold: %d", threshold));
        if(actualImage.getWidth() != expectedImage.getWidth() || actualImage.getHeight() != expectedImage.getHeight()){
            return false;
        }
        // if they are the same we get width and height
        int width = actualImage.getWidth();
        int height = actualImage.getHeight();
        // we assume that they are the same
        boolean bEquals = true;
        // save num of diff pixels
        int diffInPixels = 0;
        // image difference - it shows the difference between two images
        BufferedImage differenceImage = new BufferedImage(width, height, actualImage.getType());
        if (threshold < 0 || threshold > 100){
            Assert.fail("Threshold value must be between 0 and 100. Actual value: " + threshold);
        }
        // if threshold is 0, we just search for difference if not we check if differences are in range specified
        if (threshold == 0 ){
            // searching through height
            for (int y = 0; y < height; y++){
                // searching through width
                for (int x = 0; x < width; x ++){
                    // because there is no threshold specified then we compare RGB values
                    if (actualImage.getRGB(x, y) != expectedImage.getRGB(x, y)){
                        // if we want to make difference image, then we color difference with green color
                        if (makeDiff){
                            diffInPixels ++;
                            // 0xFFFF0000 - red
                            // 0xFF00FF00 - green
                            // color strongest FF, no red 00, green FF and no blue 00
                            differenceImage.setRGB(x,y, 0xFF00FF00);
                            // store in memory difference image and not coming out from method
                            bEquals = false;
                        } else {
                            return false;
                        }
                    } else {
                        // because we not have difference image it doesn't matter which RGB we use
                        differenceImage.setRGB(x, y, actualImage.getRGB(x, y));
                    }
                }
            }

        } else {
            for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x ++){
                    double result = compareARGB(actualImage.getRGB(x,y), expectedImage.getRGB(x,y));
                    if(100 * result > threshold) {
                        if (makeDiff){
                            diffInPixels ++;
                            differenceImage.setRGB(x,y, 0xFF00FF00);
                            bEquals = false;
                        } else {
                            return false;
                        }
                    } else {
                        differenceImage.setRGB(x, y, actualImage.getRGB(x, y));
                    }
                }
            }
        }
        if(!bEquals && makeDiff){
            log.warn(String.format("Two images are not equal! Difference size in Pixels: %d", diffInPixels));
            String sImageName = "ImageFile";
            String sDateTimeStamp = DateTimeUtils.getDateTimeStamp();
            String pathToActualImage = createActualSnapshotPath(sImageName, sDateTimeStamp);
            String pathToExpectedImage = createExpectedSnapshotPath(sImageName, sDateTimeStamp);
            String pathToDifferenceImage = createDifferenceSnapshotPath(sImageName, sDateTimeStamp);
            saveBufferedImage(actualImage,pathToActualImage);
            saveBufferedImage(expectedImage,pathToExpectedImage);
            saveBufferedImage(differenceImage,pathToDifferenceImage);
            log.info("Actual image: " + pathToActualImage);
            log.info("Expected image: " + pathToExpectedImage);
            log.info("Difference image: " + pathToDifferenceImage);
        }
        return bEquals;
    }

    /*
     * Method below is used to make a difference image and to search for small image inside bigger one,
     * and if we want to make small image ignore difference and then search for small image if we don't
     * want to then fail after first difference; in conclusion this method is for comparing two images and
     * searching for small image inside bigger one
     */
    /**
     * Method that compares screenshot image with stored
     * image and return true if they are the same
     *
     * @param actualImage {BufferedImage} - actual image taken by the test
     * @param sImageFile {String} - name of image that we expected to see
     * @param threshold {int} - num of pixel that can be tolerated for difference between two images
     * @param makeDiff {boolean} - if we want to make an image
     *
     * @return - true if two images are the same
     */
    public static boolean compareScreenshotWithImage(BufferedImage actualImage, String sImageFile, int threshold, boolean makeDiff) {
        log.trace(String.format("saveScreenShotOfWebElement (%s), threshold: %d", sImageFile, threshold));
        BufferedImage expectedImage = loadBufferedImage(sImageFile);
        // if they are different in dimensions they are definitely different
        if(actualImage.getWidth() != expectedImage.getWidth() || actualImage.getHeight() != expectedImage.getHeight()){
            return false;
        }

        int width = actualImage.getWidth();
        int height = actualImage.getHeight();

        boolean bEquals = true;

        int diffInPixels = 0;

        BufferedImage differenceImage = new BufferedImage(width, height, actualImage.getType());
        if (threshold < 0 || threshold > 100){
            Assert.fail("Threshold value must be between 0 and 100. Actual value: " + threshold);
        }

        if (threshold == 0 ){
            for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x ++){
                    if (actualImage.getRGB(x, y) != expectedImage.getRGB(x, y)){
                        if (makeDiff){
                            diffInPixels ++;
                            differenceImage.setRGB(x,y, 0xFF00FF00);
                            bEquals = false;
                        } else {
                            return false;
                        }
                    } else {
                        differenceImage.setRGB(x, y, actualImage.getRGB(x, y));
                    }
                }
            }

        } else {
            for (int y = 0; y < height; y++){
                for (int x = 0; x < width; x ++){
                    double result = compareARGB(actualImage.getRGB(x,y), expectedImage.getRGB(x,y));
                    if(100 * result > threshold) {
                        if (makeDiff){
                            diffInPixels ++;
                            differenceImage.setRGB(x,y, 0xFF00FF00);
                            bEquals = false;
                        } else {
                            return false;
                        }
                    } else {
                        differenceImage.setRGB(x, y, actualImage.getRGB(x, y));
                    }
                }
            }
        }
        if(!bEquals && makeDiff){
            log.warn(String.format("Snapshot is not equal to image %s! Difference size in Pixels: %d", sImageFile, diffInPixels));
            // we return image files to see what is the difference
            String sImageName = sImageFile.split("\\.")[0];
            String sDateTimeStamp = DateTimeUtils.getDateTimeStamp();
            String pathToActualImage = createActualSnapshotPath(sImageName, sDateTimeStamp);
            String pathToExpectedImage = createExpectedSnapshotPath(sImageName, sDateTimeStamp);
            String pathToDifferenceImage = createDifferenceSnapshotPath(sImageName, sDateTimeStamp);
            saveBufferedImage(actualImage,pathToActualImage);
            saveBufferedImage(expectedImage,pathToExpectedImage);
            saveBufferedImage(differenceImage,pathToDifferenceImage);
            log.info("Actual image: " + pathToActualImage);
            log.info("Expected image: " + pathToExpectedImage);
            log.info("Difference image: " + pathToDifferenceImage);
        }
        return bEquals;
    }

    /**
     * Method that returns true if there is a difference between screenshot
     * taken by the test and actual image using Ashot. If there is a difference,
     * test create a difference image that shows where two images deffer.
     *
     * @param screenshot {Screenshot} - screenshot of image taken by the test
     * @param sImageFile {String} - actual image (image that we use to compare with e.g. SamsaraLogo.png)
     * @param threshold {int} - the allowed difference between two images
     *
     * @return {boolean} - true if images are different
     *
     */
    private static boolean compareScreenshotWithImageAS(Screenshot screenshot, String sImageFile, int threshold) {
        log.trace(String.format("saveScreenShotOfWebElementAS (%s), threshold: %d", sImageFile, threshold));
        BufferedImage actualImage = screenshot.getImage();
        BufferedImage expectedImage = loadBufferedImage(sImageFile);

        ImageDiffer imageDiffer = new ImageDiffer();
        // difference image
        ImageDiff diff = imageDiffer.withColorDistortion(threshold).makeDiff(expectedImage, actualImage);
       // if beside distortion in color we have and some difference in pixels, take in count allowed pixel difference
       // boolean bDiff = diff.withDiffSizeTrigger(5)
        boolean bDiff = diff.hasDiff();
        if (bDiff){
            // returns a num of pixels that two images differs by
            log.warn(String.format("Snapshot is not equal to image %s! Difference size in Pixels: %d", sImageFile, diff.getDiffSize()));
            // it is colored with red the difference between two images
            // it comes from the sImageFile the name with extension which
            // we need to get rid of and take part before dot e.g. SamsaraLogo.png
            // save that part before [0]
            String sImageName = sImageFile.split("\\.")[0];
            BufferedImage markedImage = diff.getMarkedImage();
            String sDateTimeStamp = DateTimeUtils.getDateTimeStamp();
            String pathToActualImage = createActualSnapshotPath(sImageName, sDateTimeStamp);
            String pathToExpectedImage = createExpectedSnapshotPath(sImageName, sDateTimeStamp);
            String pathToDifferenceImage = createDifferenceSnapshotPath(sImageName, sDateTimeStamp);
            saveBufferedImage(actualImage,pathToActualImage);
            saveBufferedImage(expectedImage,pathToExpectedImage);
            saveBufferedImage(markedImage,pathToDifferenceImage);
            log.info("Actual image: " + pathToActualImage);
            log.info("Expected image: " + pathToExpectedImage);
            log.info("Difference image: " + pathToDifferenceImage);
        }
        return !bDiff;
    }

    /**
     * Method that returns the Point that is the center of the image that we use to click on
     *
     * @param driver {WebDriver} - driver
     * @param sImageFile {String} - actual image (image that we use to compare with)
     * @param threshold {int} - the allowed difference between two images
     *
     * @return {Point} - center of the image that we use to click on
     */
    public static Point getImageCenterLocation(WebDriver driver, String sImageFile, int threshold) {
        log.trace(String.format("getImageCenterLocation (%s), threshold: %d", sImageFile, threshold));
        BufferedImage fullImage = takeScreeShot(driver);
        BufferedImage subImage = loadBufferedImage(sImageFile);
        Point location = findSubImage(fullImage, subImage, threshold);
        Assert.assertNotNull(location, String.format("Image %s is NOT found on screen!", sImageFile));
        //we move for half of the width and half of the height
        int xOffset = subImage.getWidth() / 2;
        int yOffset = subImage.getHeight() / 2;
        return new Point(location.getX() + xOffset, location.getY() + yOffset);
    }

    /**
     * Method that finds location (Point) of the sub image inside bigger image
     *
     * @param fullImage {BufferedImage} - image that contains sub image
     * @param subImage {BufferedImage} - image that is inside bigger image
     * @param threshold {int} - the allowed difference between two images
     *
     * @return {Point} - location of the sub image
     */
    public static Point findSubImage(BufferedImage fullImage, BufferedImage subImage, int threshold){
        log.trace(String.format("findSubImage, threshold: %d", threshold));
        int w1 = fullImage.getWidth();
        int w2 = subImage.getWidth();
        int h1 = fullImage.getHeight();
        int h2 = subImage.getHeight();

        if (w2 > w1 || h2 > h1){
            return null;
        }
        int foundX;
        int foundY;

        for(int x = 0; x < w1 - w2; x++){
            for(int y = 0; y < h1 - h2; y++){
                // we cut the part of the bigger image that have dimension of the sub image
                BufferedImage partOfFullImage = fullImage.getSubimage(x, y, w2, h2);
                if (compareTwoImages(partOfFullImage, subImage, threshold, false)){
                    // if it manages to find the sub image inside bigger one
                    // than it returns coordinates of the upper left corner
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    // sometimes it happens that dimensions of an images are hardcoded on the web page (in html)
    // and in that case, it is better to take a screenshot of that element and save it and use it later
    // for the comparison and verification by the test
    /**
     * Method that save BufferedImage in specified place
     *
     * @param bufferedImage {BufferedImage} - an image that we want to save
     * @param sPathToFile {String} - path where to save an image
     */
    private static void saveBufferedImage(BufferedImage bufferedImage, String sPathToFile){
        File destFile = new File(sPathToFile);

        try {
            // saving image and storing it to a file
            ImageIO.write(bufferedImage, "PNG", destFile);
        } catch (IOException e) {
            log.warn(String.format("Buffered Image could NOT be saved in file %s! Message: %s",sPathToFile, e.getMessage()));
        }
    }

    /**
     * Method that save screenshot of WebElement and save it as BufferedImage
     *
     * @param driver {WebDriver} - driver
     * @param element {WebElement} - element that we want to take screenshot of
     * @param sFileName {String} - Name that Image will be saved as e.g. ProfileImage.png
     */
    public static void saveScreenshotOfWebElement(WebDriver driver, WebElement element, String sFileName){
        log.trace(String.format("saveScreenshotOfWebElement() %s, in file %s", element, sFileName));
        String sPathToFile = sScreenshotPath + sFileName;
        // get an image and save it to a file
        BufferedImage image = takeScreenShotOfWebElement(driver, element);
        saveBufferedImage(image, sPathToFile);
    }

    /**
     * Method that is used to compare the difference between two RGB colors
     * @description (& - is used for bit comparison e.g.
     * 1) 00010001 11110000 11001100 11101011
     * 2) 00000000 00000000 00000000 11111111
     * 3) 00000000 00000000 00000000 11101011
     * compares 1 i 2 i and 3 is the result)
     * https://en.wikipedia.org/wiki/RGBA_color_model
     */
    private static double compareARGB(int rgb1, int rgb2){
        // blue color
        double blue1 = (rgb1 & 0xFF) / 255.0;
        double blue2 = (rgb2 & 0xFF) / 255.0;

        // it moves for 8 bits to right for green color
        double green1 = ((rgb1 >> 8) & 0xFF) / 255.0;
        double green2 = ((rgb2 >> 8) & 0xFF) / 255.0;

        // it moves for 16 bits to right for red color
        double red1 = ((rgb1 >> 16) & 0xFF) / 255.0;
        double red2 = ((rgb2 >> 16) & 0xFF) / 255.0;

        // alpha is coefficient of transparency, and it moves for 24 bits to right
        double a1 = ((rgb1 >> 24) & 0xFF) / 255.0;
        double a2 = ((rgb2 >> 24) & 0xFF) / 255.0;

        return a1 * a2 * (Math.sqrt(Math.pow((red1-red2),2) + Math.pow((green1-green2),2) + Math.pow((blue1-blue2),2)))/Math.sqrt(3);
    }
   // actualImage.getRGB(x, y) != expectedImage.getRGB(x, y)

    /**
     * Method that creates snapshot path of image taken by the test
     *
     * @param sImageName {String} - name of the image that will be taken (it can be the name of the test)
     * @param sDateTimeStamp {String} - date and time when image was taken
     */
    private static String createActualSnapshotPath(String sImageName, String sDateTimeStamp){
        return sScreenshotPath + sImageName + "_" + sDateTimeStamp + "_Actual.png";
    }

    /**
     * Method that creates snapshot path of image (original/expected)
     * that will be used to compare with image taken by the test
     *
     * @param sImageName {String} - name of the expected image
     * @param sDateTimeStamp {String} - date and time when image was taken
     */
    private static String createExpectedSnapshotPath(String sImageName, String sDateTimeStamp){
        return sScreenshotPath + sImageName + "_" + sDateTimeStamp + "_Expected.png";
    }

    /**
     * Method that creates snapshot path of difference image that shows
     * what is different between expected and actual image
     *
     * @param sImageName {String} - name of the difference image
     * @param sDateTimeStamp {String} - date and time when image was taken
     */
    private static String createDifferenceSnapshotPath(String sImageName, String sDateTimeStamp){
        return sScreenshotPath + sImageName + "_" + sDateTimeStamp + "_Difference.png";
    }
}
