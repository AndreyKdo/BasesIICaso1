/*
Clase para hacer referencia a la tabla sd_problems de la base de datos de solutiondesigns
Posee una relación N a 1 con sd_owners, y se asocia al campo/atributo ownerid
Esta es una de las tablas, junto con sd_designs, que son afectadas por la transacción de la instrucción 3 del Caso 1
 */

package com.example.bases2orm;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
@Entity
@Table
public class sd_problems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private BigInteger problemid;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Date creationdate;
    @Column
    private boolean active;
    //Definición de la relación N a 1 con sd_owners. Se añade el cascade como adicional
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="ownerid")//se indica que se hace la relación con la columna de ownerid
    private sd_owners Owner;

    public sd_problems(String title, String description) {
        this.title = title;
        this.description = description;
        this.creationdate = new Date();
    }

    public sd_problems() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public sd_owners getOwner() {
        return Owner;
    }

    public void setOwner(sd_owners owner) {
        //este setter es de suma importancia ya que es llamado desde el owner para hacer la relación correspondiente
        Owner = owner;
    }
}
