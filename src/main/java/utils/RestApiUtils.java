package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.APICalls;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import objects.ApiError;
import objects.Hero;
import objects.User;
import org.testng.Assert;

public class RestApiUtils extends LoggerUtils {

     private static final String BASE_URL = PropertiesUtils.getBaseUrl();
     private static final String sAdminUser = PropertiesUtils.getAdminUserName();
     private static final String sAdminPassword = PropertiesUtils.getAdminPassword();

     //-------------------------------------------
     //----------Check if User EXIST -------------
     //-------------------------------------------

     /**
      * Method that use GET API call to check if user exists and is
      * helper method for the method below which returns boolean,
      * it takes username of specific user, username and password
      * and returns Response
      * @description - this method use basic authentication because
      * test application supporting this kind of authentication.
      * After that, goes header and body which are the main part of API call
      * header - Content type that we are sending and then accept -> what
      * we are receiving, after that goes when -> our GET API call checkIfUserExist,
      * we are receiving response if something goes wrong we catch exception and test is failed
      */
     private static Response checkIfUserExistApiCall(String sUsername, String sAuthUser, String sAuthPass){
          String sApiCall = BASE_URL + APICalls.createCheckIfUserExistApiCall(sUsername);
          Response response = null;
          try {
               response = RestAssured.given().auth().basic(sAuthUser,sAuthPass)
                          .headers("Content-Type", ContentType.JSON,"Accept",ContentType.JSON)
                          .when().get(sApiCall);
          } catch (Exception e){
               Assert.fail(String.format("Exception in checkIfUserExistApiCall (username: %s), Api Call: %s", sUsername,e.getMessage()));
          }
          return response;
     }

     /**
      * Method that check if user exists, it takes username of user that we want
      * to check and its credentials. Get status code and check if it is 200.
      * Transform response body into String and check if it is true or false,then
      * parse response as Boolean and return true/false
      *
      * @param sUsername {String} - username of user that we want to check if user exist
      * @param sAuthUser {String} - username
      * @param sAuthPass {String} - password
      *
      * @return {Boolean} - if user exist or not
      */
     public static boolean checkIfUserExist(String sUsername, String sAuthUser, String sAuthPass){
          log.trace("checkIfUserExist() "+ sUsername);
          Response response = checkIfUserExistApiCall(sUsername,sAuthUser,sAuthPass);
          int status = response.getStatusCode();
          String sResponseBody = response.getBody().asString();
          Assert.assertEquals(status,200, "Wrong Response Status code in checkIfUserExist() " + sUsername
          + " Response body: " + sResponseBody);

          // this is good approach if our response can be something other than true/false
          String result = sResponseBody.toLowerCase();
          if (!(result.equals("true") || result.equals("false"))){
               Assert.fail("Cannot convert response " + sResponseBody + " to boolean value!");
          }
          // Boolean.parseBoolean(sResponseBody); this method parse every combination
          // of lower or upper case if response is true, and everything else as false
          // True, true, TRUE -> true
          // FALSE, False, false, fafgrgfsdd, Error, Yes -> false
           return Boolean.parseBoolean(sResponseBody);
     }

     /**
      * Default method that checks if user exist using admin credentials to authenticate
      *
      * @param sUsername {String} - username of user that we want to check if user exist
      *
      * @return {Boolean} - if user exist or not
      */
     public static boolean checkIfUserExist(String sUsername){
          return checkIfUserExist(sUsername, sAdminUser, sAdminPassword);
     }

     //-------------------------------------------
     //----------GET  User------------------------
     //-------------------------------------------

     /**
      * Method that use GET API call to get user and return response in JSON format.
      * This is helper method for the method below which returns user in JSON format,
      * it takes username of specific user, username and password
      * and returns Response
      * @description - this method use basic authentication because
      * test application supporting this kind of authentication.
      * After that, goes header which is the main part of API call
      * header - Content type that we are sending and then accept -> what
      * we are receiving, after that goes when -> our GET API call checkIfUserExist,
      * we are receiving response if something goes wrong we catch exception and test is failed
      */
     private static Response getUserApiCall(String sUsername, String sAuthUser, String sAuthPass){
          String sApiCall = BASE_URL + APICalls.createGetUserApiCall(sUsername);
          Response response = null;
          try {
               response = RestAssured.given().auth().basic(sAuthUser,sAuthPass)
                       .headers("Content-Type", ContentType.JSON,"Accept",ContentType.JSON)
                       .when().get(sApiCall);
          } catch (Exception e){
               Assert.fail(String.format("Exception in getUserApiCall (username: %s), Api Call: %s", sUsername,e.getMessage()));
          }
          return response;
     }

