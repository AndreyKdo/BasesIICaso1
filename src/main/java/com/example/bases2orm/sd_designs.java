/*
Clase/tabla sd_designs para añadir un desing de una relación 1 a N respecto a sd_owners
En esta clase se agrega el ManyToOne y se asocia con el ownerid de la tabla sd_owners

 */
package com.example.bases2orm;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
@Entity
@Table
public class sd_designs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private BigInteger designid;
    @Column
    private String title;
    @Column
    private String description;
    //se especifica que la tabla sd_designs se relaciona con un owner y con las cascades se indica que no se debe eliminar nada
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="ownerid")//se indica que se hace la relación con la columna de ownerid
    private sd_owners Owner;
    @Column
    private Date creationdate;
    @Column
    private Date publishdate;
    @Column
    private boolean active;
    @Column
    private BigInteger parentdesignid;//modificación realizada en el quiz (null por defecto)

    public sd_designs(String title, String description) {
        this.title = title;
        this.description = description;
        this.creationdate = new Date();;
    }

    public sd_designs() {

    }

    public sd_owners getOwner() {
        return Owner;
    }

    public void setOwner(sd_owners owner) {
        Owner = owner;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigInteger getParentdesignid() {
        return parentdesignid;
    }

    public void setParentdesignid(BigInteger parentdesignid) {
        this.parentdesignid = parentdesignid;
    }
}
