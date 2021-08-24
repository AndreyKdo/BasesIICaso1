/*
Clase que hace referencia a la tabla sd_owners de la base de datos de solutiondesigns
Esta clase posee dos relaciones 1 a N: los problems y los designs.
Esta clase funciona como plantilla para Spring para hacer la transformación de query a objeto y viceversa
 entre el ORM y la base de datos.
Los cambios realizados en este objeto influyen en los cambios realizados en la base de datos respecto a la relación
existente entre las tablas [sd_designs]>--[sd_owners]--<[sd_problems] ya que es el parámetro pasado a los save de Spring.

 */
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

    //para especificar que un owner tiene muchos diseños (respuesta del punto 1 del Caso)
    @OneToMany(mappedBy = "Owner",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    private List<sd_designs> ownerDesigns = new ArrayList<sd_designs>();
    //para especificar que un owner tiene muchos problemas
    @OneToMany(mappedBy = "Owner",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    private List<sd_problems> ownerProblems;

    public sd_owners() {
    }

    public void setOwnerDesigns(List<sd_designs> ownerDesigns) {
        this.ownerDesigns = ownerDesigns;
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

    public List<sd_designs> getOwnerDesigns() {
        return ownerDesigns;
    }

    //Este método addDesigns es parte de la respuesta 1 del Caso
    //se agregan los diseños desde el lado del cliente para realizar la relación 1 a N
    public void addDesigns(sd_designs Design){
        ownerDesigns.add(Design);//agrega a la lista de diseños del owner instanciados el nuevo diseño
        Design.setOwner(this);//se le setea al diseño creado el owner actual para relacionarlo de la forma N a 1
    }
    //método adicional para añadir problems al owner
    public void addProblems(sd_problems Problem){
        if(ownerProblems==null) ownerProblems = new ArrayList<>();
        ownerProblems.add(Problem);
        Problem.setOwner(this);
    }
}

