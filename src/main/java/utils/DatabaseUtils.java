package utils;

import objects.DatabaseHero;
import objects.DatabaseUser;
import objects.Hero;
import objects.User;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.testng.Assert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Handles all things related to database e.g. queries, connection ...
 */
public class DatabaseUtils extends LoggerUtils{

    public static final String sDataSourceUrl = PropertiesUtils.getDataSourceUrl();
    public static final String sRootUsername = PropertiesUtils.getRootUsername();
    public static final String sRootPassword = PropertiesUtils.getRootPassword();

    // In case we need this if communication with database
    // is not established just with jar
    // public static final String sDatabaseDriver = PropertiesUtils.getDatabaseDriver();

    /*
     * A single data - ony one field/cell
     * SELECT username FROM users WHERE username = 'filet'
     * ScalarHandler<String> handler = new ScalarHandler<>();
     * it forms a response the similar way to Gson - serialize/deserialize,
     * the same way it is with DBUtils. The ScalarHandler convert one type of data
     * like String, Long.... and we specified which type of date we get
     * We use ScalarHandler when we need content of one field from DB
     *
     * A set of data - all data from one column
     * SELECT username FROM users
     * ColumnListHandler<String> columnHandler = new ColumnListHandler<>()
     * if we need to get multiple elements, and we use a list of elements of the same type
     * we specified which type of date we get
     *
     * The content of a row or part of a row
     * SELECT * FROM users WHERE username = 'filet'
     * ResultSetHandler<DatabaseUser> = new BeanHandler<>(DatabaseUser.class)
     * if we are NOT looking for the content of one cell or a set of data of the same
     * type, but if we are looking for different types of data, and it doesn't matter if
     * it is simple or a complex query; the only thing that matters is that it is different type of
     * data and e.g. this way we can map DatabaseUser as we did with API user
     *
     * The content of multiple rows - List of list
     * SELECT * FROM users
     * SELECT * FROM heroes WHERE fk_user_id = 3
     * ResultSetHandler<List<DatabaseUser>> listHandler = new BeanListHandler<>(DatabaseUser.class);
     * It can be created a list of complex data that is e.g. <DatabaseUser>
     * or a list of heroes that belong to one user
     */

    /*
     * userID is in database defined as VARCHAR which is String, and we get
     * User ID of some known user SELECT user_id FROM users WHERE username = 'filet'
     * Connection is established via Connection manager and query return the type of data
     * depending on which handler we use. Scalar in our case returns String.
     * If something went wrong we need to terminate connection (what if there is 50 opened connection)
     * in finally.
     * Question mark indicate parameters and how many question marks we need that many parameters
     * The parameters are replaced according to the order
     *
     * Exception in method getUserID() with filet username while trying to connect to database. Message:
     * No suitable driver found for jdbc:mysql://localhost:3306/samsara!
     *
     * When we get this error then we are missing a driver, like we use driver in TestNG there
     * is driver for Database and every database has its own driver. Because we use MySQL database
     * on SamsaraApp we need jar which is for that MySQL (jar MySQL Connector/J » 8.0.29) database
     * in some cases adding jar won't be enough, and we need to add Class.forName("com.mysql.jdbc.Driver")
     * and to explicitly declare driver type
     */

