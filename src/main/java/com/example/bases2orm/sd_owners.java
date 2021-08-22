package com.example.bases2orm;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
@Entity
@Table


public class sd_owners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private BigInteger ownerid;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String email;
    @Column
    private byte[] password;
    @Column
    private boolean enabled;
    @Column
    private Date creationdate;

    public sd_owners() {
    }

    public sd_owners(String firstname, String lastname, String email, byte[] password, boolean enabled, Date creationdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.creationdate = creationdate;
    }


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }
}

