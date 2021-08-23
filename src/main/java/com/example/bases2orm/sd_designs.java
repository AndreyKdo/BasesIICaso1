/*
Clase que hace referencia a la tabla sd_designs de la base de datos solutiondesigns
para añadir un desing de una relación 1 a N respecto a sd_owners
En esta clase se agrega el ManyToOne y se asocia con el ownerid de la tabla sd_owners
Esta clase es la respuesta al punto 1 del Caso 1
Esta es una de las tablas, junto con sd_problems, que son afectadas por la transacción de la instrucción 3 del Caso 1
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
    private BigInteger parentdesignid;//modificación realizada en la base de datos original de solutiondesigns debido al quiz 1 (null por defecto)

    public sd_designs(String title, String description) {
        this.title = title;
        this.description = description;
        this.creationdate = new Date();//la fecha se setea con el objeto propio de Java. Se evita ser pasado por parámetro
    }

    public sd_designs() {

    }

    public sd_owners getOwner() {
        return Owner;
    }

    //setOwner es llamado desde la instancia sd_owners que se le está asociando
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
