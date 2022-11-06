package objects;

import com.github.javafaker.Faker;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import utils.DateTimeUtils;
import utils.HeroClass;
import java.util.Date;
import java.util.Objects;

public class Hero {
    // When we use Gson we can set the name of variable to be something that is
    // more readable and appropriate, and we can use annotation @SerializedName above
    // variable. The parameter (value) of this annotation is the name to be used when
    // serialising and deserializing objects -> to be easier recognised by Gson

    // Array of Hero classes that is used to create random Hero class using faker
    private static final String[] heroClasses = {HeroClass.WARRIOR, HeroClass.GUARDIAN, HeroClass.REVENANT,
    HeroClass.ENGINEER, HeroClass.THIEF, HeroClass.RANGER, HeroClass.ELEMENTALIST, HeroClass.NECROMANCER,
    HeroClass.MESMER};
    @Expose
    @SerializedName("name")
    private String heroName;

    @Expose
    @SerializedName("type")
    private String heroClass;

    @Expose
    @SerializedName("level")
    private Integer heroLevel;

    @Expose
    private String username;

    @Expose
    private Long createdAt;

    /**
     * Constructor for the Hero class, when we already have a hero that
     * is created in database and belongs to some user
     */
    public Hero(String heroName, String heroClass, int heroLevel,
                String username, Date createdAt) {
        this.setHeroName(heroName);
        this.setHeroClass(heroClass);
        this.setHeroLevel(heroLevel);
        this.setUsername(username);
        this.setCreatedAt(createdAt);
    }

    /**
     * Constructor for the Hero class, which is not created in database,
     * but we know which user will be creating that hero
     */
    private Hero(String heroName, String heroClass, int heroLevel,
                String username) {
        this.setHeroName(heroName);
        this.setHeroClass(heroClass);
        this.setHeroLevel(heroLevel);
        this.setUsername(username);
        this.setCreatedAt(null);
    }

    /**
     * Constructor for the Hero class when we have only
     * these three information
     */
    public Hero(String heroName, String heroClass, int heroLevel) {
        this.setHeroName(heroName);
        this.setHeroClass(heroClass);
        this.setHeroLevel(heroLevel);
        this.setUsername(null);
        this.setCreatedAt(null);
    }

    // Getters and Setters
    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }

    public Integer getHeroLevel() {
        return heroLevel;
    }

    public void setHeroLevel(Integer heroLevel) {
        this.heroLevel = heroLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for Date if there is no date to return null, otherwise
     * to get Date from DateTimeUtils class
     */
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

    /**
     * Method that creates new Hero with User instance, HeroName, HeroClass and
     * HeroLevel as parameter
     * @description - it is the best if test name is passed as String parameter
     * for HeroName because it is easier to debug that way and, than method will add the
     * DateTimeStamp when Hero is created if there is more than one Hero created,
     * and then it creates new Hero instance
     *
     * @param user {User} - User that will create Hero
     * @param sHeroName {String} - Hero name
     * @param sHeroClass {String} - Hero class
     * @param iHeroLevel {int} - Hero level
     *
     * @return {User} - new User instance
     */
    public static Hero createNewUniqueHero(User user, String sHeroName, String sHeroClass, int iHeroLevel){
        String heroName = sHeroName + DateTimeUtils.getDateTimeStamp();
        return  new Hero(heroName,sHeroClass,iHeroLevel,user.getUsername());
    }

    /**
     * Method that create new Unique Hero using faker class
     *
     * @param user {User} - User that will create Hero
     * @param sHeroName {String} - Hero name
     *
     * @return {String} - random First Name
     */
    public static Hero createNewUniqueHero(User user, String sHeroName){
        String sHeroClass = createRandomHeroClass();
        int iHeroLevel = createRandomHeroLevel();
        return createNewUniqueHero(user,sHeroName,sHeroClass,iHeroLevel);
    }


    /**
     * Method that create new random Hero level using
     * faker class
     *
     * @return {String} - random Hero level
     */
    public static int createRandomHeroLevel(){
        Faker faker = new Faker();
        return faker.random().nextInt(0,80);
    }

    /**
     * Method that create new random Hero class using
     * faker class
     *
     * @return {String} - random Hero class
     */
    public static String createRandomHeroClass(){
        Faker faker = new Faker();
        int i = faker.random().nextInt(0,heroClasses.length-1);
        return heroClasses[i];
    }

    /**
     * Equals that compare only if two instance of Hero object have
     * the same name and return true if they have
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return Objects.equals(getHeroName(),hero.getHeroName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeroName());
    }

    @Override
    public String toString() {
        return "Hero{" +
                "Hero Name='" + getHeroName() + '\'' +
                ", Hero Class='" + getHeroClass() + '\'' +
                ", Hero Level=" + getHeroLevel() +
                ", Username='" + getUsername() + '\'' +
                ", Created At=" + getCreatedAt() +
                '}';
    }
}
