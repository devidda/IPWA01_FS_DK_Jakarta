package de.iu.jakarta_jsf.persistence;

import jakarta.persistence.*;

@Entity
public class UserEntity {

    public static final String COLUMN_MAIL_ADDRESS = "mailAddress";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = COLUMN_MAIL_ADDRESS, unique = true)
    private String mailAddress;

    private String password;

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
}
