package objects;

import java.util.Date;

public class DatabaseHero {
    private String hero_id;
    private String name;
    private String type;
    private Integer level;
    private Date created;
    private String fk_user_id;


    // Getters and Setters

    public String getHero_id() {
        return hero_id;
    }

    public void setHero_id(String hero_id) {
        this.hero_id = hero_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getFK_user_id() {
        return fk_user_id;
    }

    public void setFk_user_id(String fk_user_id) {
        this.fk_user_id = fk_user_id;
    }

    @Override
    public String toString() {
        return "DatabaseHero{" +
                "hero_id='" + hero_id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", level=" + level +
                ", created=" + created +
                ", fk_user_id='" + fk_user_id + '\'' +
                '}';
    }
}