     /**
      * Method that first checks if user exist, transform response body into pretty String
      * (response body in JSON format), get status code and check if it is 200 and returns
      * that response
      * @description - this method is useful for deserialization of JSON response into User
      * object via Gson. (We get a String as a response and if there is no Gson as a helper
      * we would be making complex method which would be going through long String in search for a mach)
      * If there is some data that is defined in the class, and it is not present in JSON its
      * value will be null
      *
      * @param sUsername {String} - username of user that we want to get
      * @param sAuthUser {String} - username
      * @param sAuthPass {String} - password
      *
      * @return {String} - User object in JSON format
      */
     public static String getUserJSONFormat(String sUsername, String sAuthUser, String sAuthPass){
          log.trace("getUserJSONFormat() "+ sUsername);
          Assert.assertTrue(checkIfUserExist(sUsername,sAuthUser,sAuthPass),"User " + sUsername + " DOESN'T exist!");
          Response response = getUserApiCall(sUsername,sAuthUser,sAuthPass);
          int status = response.getStatusCode();
          String sResponseBody = response.getBody().asPrettyString();
          Assert.assertEquals(status,200, "Wrong Response Status code in getUserJSONFormat() " + sUsername
                  + " Response body: " + sResponseBody);
          return sResponseBody;
     }

     /**
      * Default method that GET user using admin credentials to authenticate
      *
      * @param sUsername {String} - username of user that we want to check if user exist
      *
      * @return {String} - User object in JSON format
      */
     public static String getUserJSONFormat(String sUsername){
          return getUserJSONFormat(sUsername, sAdminUser, sAdminPassword);
     }

     /**
      * Method that returns User object
      * @description - this method deserialize from JSON format  into User
      * Java class using Gson. Gson supports implicit casting and that is why User class has
      * the same variable names and types as they are in response we get from API call (JSON)
      * Gson "looks" at class that is provided and response in JSON and then matches them
      * by type and name e.g -> "username": "dedoje" =>  private String username;
      *
      * @param sUsername {String} - username of user that we want to get
      * @param sAuthUser {String} - username
      * @param sAuthPass {String} - password
      *
      * @return {User} - User object
      *
      */
     public static User getUser(String sUsername, String sAuthUser, String sAuthPass){
          log.debug("getUser() "+ sUsername);
          String JSON = getUserJSONFormat(sUsername,sAuthUser,sAuthPass);
          Gson gson = new Gson();
          return gson.fromJson(JSON, User.class);
     }

     /**
      * Default method that returns User object using admin credentials to authenticate
      *
      * @param sUsername {String} - username of user that we want to get
      *
      * @return {User} - User object
      */
     public static User getUser(String sUsername){
         return getUser(sUsername,sAdminUser,sAdminPassword);
     }

     /**
      * Method that gets GET User error from GET API call and
      * return it as a String
      *
      * @param sUsername {String} - User's username
      * @param sAuthUser {String} - username to authenticate
      * @param sAuthPass {String} - password to authenticate
      *
      * @return {String} - GET User API Error as a String
      */
     public static String getUserErrorJSONFormat(String sUsername, String sAuthUser, String sAuthPass){
          log.trace("getUserErrorJSONFormat() "+ sUsername);
          Response response = getUserApiCall(sUsername,sAuthUser,sAuthPass);
          return response.getBody().asString();
     }


     /**
      * Method that gets User error from GET API call and convert it to an
      * ApiError instance using Gson
      *
      * @param sUsername {String} - User's username
      * @param sAuthUser {String} - username to authenticate
      * @param sAuthPass {String} - password to authenticate
      *
      * @return {ApiError} - GET User API Error as a ApiError instance
      */
     public static ApiError getUserError(String sUsername, String sAuthUser, String sAuthPass){
          log.debug("getUserError() "+ sUsername);
          String JSON = getUserErrorJSONFormat(sUsername,sAuthUser,sAuthPass);
          Gson gson = new Gson();
          return gson.fromJson(JSON, ApiError.class);
     }

      /**
      * Method that gets User error from GET API call and convert it to an
      * ApiError instance using admin to authenticate
      *
      *  @return {ApiError} - GET User API Error as a ApiError instance
      */
     public static ApiError getUserError(String sUsername){
          return getUserError(sUsername, sAdminUser,sAdminPassword);
     }

