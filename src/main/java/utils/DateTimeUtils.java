package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils extends LoggerUtils {
    public static void wait(int seconds){
       try {
           Thread.sleep(1000L * seconds);
       }catch (InterruptedException e){
           //This is how we can implement Thread sleep in our tests and modify, so we don't
           //have to have exception to handle in tests and to write time in milliseconds
           //also we can fail test in case that this type of implementation is important for our test
           //or if it is not just to print warning log and go ahead with testing
           //Assert.fail();
           log.warn(String.format("InterruptedException exception in Thread.sleep(). " +
                   "Message: %s", e.getMessage()));
       }
    }

    /**
     * Method that gets a current date that is on our machine
     *
     * @return {Date} - current date
     */
    public static Date getCurrentDateTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * Method that use milliseconds that is passed as parameter
     * and convert that in Date (it is useful because time of
     * creating e.g. user is stored in milliseconds, and it can be
     * converted, so it will easier to read and debug)
     *
     * @param milliSeconds {long} - date in milliseconds
     *
     * @return {Date} - Date based on milliseconds
     */
    public static Date getDateTime(long milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return calendar.getTime();
    }

    /**
     * Method that date that is passed as parameter convert in
     * desired format that is passed as parameter and returns
     * a Date in desired format
     *
     * @param date {Date} - Date that we want to convert
     * @param pattern {String} - Desired date format
     *
     * @return {Date} - formatted Date
     */
    //samsara dd.MM.yyyy. HH:mm -> 06.02.2022.
     public static String getFormattedDateTime(Date date, String pattern){
         DateFormat dateFormat = new SimpleDateFormat(pattern);
         return dateFormat.format(date);
     }

     /**
      * Method that gets Date, date pattern and locale e.g. CEST, PST...
      * and format date based on parameter passed according to
      * iso 639-1 https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
      *
      * @param date {Date} - the Date that we want to transform into specified locale
      * @param pattern {String} - the pattern describing the date and time format
      * @param locale {String} - the locale whose date format symbols should be used
      *
      * @return {String} - the formatted Date
      */
     public static String getLocalizedDateTime(Date date, String pattern, String locale){
         Locale local = new Locale(locale);
         DateFormat dateFormat = new SimpleDateFormat(pattern,local);
         return dateFormat.format(date);
     }

    /**
     * Parse a date/time string according to the given pattern
     * @description - try/catch is needed because we may encounter the
     * problem while parsing string into date
     *
     * @param sDateTime - the date/time string to be parsed
     * @param pattern {String} - the pattern describing the date and time format
     *
     * @return - a Date, or null if the input could not be parsed
     */
    public static Date getParsedDateTime(String sDateTime, String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(sDateTime);
        } catch (ParseException e) {
            Assert.fail(String.format("Cannot parse date %s using %s pattern! Message: %s",sDateTime,pattern,e.getMessage()));
        }
        return date;
    }

    /**
     * Current Date from local machine is converted
     * in desired Date format that is passed as a String parameter
     *
     * @param pattern {String} - Desired date format
     *
     * @return {Date} - formatted Date
     */
    public static String getFormattedCurrentDateTime(String pattern){
         Date date = getCurrentDateTime();
         return getFormattedDateTime(date,pattern);
    }

    /**
     * Method that gets Date from local machine and convert it in
     * default format (22.06.23. 18:07:59:123-> year as two digit number,
     * month, day, hour, minutes, seconds, milliseconds)
     *
     * @return {Date} - formatted Date
     */
    public static String getDateTimeStamp(){
        return getFormattedCurrentDateTime("yyMMddHHmmssSSS");
    }

    /**
     * Method that compares difference between two dates and check if the difference is in
     * the optimal range -> range is defined by parameter we pass as num of seconds
     * @description - it can be used to compare time when user is created and time when
     * that user is created in database and check if there is a big difference between those
     * values which can indicate there is a problem with adding user to database
     *
     * @param date1 {Date} - date when user is saved in database
     * @param date2 {Date} - date when user is created
     * @param threshold {int} - the optimal range in seconds that can be tolerated
     *                          between those two values
     *
     * @return {int} - difference between saved and created time
     */
    public static boolean compareDateTimes(Date date1, Date date2, int threshold){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(date1);
        calendar2.setTime(date2);

        long diff = (calendar2.getTimeInMillis() - calendar1.getTimeInMillis())/1000;
        log.debug(String.format("Compare Dates (Date 1: %s, Date 2: %s), Actual Difference: %s",calendar1.getTime(),calendar2.getTime(),diff));
        // Difference can be negative nad positive and this is why we use abs value
        return Math.abs(diff) <= threshold;
    }

    /**
     * Method that compares difference between two dates and check if the difference
     * is in default range which is +-1 min
     *
     * @param date1 {Date} - date when user is saved in database
     * @param date2 {Date} - date when user is created
     *
     * @return {int} - difference between saved and created time
     */
    public static boolean compareDateTimes(Date date1, Date date2){
        return compareDateTimes(date1,date2,60 );
    }

    /**
     * Method that we use to get time and timezone from browser
     * one of usual solution is to use a JavaScriptExecutor getTimezoneOffset()
     * which requires a lots of conversions and calculations between input and output
     * and also to pay attention to prevent mistakes
     * .
     * This is an easier way of implementation. Most of the new browsers use the already
     * formatted date via JS, and then we can specify the timezone we need
     *
     * @param driver {WebDriver} - WebDriver
     *
     * @return {Date} - browser date
     */
    public static Date getBrowserDateTime(WebDriver driver){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String sBrowserDateTime = (String) js.executeScript("var browserDateTime = new Date().getTime(); " +
                "return Intl.DateTimeFormat('en-GB', { dateStyle: 'full', timeStyle: 'long' }).format(browserDateTime);");
        // Saturday, 9 July 2022 at 22:00:50 CEST turn into Sat Jul 09 22:07:53 CEST 2022

        sBrowserDateTime = sBrowserDateTime.replace(" at "," ");
        String sPattern = "EEEE, dd MMMM yyyy HH:mm:ss z";
        return getParsedDateTime(sBrowserDateTime,sPattern);
    }

    /**
     * Method that gets the timezone from the Browser where tests are executed
     *
     * @param driver {WebDriver} - WebDriver
     *
     * @return {String} - timezone from the browser where tests are executed
     */
    public static String getBrowserTimezone(WebDriver driver){
        Date date = getBrowserDateTime(driver);
        return getFormattedDateTime(date, "z");
    }
}
