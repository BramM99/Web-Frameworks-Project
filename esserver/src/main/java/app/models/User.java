package app.models;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "eMail")
})
@NamedQueries({
        @NamedQuery(name = "User_find_by_email", query = "SELECT u FROM User u WHERE u.eMail= ?1"),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String eMail;

    private String hashedPassWord;

    private boolean admin;

    private boolean active;

    public User() {
    }


    public User(long id, String name, String eMail, String hashedPassWord, boolean admin, boolean active) {
        this.id = id;
        this.name = name;
        this.eMail = eMail;
        this.hashedPassWord = hashedPassWord;
        this.admin = admin;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


    public void setHashedPassWord(String hashedPassWord) {
        this.hashedPassWord = hashedPassWord;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getHashedPassWord() {
        return hashedPassWord;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eMail='" + eMail + '\'' +
                ", hashedPassWord='" + hashedPassWord + '\'' +
                ", admin=" + admin +
                '}';
    }
}