     //-------------------------------------------
     //----------POST  User-----------------------
     //-------------------------------------------

     /**
      * Method that use POST API calls to POST user and return response in JSON format.
      * This is helper method for the method below which POST user in database,
      * it takes username of specific user, username and password
      * and returns Response
      * @description - this method use basic authentication because
      * test application supporting this kind of authentication.
      * After that, goes header and body which are the main part of API call
      * header -> Content type that we are sending, body -> User we want to add to database
      * and then accept -> what we are receiving, after that goes when -> our POST API call, and
      * we are receiving response. If something goes wrong we catch exception and test will fail
      */
     private static Response postUserApiCall(User user, String sAuthUser, String sAuthPass){
          String sApiCall = BASE_URL + APICalls.createPostUserApiCall();
          Response response = null;
          try {
               Gson gson = new Gson();
               String json = gson.toJson(user, User.class);
               response = RestAssured.given().auth().basic(sAuthUser,sAuthPass)
                       .headers("Content-Type", ContentType.JSON,"Accept",ContentType.JSON)
                       .body(json)
                       .when().post(sApiCall);
          } catch (Exception e){
               Assert.fail(String.format("Exception in postUserApiCall (username: %s), Api Call: %s", user.getUsername(),e.getMessage()));
          }
          return response;
     }

     /**
      * Method that POST user in database and check if that user exist
      *
      * @param user {User} - User object
      * @param sAuthUser {String} - username
      * @param sAuthPass {String} - password
      *
      */
     public static void postUser(User user, String sAuthUser,String sAuthPass){
          log.debug("postUser() "+ user.getUsername());
          Assert.assertFalse(checkIfUserExist(user.getUsername(),sAuthUser,sAuthPass),"User " + user.getUsername() + " already exist!");
          Response response = postUserApiCall(user,sAuthUser,sAuthPass);
          int status = response.getStatusCode();
          String sResponseBody = response.getBody().asString();
          Assert.assertEquals(status,200, "Wrong Response Status code in postUser() " + user.getUsername()
                  + " Response body: " + sResponseBody);
          log.debug("User CREATED: " + checkIfUserExist(user.getUsername(),sAuthUser,sAuthPass));
     }

     /**
      * Default method that POST User object using admin credentials to authenticate
      *
      * @param user {User} - User object
      *
      */
     public static void postUser(User user){
          postUser(user,sAdminUser,sAdminPassword);
     }

     /**
      * Method that gets POST User error from POST API call and
      * return it as a String
      *
      * @param user {User} - User to POST
      * @param sAuthUser {String} - username to authenticate
      * @param sAuthPass {String} - password to authenticate
      *
      * @return {String} - POST User API Error as a String
      */
     public static String postUserErrorJSONFormat(User user, String sAuthUser, String sAuthPass){
          log.trace("postUserErrorJSONFormat() "+ user.getUsername());
          Response response = postUserApiCall(user,sAuthUser,sAuthPass);
          return response.getBody().asString();
     }


     /**
      * Method that gets POST User error from POST API call and convert it to an
      * ApiError instance using Gson
      *
      * @param user {User} - User to POST
      * @param sAuthUser {String} - username to authenticate
      * @param sAuthPass {String} - password to authenticate
      *
      * @return {ApiError} - POST User API Error as a ApiError instance
      */
     public static ApiError postUserError(User user, String sAuthUser, String sAuthPass){
          log.debug("postUserError() "+ user.getUsername());
          String JSON = postUserErrorJSONFormat(user,sAuthUser,sAuthPass);
          Gson gson = new Gson();
          return gson.fromJson(JSON, ApiError.class);
     }

     /**
      * Method that gets POST User error from POST API call and convert it to an
      * ApiError instance using admin to authenticate
      *
      *  @return {ApiError} - POST User API Error as a ApiError instance
      */
     public static ApiError postUserError(User user){
          return postUserError(user, sAdminUser,sAdminPassword);
     }


     //-------------------------------------------
     //----------DELETE  User---------------------
     //-------------------------------------------


