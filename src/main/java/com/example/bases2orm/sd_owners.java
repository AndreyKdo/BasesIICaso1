package com.example.bases2orm;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;

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

    //para especificar que un owner tiene muchos diseños
    @OneToMany(mappedBy = "Owner",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    private List<sd_designs> ownerDesigns;

    @OneToMany(mappedBy = "Owner",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    private List<sd_problems> ownerProblems;

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

    //se agregan los diseños desde el lado del cliente para realizar la relación 1 a N
    public void addDesigns(sd_designs Design){
        if(ownerDesigns==null) ownerDesigns = new ArrayList<>();
        ownerDesigns.add(Design);

        Design.setOwner(this);
    }
    public void addProblems(sd_problems Problem){
        if(ownerProblems==null) ownerProblems = new ArrayList<>();
        ownerProblems.add(Problem);

        Problem.setOwner(this);
    }
}

