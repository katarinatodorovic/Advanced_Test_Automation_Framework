package data;

import org.testng.Assert;
import utils.PropertiesUtils;
import java.util.Properties;

/**
 * The CommonStrings class - class for storing common Strings
 */
public final class CommonStrings {

    // Method from the comment is ok when application has a lots of Strings,
    // and we can use only one method that different Strings can be passed to, but,
    // the problem emerge when we are dealing with token Strings
    // public static final LOGOUT_SUCCESS_MESSAGE = "LOGOUT_SUCCESS_MESSAGE"
    // CommonStrings.getLocaleString(CommonStrings.LOGOUT_SUCCESS_MESSAGE)
    // when we use token Strings we could use replace
    // CommonStrings.getLocaleString(CommonStrings.LOGOUT_SUCCESS_MESSAGE).replace(%USERNAME%,sUsername)

    // this way of implementing localization sometimes is not the best for bigger projects
    // that have robust localization because we will then make a lots of methods
    // for different Strings that are localized and example in previous comment is better
    // class for storing strings and several methods for managing them

    // Name of file that will be used in localization
    public static final String sLocaleFile = "locale_" + PropertiesUtils.getLocale() + ".loc";

    // Path to file that will be used in localization
    public static final String sLocalePath = "\\locale\\" + sLocaleFile;

    // the file is loaded only once by storing values in a map that is very fast to search
    public static final Properties locale = PropertiesUtils.loadPropertiesFile(sLocalePath);

    /**
     * Method that returns the value of the locale String defined in the property file (e.g. locale_en.loc)
     * which is representing the default language (e.g. en) of the application
     *
     * @param title {String} - name of the locale string from property file (e.g. LOGIN_ERROR_MESSAGE)
     *
     * @return {String} - the value of locale property from the property file
     */
    private static String getLocaleString(String title){
        String text = locale.getProperty(title);
        Assert.assertNotNull(text, String.format("String %s doesn't exist in file %s!", title, sLocaleFile));
        return text;
    }

    //-------------------------------------------------------------------
    //
    //       TAB TITLE
    //
    //--------------------------------------------------------------------

    /**
     * Method that returns the locale Login tab title
     *
     * @return {String} - the locale Login tab title
     */
    public static String getLoginTabTitle(){
        return getLocaleString("LOGIN_TAB_TITLE");
    }

    /**
     * Method that returns the locale Home tab title
     *
     * @return {String} - the locale Home tab title
     */
    public static String getHomeTabTitle(){
        return getLocaleString("HOME_TAB_TITLE");
    }

    /**
     * Method that returns the locale Users Tab title
     *
     * @return {String} - the locale Users Tab title
     */
    public static String getUsersTabTitle(){
        return getLocaleString("USERS_TAB_TITLE");
    }

    /**
     * Method that returns the locale Heroes tab title
     *
     * @return {String} - the locale Heroes tab title
     */
    public static String getHeroesTabTitle(){
        return getLocaleString("HEROES_TAB_TITLE");
    }

    /**
     * Method that returns the locale Gallery tab title
     *
     * @return {String} - the locale Gallery tab title
     */
    public static String getGalleryTabTitle(){
        return getLocaleString("GALLERY_TAB_TITLE");
    }

    /**
     * Method that returns the locale API tab title
     *
     * @return {String} - the locale API tab title
     */
    public static String getAPITabTitle(){
        return getLocaleString("API_TAB_TITLE");
    }

    /**
     * Method that returns the locale Practice tab title
     *
     * @return {String} - the locale Practice tab title
     */
    public static String getPracticeTabTitle(){
        return getLocaleString("PRACTICE_TAB_TITLE");
    }

    /**
     * Method that returns the locale Broken Link tab title
     *
     * @return {String} - the locale Broken Link tab title
     */
    public static String getBrokenLinkTabTitle(){
        return getLocaleString("BROKEN_LINK_TAB_TITLE");
    }

    /**
     * Method that returns the locale Admin tab title
     *
     * @return {String} - the locale Admin tab title
     */
    public static String getAdminTabTitle(){
        return getLocaleString("ADMIN_TAB_TITLE");
    }

    //-------------------------------------------------------------------
    //
    //       LINK TITLES
    //
    //--------------------------------------------------------------------

    /**
     * Method that returns the locale Profile link title
     *
     * @return {String} - the locale Profile link title
     */
    public static String getProfileLinkTitle(){
        return getLocaleString("PROFILE_LINK_TITLE");
    }