     /**
      * Method that use DELETE API calls to DELETE user from database
      * and return response in JSON format. This is helper method for
      * the method below which DELETE user from database. It takes
      * username of specific user, username and password and returns Response
      */
     private static Response deleteUserApiCall(String sUsername, String sAuthUser, String sAuthPass){
          String sApiCall = BASE_URL + APICalls.createDeleteUserApiCall(sUsername);
          Response response = null;
          try {
               response = RestAssured.given().auth().basic(sAuthUser,sAuthPass)
                       .headers("Content-Type", ContentType.JSON,"Accept",ContentType.JSON)
                       .when().delete(sApiCall);
          } catch (Exception e){
               Assert.fail(String.format("Exception in deleteUserApiCall (username: %s), Api Call: %s", sUsername,e.getMessage()));
          }
          return response;
     }

     /**
      * Method that DELETE user from database and check if that user still exist
      *
      * @param sUsername {String} - username of user that we want to DELETE
      * @param sAuthUser {String} - username
      * @param sAuthPass {String} - password
      *
      */
     public static void deleteUser(String sUsername, String sAuthUser,String sAuthPass){
          log.debug("deleteUser() "+ sUsername);
          Assert.assertTrue(checkIfUserExist(sUsername,sAuthUser,sAuthPass),"User " + sUsername + " DOESN'T exist!");
          Response response = deleteUserApiCall(sUsername,sAuthUser,sAuthPass);
          int status = response.getStatusCode();
          String sResponseBody = response.getBody().asString();
          Assert.assertEquals(status,200, "Wrong Response Status code in deleteUser() " + sUsername
                  + " Response body: " + sResponseBody);
          log.debug("User DELETED: " + !checkIfUserExist(sUsername,sAuthUser,sAuthPass));
     }

     /**
      * Default method that DELETE User from database using admin credentials to authenticate
      *
      * @param sUsername {String} - username of user that we want to DELETE
      *
      */
     public static void deleteUser(String sUsername){
          deleteUser(sUsername, sAdminUser,sAdminPassword);
     }



     //---------------------------------  H    E    R    O    E    S  ---------------------------------
     //-------------------------------------    A     P     I     --------------------------------------

     //-------------------------------------------
     //----------Check if HERO EXIST -------------
     //-------------------------------------------

     /**
      * Method that use GET API call to check if hero exists and is
      * helper method for the method below which returns boolean,
      * it takes heroName of specific hero, username and password
      * and returns Response
      */
     private static Response checkIfHeroExistApiCall(String sHeroName, String sAuthName, String sAuthPass){
          String sApiCall = BASE_URL + APICalls.createCheckIfHeroExistApiCall(sHeroName);
          Response response = null;
          try {
               response = RestAssured.given().auth().basic(sAuthName,sAuthPass)
                       .headers("Content-Type", ContentType.JSON,"Accept",ContentType.JSON)
                       .when().get(sApiCall);
          } catch (Exception e){
               Assert.fail(String.format("Exception in checkIfHeroExistApiCall (HeroName: %s), Api Call: %s", sHeroName,e.getMessage()));
          }
          return response;
     }

     /**
      * Method that check if hero exists, it takes heroName of hero that we want
      * to check and user credentials. Get status code and check if it is 200.
      * Transform response body into String and check if it is true or false,then
      * parse response as Boolean and return true/false
      *
      * @param sHeroName {String} - heroName of hero that we want to check if exist
      * @param sAuthName {String} - username
      * @param sAuthPass {String} - password
      *
      * @return {Boolean} - if hero exist or not
      */
     public static boolean checkIfHeroExist(String sHeroName, String sAuthName, String sAuthPass){
          log.trace("checkIfHeroExist() "+ sHeroName);
          Response response = checkIfHeroExistApiCall(sHeroName,sAuthName,sAuthPass);
          int status = response.getStatusCode();
          String sResponseBody = response.getBody().asString();
          Assert.assertEquals(status,200, "Wrong Response Status code in checkIfHeroExist() " + sHeroName
                  + " Response body: " + sResponseBody);

          // this is good approach if our response can be something other than true/false
          String result = sResponseBody.toLowerCase();
          if (!(result.equals("true") || result.equals("false"))){
               Assert.fail("Cannot convert response " + sResponseBody + " to boolean value!");
          }
          // Boolean.parseBoolean(sResponseBody); this method parse every combination
          // of lower or upper case if response is true, and everything else as false
          // True, true, TRUE -> true
          // FALSE, False, false, fafgrgfsdd, Error, Yes -> false
          return Boolean.parseBoolean(sResponseBody);
     }

