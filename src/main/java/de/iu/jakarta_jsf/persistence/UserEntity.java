package de.iu.jakarta_jsf.persistence;

import jakarta.persistence.*;

/**
 * Class to store data of the user of the application. As a JPA Entity, it can be stored in the database.
 * Accessed and processed for validation in {@link de.iu.jakarta_jsf.services.AuthServiceImpl}.
 */
@Entity
@Table(name = "User")
public class UserEntity {

    public static final String COLUMN_MAIL_ADDRESS = "mailAddress";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ADMIN = "admin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = COLUMN_MAIL_ADDRESS, unique = true)
    private String mailAddress;
    @Column(name = COLUMN_PASSWORD)
    private String password;

    @Column(name = COLUMN_ADMIN)
    private Boolean isAdmin = false;

    /**
     * getter and setter
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        if (isAdmin == null) return false;
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