    /**
     * Method that returns the locale Log Out link title
     *
     * @return {String} - the locale Log Out link title
     */
    public static String getLogoutLinkTitle(){
        return getLocaleString("LOGOUT_LINK_TITLE");
    }

    //-------------------------------------------------------------------
    //
    //       PAGE TITLES
    //
    //--------------------------------------------------------------------


    /**
     * Method that returns the locale Login Page title
     *
     * @return {String} - the locale Login Account title
     */
    public static String getLoginPageTitle(){
        return getLocaleString("LOGIN_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Reset Password Page title
     *
     * @return {String} - the locale Reset Password title
     */
    public static String getResetPasswordPageTitle(){
        return getLocaleString("RESET_PASSWORD_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Create Account Page title
     *
     * @return {String} - the locale Create Account title
     */
    public static String getCreateAccountPageTitle(){
        return getLocaleString("CREATE_ACCOUNT_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Home Page title
     *
     * @return {String} - the locale Home Page title
     */
    public static String getHomePageTitle(){
        return getLocaleString("HOME_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Users Page title
     *
     * @return {String} - the locale Users Page title
     */
    public static String getUsersPageTitle(){
        return getLocaleString("USERS_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Heroes Page title
     *
     * @return {String} - the locale Heroes Page title
     */
    public static String getHeroesPageTitle(){
        return getLocaleString("HEROES_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Gallery Page title
     *
     * @return {String} - the locale Gallery Page title
     */
    public static String getGalleryPageTitle(){
        return getLocaleString("GALLERY_PAGE_TITLE");
    }

    /**
     * Method that returns the locale API Page title
     *
     * @return {String} - the locale API Page title
     */
    public static String getAPIPageTitle(){
        return getLocaleString("API_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Practice Page title
     *
     * @return {String} - the locale Practice Page title
     */
    public static String getPracticePageTitle(){
        return getLocaleString("PRACTICE_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Broken Link Page title
     *
     * @return {String} - the locale Broken Link title
     */
    public static String getBrokenLinkPageTitle(){
        return getLocaleString("BROKEN_LINK_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Admin Page title
     *
     * @return {String} - the locale Admin Page title
     */
    public static String getAdminPageTitle(){
        return getLocaleString("ADMIN_PAGE_TITLE");
    }

    /**
     * Method that returns the locale Profile Page title
     *
     * @return {String} - the locale Profile Page title
     */
    public static String getProfilePageTitle(){
        return getLocaleString("PROFILE_PAGE_TITLE");
    }

    //-------------------------------------------------------------------
    //
    //       LOGIN PAGE
    //
    //--------------------------------------------------------------------

    /**
     * Method that returns the locale Login Error Message
     *
     * @return {String} - the locale Login Error Message
     */
    public static String getLoginErrorMessage(){
        return getLocaleString("LOGIN_ERROR_MESSAGE");
    }


    /**
     * Method that returns the locale logout success message displayed after successful logout
     *
     * @return {String} - the locale logout success message
     */
    public static String getLogoutSuccessMessage(){
        return getLocaleString("LOGOUT_SUCCESS_MESSAGE");
    }

    /**
     * Method that returns the locale register success message displayed after successful register
     *
     * @return {String} - the locale register success message
     */
    public static String getRegisterSuccessMessage(){
        return getLocaleString("REGISTER_SUCCESS_MESSAGE");
    }


    //-------------------------------------------------------------------
    //
    //       PRACTICE PAGE
    //
    //--------------------------------------------------------------------


    /**
     * Method that returns the locale Useless Tooltip Text displayed on mouse hover
     *
     * @return {String} - the locale Useless Tooltip Text
     */
    public static String getUselessTooltipText(){
        return getLocaleString("USELESS_TOOLTIP_TEXT");
    }

    //-------------------------------------------------------------------
    //
    //       HERO CLASS
    //
    //--------------------------------------------------------------------

    /**
     * Method that returns the locale Warrior Hero Class
     *
     * @return {String} - the locale Warrior Hero Class
     */
    public static String getWarriorHeroClass(){
        return getLocaleString("WARRIOR_HERO_CLASS");
    }

    /**
     * Method that returns the locale Guardian Hero Class
     *
     * @return {String} - the locale Guardian Hero Class
     */
    public static String getGuardianHeroClass(){
        return getLocaleString("GUARDIAN_HERO_CLASS");
    }

    /**
     * Method that returns the locale Revenant Hero Class
     *
     * @return {String} - the locale Revenant Hero Class
     */
    public static String getRevenantHeroClass(){
        return getLocaleString("REVENANT_HERO_CLASS");
    }

    /**
     * Method that returns the locale Engineer Hero Class
     *
     * @return {String} - the locale Engineer Hero Class
     */
    public static String getEngineerHeroClass(){
        return getLocaleString("ENGINEER_HERO_CLASS");
    }

    /**
     * Method that returns the locale Thief Hero Class
     *
     * @return {String} - the locale Thief Hero Class
     */
    public static String getThiefHeroClass(){
        return getLocaleString("THIEF_HERO_CLASS");
    }

    /**
     * Method that returns the locale Ranger Hero Class
     *
     * @return {String} - the locale Ranger Hero Class
     */
    public static String getRangerHeroClass(){
        return getLocaleString("RANGER_HERO_CLASS");
    }

    /**
     * Method that returns the locale Elementalist Hero Class
     *
     * @return {String} - the locale Elementalist Hero Class
     */
    public static String getElementalistHeroClass(){
        return getLocaleString("ELEMENTALIST_HERO_CLASS");
    }

    /**
     * Method that returns the locale Necromancer Hero Class
     *
     * @return {String} - the locale Necromancer Hero Class
     */
    public static String getNecromancerHeroClass(){
        return getLocaleString("NECROMANCER_HERO_CLASS");
    }

    /**
     * Method that returns the locale Mesmer Hero Class
     *
     * @return {String} - the locale Mesmer Hero Class
     */
    public static String getMesmerHeroClass(){
        return getLocaleString("MESMER_HERO_CLASS");
    }

    //-------------------------------------------------------------------
    //
    //       DELETE HERO DIALOG BOX
    //
    //--------------------------------------------------------------------

    /**
     * Method that returns the locale Delete Hero Message
     *
     * @param sHeroName {String} - hero name
     * @param sHeroClass {String} - hero class
     * @param sHeroLevel {String} - hero level
     *
     * @return {String} - the locale Delete Hero Message
     */
    public static String getDeleteHeroMessage(String sHeroName, String sHeroClass, String sHeroLevel){
        return getLocaleString("DELETE_HERO_MESSAGE").
                replace("%HERO_NAME%",sHeroName).
                replace("%HERO_CLASS%",sHeroClass).
                replace("%HERO_LEVEL%",sHeroLevel);
    }

    /**
     * Method that returns the locale Drag and Drop message
     *
     * @return {String} - the locale Drag and Drop message
     */
    public static String getDragAndDropMessage(){
        return getLocaleString("DRAG_AND_DROP_MESSAGE");
    }

    //-------------------------------------------------------------------
    //
    //       API ERRORS
    //
    //--------------------------------------------------------------------

    /**
     * Method that returns the locale Api Error Forbidden
     *
     * @return {String} - the locale Api Error Forbidden
     */
    public static String getApiErrorForbidden(){
        return getLocaleString("API_ERROR_FORBIDDEN");
    }

    /**
     * Method that returns the locale Api Error Internal Server Error
     *
     * @return {String} - the locale Api Error Internal Server Error
     */
    public static String getApiErrorInternalServerError(){
        return getLocaleString("API_ERROR_INTERNAL_SERVER_ERROR");
    }

    //-------------------------------------------------------------------
    //
    //       API MESSAGES
    //
    //--------------------------------------------------------------------

    /**
     * Method that returns the locale Api Message Access Denied
     *
     * @return {String} - the locale Api Message Access Denied
     */
    public static String getApiMessageAccessDenied(){
        return getLocaleString("API_MESSAGE_ACCESS_DENIED");
    }

    /**
     * Method that returns the locale Api Message Already Existing User
     *
     * @return {String} - the locale Api Message Already Existing User
     */
    public static String getApiMessageAlreadyExistingUser(String sUsername){
        return getLocaleString("API_MESSAGE_ALREADY_EXISTING_USER").replace("%USERNAME%",sUsername);
    }

    /**
     * Method that returns the locale Api Message Email Not Specified
     *
     * @return {String} - the locale Api Message Email Not Specified
     */
    public static String getApiMessageEmailNotSpecified(){
        return getLocaleString("API_MESSAGE_EMAIL_NOT_SPECIFIED");
    }

    //-------------------------------------------------------------------
    //
    //       API EXCEPTIONS
    //
    //--------------------------------------------------------------------

    /**
     * Method that returns the locale Api Illegal Argument Exception
     *
     * @return {String} - the locale Api Illegal Argument Exception
     */
    public static String getApiIllegalArgumentException(){
        return getLocaleString("API_ILLEGAL_ARGUMENT_EXCEPTION");
    }
}