     /**
      * Default method that checks if hero exist using admin credentials to authenticate
      *
      * @param sHeroName {String} - heroName of hero that we want to check if exist
      *
      * @return {Boolean} - if hero exist or not
      */
     public static boolean checkIfHeroExist(String sHeroName){
          return checkIfHeroExist(sHeroName, sAdminUser, sAdminPassword);
     }

     //-------------------------------------------
     //----------GET  Hero------------------------
     //-------------------------------------------

     /**
      * Method that use GET API call to get hero and return response in JSON format.
      * This is helper method for the method below which returns hero in JSON format,
      * it takes heroName, username and password
      * and returns Response
      */
     private static Response getHeroApiCall(String sHeroName, String sAuthName, String sAuthPass){
          String sApiCall = BASE_URL + APICalls.createGeHeroApiCall(sHeroName);
          Response response = null;
          try {
               response = RestAssured.given().auth().basic(sAuthName,sAuthPass)
                       .headers("Content-Type", ContentType.JSON,"Accept",ContentType.JSON)
                       .when().get(sApiCall);
          } catch (Exception e){
               Assert.fail(String.format("Exception in getHeroApiCall (heroName: %s), Api Call: %s", sHeroName,e.getMessage()));
          }
          return response;
     }

     /**
      * Method that first checks if hero exist, transform response body into pretty String
      * (response body in JSON format), get status code and check if it is 200 and returns
      * that response
      *
      * @param sHeroName {String} - heroName of hero that we want to get
      * @param sAuthName {String} - username
      * @param sAuthPass {String} - password
      *
      * @return {String} - hero object in JSON format
      */
     public static String getHeroJSONFormat(String sHeroName, String sAuthName, String sAuthPass){
          log.trace("getHeroJSONFormat() "+ sHeroName);
          Assert.assertTrue(checkIfHeroExist(sHeroName,sAuthName,sAuthPass),"hero " + sHeroName + " DOESN'T exist!");
          Response response = getHeroApiCall(sHeroName,sAuthName,sAuthPass);
          int status = response.getStatusCode();
          String sResponseBody = response.getBody().asPrettyString();
          Assert.assertEquals(status,200, "Wrong Response Status code in getHeroJSONFormat() " + sHeroName
                  + " Response body: " + sResponseBody);
          return sResponseBody;
     }

     /**
      * Default method that GET hero using admin credentials to authenticate
      *
      * @param sHeroName {String} - heroName of hero that we want to check if hero exist
      *
      * @return {String} - hero object in JSON format
      */
     public static String getHeroJSONFormat(String sHeroName){
          return getHeroJSONFormat(sHeroName, sAdminUser, sAdminPassword);
     }

     /**
      * Method that returns Hero object
      *
      * @param sHeroName {String} - heroName of hero that we want to get
      * @param sAuthName {String} - username
      * @param sAuthPass {String} - password
      *
      * @return {Hero} - Hero object
      *
      */
     public static Hero getHero(String sHeroName, String sAuthName, String sAuthPass){
          log.debug("getHero() "+ sHeroName);
          String JSON = getHeroJSONFormat(sHeroName,sAuthName,sAuthPass);
          // We can use simple instance of Gson instead of GsonBuilder nut we can't use
          // e.g. Exposed annotation and other methods if we don't want variables to
          // be serialized or deserialized
          // Gson gson = new Gson();
          // return gson.fromJson(JSON, hero.class)
          Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
          return gson.fromJson(JSON, Hero.class);
     }

     /**
      * Default method that returns Hero object using admin credentials to authenticate
      *
      * @param sHeroName {String} - heroName of hero that we want to get
      *
      * @return {Hero} - Hero object
      */
     public static Hero getHero(String sHeroName){
          return getHero(sHeroName,sAdminUser,sAdminPassword);
     }

     //-------------------------------------------
     //----------POST  Hero-----------------------
     //-------------------------------------------