    /**
     * Method that returns specific user's ID from user's table
     * @description - try to connect to database, enters the query and store query in
     * String and try to close connection
     * query - SELECT user_id FROM users WHERE username = ?
     *
     * @param sUsername {String} - User's username
     *
     * @return {String} - user's ID
     */
    public static String getUserID(String sUsername){
        log.trace("getUserID " + sUsername);
        String sqlQuery = "SELECT user_id FROM users WHERE username = ?";
        Connection connection = null;
        QueryRunner run = new QueryRunner();
        ScalarHandler<String> handler = new ScalarHandler<>();
        //In case we need this if communication with database
        // is not established just with jar
        // Class.forName(sDatabaseDriver); first solution
        // DbUtils.loadDriver(sDatabaseDriver); second solution
        String result = null;

        try {
            connection = DriverManager.getConnection(sDataSourceUrl,sRootUsername,sRootPassword);
            result = run.query(connection,sqlQuery,handler,sUsername);

        } catch (SQLException e) {
            Assert.fail(String.format("Exception in method getUserID() with %s username while trying to connect to database. Message: %s!",sUsername,e.getMessage()));
        } finally {
            // if connection still existing and is not closed, close te connection
            try{
                if (connection!=null){
                    if(!connection.isClosed()){
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e){
                Assert.fail(String.format("Exception in method getUserID() with %s username while trying to close connection to database. Message: %s!",sUsername,e.getMessage()));
            }
        }
        return result;
    }

    /**
     * Method that returns username from user's table
     * @description - try to connect to database, enters the query and store query in
     * String and try to close connection
     * query - SELECT user_id FROM users WHERE userID = ?
     *
     * @param sUserID {String} - User's username
     *
     * @return {String} - user's usernames
     */
    public static String getUsername(String sUserID){
        log.trace("getUsername " + sUserID);
        String sqlQuery = "SELECT username FROM users WHERE user_id = ?";
        Connection connection = null;
        QueryRunner run = new QueryRunner();
        ScalarHandler<String> handler = new ScalarHandler<>();
        String result = null;

        try {
            connection = DriverManager.getConnection(sDataSourceUrl,sRootUsername,sRootPassword);
            result = run.query(connection,sqlQuery,handler,sUserID);

        } catch (SQLException e) {
            Assert.fail(String.format("Exception in method getUsername with %s username while trying to connect to database. Message: %s!",sUserID,e.getMessage()));
        } finally {
            // if connection still existing and is not closed, close te connection
            try{
                if (connection!=null){
                    if(!connection.isClosed()){
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e){
                Assert.fail(String.format("Exception in method getUsername with %s username while trying to close connection to database. Message: %s!",sUserID,e.getMessage()));
            }
        }
        return result;
    }

    /**
     * Method that returns list of user's usernames
     * @description - try to connect to database, enters the query and store query in
     * List<String> and try to close connection
     * query - SELECT username FROM users
     *
     * @return {List} - list of user's usernames
     */
    public static List<String> getAllUsernames(){
        log.trace("getAllUsernames() ");
        String sqlQuery = "SELECT username FROM users";
        Connection connection = null;
        QueryRunner run = new QueryRunner();
        ColumnListHandler<String> handler = new ColumnListHandler<>();
        List <String> result = null;

        try {
            connection = DriverManager.getConnection(sDataSourceUrl,sRootUsername,sRootPassword);
            result = run.query(connection,sqlQuery,handler);

        } catch (SQLException e) {
            Assert.fail(String.format("Exception in method getAllUsernames() while trying to connect to database. Message: %s!",e.getMessage()));
        } finally {
            // if connection still existing and is not closed, close te connection
            try{
                if (connection!=null){
                    if(!connection.isClosed()){
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e){
                Assert.fail(String.format("Exception in method getAllUsernames() while trying to close connection to database. Message: %s!",e.getMessage()));
            }
        }
        return result;
    }

    /**
     * Method that returns DatabaseUser instance from user's table recognise and assemble
     * data into DatabaseUser, so it can return an instance of DatabaseUser
     * @description - try to connect to database, enters the query and store query in
     * String and try to close connection
     * query - SELECT * FROM users WHERE username = ?
     *
     * @param sUsername {String} - User's username
     *
     * @return {DatabaseUser} - DatabaseUser user
     */
    public static DatabaseUser getDatabaseUser(String sUsername){
        log.trace("getDatabaseUser " + sUsername);
        String sqlQuery = "SELECT * FROM users WHERE username = ?";
        Connection connection = null;
        QueryRunner run = new QueryRunner();
        ResultSetHandler<DatabaseUser> handler = new BeanHandler<>(DatabaseUser.class);
        DatabaseUser result = null;

        try {
            connection = DriverManager.getConnection(sDataSourceUrl,sRootUsername,sRootPassword);
            result = run.query(connection,sqlQuery,handler,sUsername);

        } catch (SQLException e) {
            Assert.fail(String.format("Exception in method getDatabaseUser with %s username while trying to connect to database. Message: %s!",sUsername,e.getMessage()));
        } finally {
            // if connection still existing and is not closed, close te connection
            try{
                if (connection!=null){
                    if(!connection.isClosed()){
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e){
                Assert.fail(String.format("Exception in method getDatabaseUser with %s username while trying to close connection to database. Message: %s!",sUsername,e.getMessage()));
            }
        }
        return result;
    }

    /**
     * Method that assemble user based on data that gets from databaseUser
     * and return an instance of the User's class
     *
     * @param sUsername {String} - user's username
     *
     * @return {User} - an instance of User
     */
    public static User getUser(String sUsername){
        log.trace("getUser " + sUsername);
        DatabaseUser databaseUser = getDatabaseUser(sUsername);
        return assembleUser(databaseUser);
    }

    /**
     * Method that create an instance of the Hero class based on database hero
     *
     * @param databaseHero {DatabaseHero} - DatabaseHero instance
     *
     * @return {Hero} - an instance of Hero
     */
    private static Hero assembleHero(DatabaseHero databaseHero){
        String sUsername = getUsername(databaseHero.getFK_user_id());
        Hero hero = new Hero(databaseHero.getName(),databaseHero.getType(),databaseHero.getLevel(),sUsername,databaseHero.getCreated());
        return  hero;
    }

    /**
     * Method that create an instance of the User class based on database User
     *
     * @param databaseUser {DatabaseUser} - DatabaseUser instance
     *
     * @return {User} - an instance of User
     */
    private static User assembleUser(DatabaseUser databaseUser){
        String sUsername= databaseUser.getUsername();
        String sPassword= databaseUser.getPassword();
        String sEmail= databaseUser.getEmail();
        String sFirstName= databaseUser.getFirstName();
        String sLastname= databaseUser.getLastName();
        String sAbout= databaseUser.getAbout();
        String sSecretQuestion= databaseUser.getSecretQuestion();
        String sSecretAnswer= databaseUser.getSecretAnswer();
        Date createdAt = databaseUser.getCreated();
        List<DatabaseHero> databaseHeroes = getDatabaseHeroesForUser(databaseUser.getUserID());
        List<Hero> heroes = new ArrayList<>();

        for (DatabaseHero databaseHero : databaseHeroes){
            heroes.add(assembleHero(databaseHero));
        }
        User user = new User(sUsername,sPassword,sEmail,sFirstName,sLastname,sAbout,sSecretQuestion,sSecretAnswer,createdAt,heroes);
        return user;
    }

    /**
     * Method that returns a list of user's heroes, it recognises and assemble
     * data into DatabaseUser, so it can return a list of an instances of DatabaseHero
     * @description - try to connect to database, enters the query and store query in
     * List<String> and try to close connection
     * query - SELECT username FROM users
     *
     * @param sUserID {String} - User's ID
     *
     * @return {List} - list of user's heroes
     */
    public static List<DatabaseHero> getDatabaseHeroesForUser(String sUserID){
        log.trace("getDatabaseHeroesForUser() " + sUserID );
        String sqlQuery = "SELECT * FROM heroes WHERE fk_user_id = ?";
        Connection connection = null;
        QueryRunner run = new QueryRunner();
        ResultSetHandler<List<DatabaseHero>> handler = new BeanListHandler<>(DatabaseHero.class);
        List<DatabaseHero> result = null;

        try {
            connection = DriverManager.getConnection(sDataSourceUrl,sRootUsername,sRootPassword);
            result = run.query(connection,sqlQuery,handler,sUserID);

        } catch (SQLException e) {
            Assert.fail(String.format("Exception in method getDatabaseHeroesForUser with %s username while trying to connect to database. Message: %s!",sUserID,e.getMessage()));
        } finally {
            // if connection still existing and is not closed, close te connection
            try{
                if (connection!=null){
                    if(!connection.isClosed()){
                        DbUtils.close(connection);
                    }
                }
            } catch (SQLException e){
                Assert.fail(String.format("Exception in method getDatabaseHeroesForUser with %s username while trying to close connection to database. Message: %s!",sUserID,e.getMessage()));
            }
        }
        return result;
    }
}
