/*
Clase que se encarga de traficar las consultas e inserciones solicitadas desde el Browser.
 */
package com.example.bases2orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api")//directorio principal
public class sd_owners_controller {
    @Autowired
    private final sd_owners_services owners_services;

    public sd_owners_controller(sd_owners_services controllerOwner) {
        this.owners_services = controllerOwner;
    }
    @GetMapping(path = "/add")//para añadir un owner
    public void add(@RequestParam("pfirstname") String pfirstname,
                    @RequestParam("plastname") String plastname,
                    @RequestParam("pemail") String pemail,
                    @RequestParam("ppassword") byte[] ppassword,
                    @RequestParam("penabled") boolean penabled,
                    @RequestParam("pcreationdate") Date pcreationdate){
        sd_owners Owners = new sd_owners(pfirstname,plastname,pemail,ppassword,penabled,pcreationdate);
        owners_services.addThings(Owners);
    }
    @GetMapping(path = "/select")//para hacer un select de todos los owners
    public List<sd_owners> traigaTodo(){
        return owners_services.demeTodo();
    }

    //**************** Método respuesta a la instrucción 1 del Caso 1 ***********************//
    //método para añadir un diseño más de un Owner
    @GetMapping(path = "/adddesign")//directorio http://localhost:8080/api/adddesign?
    public void addDesign(@RequestParam("ptitle") String ptitle,
                          @RequestParam("pdescription") String pdescription,
                          @RequestParam("powneremail") String powneremail){
        //se obtiene el owner que se le debe asociar al diseño mediante el campo de email
        //findByEmail es un método abstracto, ya implementado por Spring
        sd_owners Owner = owners_services.findByEmail(powneremail);
        sd_designs Ownerdesign = new sd_designs(ptitle,pdescription);//se crea el diseño según los parámetros
        Owner.addDesigns(Ownerdesign); //se llama a la instancia de Owner para realizar la relación 1 a N
        owners_services.save(Owner); //se guardan los cambios en la base de datos
    }
    //**************** Método respuesta a la instrucción 3 del Caso 1 ***********************//
    //método para llevar a cabo la transacción. La misma se ejecuta en la clase sd_owners_services
    @GetMapping(path = "/addproblemAndDesign")//directorio http://localhost:8080/api/addproblemAndDesign?
    public void addOwnerProblemDesign(
            @RequestParam("designtitle") String designtitle,
            @RequestParam("designdescription") String designdescription,
            @RequestParam("problemtitle") String problemtitle,
            @RequestParam("problemdescription") String problemdescription,
            @RequestParam("powneremail") String powneremail) throws SystemException {//manejo de excepciones debido a la comunicación que posee con la transacción
        //se instancias los objetos a ser insertados en ambas tablas
        sd_designs Ownerdesign = new sd_designs(designtitle,designdescription);
        sd_problems Ownerproblem = new sd_problems(problemtitle,problemdescription);
        //se obtiene el owner que tiene la relación 1 a N con los objetos anteriores
        sd_owners Owner = owners_services.findByEmail(powneremail);
        owners_services.addOwnerProblemDesigns(Owner,Ownerproblem,Ownerdesign);//llamada a la transacción en sd_owners_services
    }
}