     /**
      * Method that use POST API calls to POST Hero and return response in JSON format.
      * This is helper method for the method below which POST Hero in database,
      * it takes heroName of specific Hero, username and password
      * and returns Response
      *
      * @description - response returns 302 redirected instead of 200 ok when
      * it successfully posts a Hero which we can see from delete example (Samsara APP)
      * Postman ignores that but in Java we use redirects().follow(false)
      *
      *        @PostMapping(value="/heroes/delete/{id}")
      *         public ModelAndView deleteHero(@PathVariable("id") String id, ModelMap model) {
      * 		HeroDto heroDto = heroService.findByHeroId(id);
      * 		heroService.deleteHero(heroDto);
      * 		ModelAndView modelAndView = new ModelAndView();
      * 		modelAndView.setViewName("redirect:/heroes");
      * 		modelAndView.addObject("heroes", heroService.findAll());
      * 		return modelAndView;
      *    }
      *
      */
     private static Response postHeroApiCall(Hero hero, String sAuthUser, String sAuthPass){
          String sApiCall = BASE_URL + APICalls.createPostHeroApiCall();
          Response response = null;
          try {
               Gson gson = new Gson();
               String json = gson.toJson(hero, Hero.class);
               response = RestAssured.given().auth().basic(sAuthUser,sAuthPass)
                       .headers("Content-Type", ContentType.JSON,"Accept",ContentType.JSON)
                       .body(json)
                       .when().redirects().follow(false).post(sApiCall);
          } catch (Exception e){
               Assert.fail(String.format("Exception in postHeroApiCall (username: %s), Api Call: %s", hero.getHeroName(),e.getMessage()));
          }
          return response;
     }

     /**
      * Method that POST Hero in database and check if that Hero exist
      *
      * @param hero {Hero} - Hero object
      * @param sAuthUser {String} - username
      * @param sAuthPass {String} - password
      *
      */
     public static void postHero(Hero hero, String sAuthUser,String sAuthPass){
          log.debug("postHero() " + hero.getHeroName());
          Assert.assertFalse(checkIfHeroExist(hero.getHeroName(),sAuthUser,sAuthPass),"Hero " + hero.getHeroName() + " already exist!");
          Response response = postHeroApiCall(hero,sAuthUser,sAuthPass);
          int status = response.getStatusCode();
          String sResponseBody = response.getBody().asString();
          Assert.assertEquals(status,200, "Wrong Response Status code in postHero() " + hero.getHeroName()
                  + " Response body: " + sResponseBody);
          log.debug("Hero CREATED: " + checkIfHeroExist(hero.getHeroName(),sAuthUser,sAuthPass));
     }

     /**
      * Default method that POST Hero object using admin credentials to authenticate
      *
      * @param hero {Hero} - Hero object
      *
      */
     public static void postHero(Hero hero){
          postHero(hero,sAdminUser,sAdminPassword);
     }


     //-------------------------------------------
     //----------DELETE  Hero---------------------
     //-------------------------------------------


     /**
      * Method that use DELETE API calls to DELETE Hero from database
      * and return response in JSON format. This is helper method for
      * the method below which DELETE Hero from database. It takes
      * heroName, username and password and returns Response
      */
     private static Response deleteHeroApiCall(String sHeroName, String sAuthUser, String sAuthPass){
          String sApiCall = BASE_URL + APICalls.createDeleteHeroApiCall(sHeroName);
          Response response = null;
          try {
               response = RestAssured.given().auth().basic(sAuthUser,sAuthPass)
                       .headers("Content-Type", ContentType.JSON,"Accept",ContentType.JSON)
                       .when().delete(sApiCall);
          } catch (Exception e){
               Assert.fail(String.format("Exception in deleteHeroApiCall (heroName: %s), Api Call: %s", sHeroName,e.getMessage()));
          }
          return response;
     }

     /**
      * Method that DELETE Hero from database and check if that Hero still exist
      *
      * @param sHeroName {String} - heroName of Hero that we want to DELETE
      * @param sAuthUser {String} - username
      * @param sAuthPass {String} - password
      *
      */
     public static void deleteHero(String sHeroName, String sAuthUser,String sAuthPass){
          log.debug("deleteHero() "+ sHeroName);
          Assert.assertTrue(checkIfHeroExist(sHeroName,sAuthUser,sAuthPass),"Hero " + sHeroName + " DOESN'T exist!");
          Response response = deleteHeroApiCall(sHeroName,sAuthUser,sAuthPass);
          int status = response.getStatusCode();
          String sResponseBody = response.getBody().asString();
          Assert.assertEquals(status,200, "Wrong Response Status code in deleteHero() " + sHeroName
                  + " Response body: " + sResponseBody);
          log.debug("Hero DELETED: " + !checkIfHeroExist(sHeroName,sAuthUser,sAuthPass));
     }

     /**
      * Default method that DELETE Hero from database using admin credentials to authenticate
      *
      * @param sHeroName {String} - heroName of Hero that we want to DELETE
      *
      */
     public static void deleteHero(String sHeroName){
          deleteHero(sHeroName, sAdminUser,sAdminPassword);
     }
}
