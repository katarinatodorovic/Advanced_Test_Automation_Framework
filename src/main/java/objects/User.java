package objects;

import com.github.javafaker.Faker;
import org.testng.Assert;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String about;
    private String secretQuestion;
    private String secretAnswer;

    // due to serialization and deserialization, Long and Integer types are used
    // instead of primitive types. Because, if we use wrapper classes when e.g. 'createdAt'
    // is zero the wrapper class returns null instead of zero which is better. When as a long
    // createdAt = 0, then converted into Date it will be 1.1.1970. which is meaningless
    // in our case; variables for defining User are named as they are in JASON response
    private Long createdAt;
    private Integer heroCount;
    private List<Hero> heroes;

    /**
     * Constructor for the User because we want it to be created the particular way
     *
     */
   public User(String username, String password, String email,
                String firstName, String lastName, String about,
                String secretQuestion, String secretAnswer, Date createdAt,
                List<Hero> heroes) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        this.setCreatedAt(createdAt);
        this.heroes = heroes;
        // redundant so it won't have more or less than it really have
       // if the list is empty then it would be 0 and if it is not then
       // how many heroes there are
        this.heroCount = heroes.size();
    }
    /**
     * Constructor for the User without Hero, because when we create new user
     * there is no Hero created
     */
    private User(String username, String password, String email,
                String firstName, String lastName, String about,
                String secretQuestion, String secretAnswer,
                Long createdAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        this.setCreatedAt(createdAt);
        this.heroes = new ArrayList<>();
        this.heroCount = 0;
    }
    /**
     * Constructor for the User which is created based only on username that we
     * passed as parameter
     */
    private User(String username) {
        this.username = username;
        this.password = PropertiesUtils.getDefaultPassword();
        this.email = username + "@mail.com";
        this.firstName = createRandomFirstName();
        this.lastName = createRandomLastName();
        this.about = PropertiesUtils.getDefaultAbout();
        this.secretQuestion = PropertiesUtils.getDefaultSecretQuestion();
        this.secretAnswer = PropertiesUtils.getDefaultSecretAnswer();
        this.createdAt = null;
        this.heroes = new ArrayList<>();
        this.heroCount = 0;
    }

    //Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setClearEmail() {
        this.email = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return getFirstName()+" "+getLastName();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    /**
     * Getter for Date - if there is no date to return null, otherwise
     * to get Date from DateTimeUtils class
     */
    // It is more meaningful for the purpose of UI testing to
    // return the date when User is created instead of number
    // this way Date is independent of Time Zone and Date format
    public Date getCreatedAt() {
        if(this.createdAt == null){return null;}
        return DateTimeUtils.getDateTime(this.createdAt);
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Setter for Date if there is no date to set to null, otherwise
     * to set Date as it is in DateTimeUtils class
     */
    public void setCreatedAt(Date date) {
        if(date == null){
            this.createdAt = null;
        }else{
            this.createdAt = date.getTime();
        }
    }

    public Integer getHeroCount() {
        return heroCount;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
        this.heroCount = heroes.size();
    }

    /**
     * Method that check if Hero with specific name exist in heroes list.
     * If it finds hero with the name specified than it will return that Hero,
     * otherwise return null
     *
     * @param sHeroName {String} - Hero name to search for
     *
     * @return {Hero} - Hero instance if it is present in the heroes list
     */
    public Hero getHero(String sHeroName){
        if(heroes != null){
            for (Hero h : heroes){
                if(h.getHeroName().equals(sHeroName)){
                    return h;
                }
            }
        }
        return null;
    }

    /**
     * Method that add Hero (via UI) to the heroes list for specific user
     * check if heroes is null and if it is true, then it creates new Array list
     * check if that list doesn't contain hero that we passed as parameter and
     * create new hero based on parameter passed
     *
     * @param hero {Hero} - Hero
     */
    public void addHero(Hero hero) {
        if(heroes == null){
            heroes = new ArrayList<>();
        }
        if (!heroes.contains(hero)){
            hero.setUsername(this.username);
            heroes.add(hero);
            heroCount = heroes.size();
        } else {
            Assert.fail(String.format("User %s cannot have two heroes with the same name %s!",getUsername(),hero.getHeroName()));
        }
    }

    /**
     * Method that remove Hero (via UI) from heroes list
     */
    public void removeHero(Hero hero){
        if(heroes.contains(hero)){
            heroes.remove(hero);
            heroCount = heroes.size();
        } else {
            Assert.fail(String.format("User %s doesn't have hero with name %s!",getUsername(),hero.getHeroName()));
        }
    }
   // public void updateHero(){}

    /**
     * Method that creates new User with username passed as parameter
     * @description - it is the best if test name is passed as String parameter
     * because it is easier to debug that way and, than method will add the
     * DateTimeStamp when user is created if there is more than one user created,
     * and then it creates new User instance using constructor that takes only
     * username as parameter
     *
     * @param sUsername {String} - user name for new User
     *
     * @return {User} - new User instance
     */
    public static User createNewUniqueUser(String sUsername){
        String username = sUsername.toLowerCase() + DateTimeUtils.getDateTimeStamp();
        return  new User(username);
    }

    /**
     * Method that create new random First Name using
     * faker class
     *
     * @return {String} - random First Name
     */
    public static String createRandomFirstName(){
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    /**
     * Method that create new random Last Name using
     * faker class
     *
     * @return {String} - random Last Name
     */
    public static String createRandomLastName(){
        Faker faker = new Faker();
        return faker.name().lastName();
    }

    // right click -> Generate... -> equals and hashcode, and we choose what we want
    /**
     * @description - if we don't override equal method
     * then it will compare two object references and that information has
     * no meaning for this situation because we only want to not have two users
     * with the same username
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername());
    }

    /**
     * Like in previous case with equals we want hasCode to be
     * generated only for username
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    /**
     *
     */
    @Override
    public String toString() {
        return "User  {" +
                "Username='" + getUsername() + '\'' +
                ", Password='" + getPassword() + '\'' +
                ", Email='" + getEmail() + '\'' +
                ", Name='" + getFullName() + '\'' +
                ", About='" + getAbout() + '\'' +
                ", Secret Question='" + getSecretQuestion() + '\'' +
                ", Secret Answer='" + getSecretAnswer() + '\'' +
                ", Created at=" + getCreatedAt() +
                ", Hero Count=" + getHeroCount() +
                ", Heroes=" + getHeroes() +
                '}';
    }
}
