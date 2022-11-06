package data;
/**
 * Class for storing API cals
 */
public final class APICalls {

    //------------------------------------ USER API CALLS -----------------------------------
    public static final String CHECK_IF_USER_EXISTS="/api/users/exists/";
    public static final String GET_USER="/api/users/findByUsername/";
    public static final String POST_USER="/api/users/add";
    public static final String DELETE_USER="/api/users/deleteByUsername/";



    //---------------------------------- HEROES API CALLS ---------------------------------
    public static final String CHECK_IF_HERO_EXISTS="/api/heroes/exists/";
    public static final String GET_HERO="/api/heroes/findByName/";
    public static final String POST_HERO="/api/heroes/add";
    public static final String DELETE_HERO="/api/heroes/deleteByName/";


    //--------------------------------API calls for defined USER ------------------------------------------------------
    public static String createCheckIfUserExistApiCall(String sUsername){
        return CHECK_IF_USER_EXISTS + sUsername;
    }
    public static String createGetUserApiCall(String sUsername){
        return GET_USER + sUsername;
    }
    public static String createPostUserApiCall(){
        return POST_USER;
    }
    public static String createDeleteUserApiCall(String sUsername){
        return DELETE_USER + sUsername;
    }

    //------------------------------ API calls for defined HEROES ---------------------------------------------------
    public static String createCheckIfHeroExistApiCall(String sHeroName){
        return CHECK_IF_HERO_EXISTS + sHeroName;
    }
    public static String createGeHeroApiCall(String sHeroName){
        return GET_HERO + sHeroName;
    }
    public static String createPostHeroApiCall(){
        return POST_HERO;
    }
    public static String createDeleteHeroApiCall(String sHeroName){
        return DELETE_HERO + sHeroName;
    }
}
