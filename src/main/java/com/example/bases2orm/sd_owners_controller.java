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
@RequestMapping(path = "/api")
public class sd_owners_controller {
    @Autowired
    private final sd_owners_services owners_services;

    public sd_owners_controller(sd_owners_services controllerOwner) {
        this.owners_services = controllerOwner;
    }
    @GetMapping(path = "/add")
    public void add(@RequestParam("pfirstname") String pfirstname,
                    @RequestParam("plastname") String plastname,
                    @RequestParam("pemail") String pemail,
                    @RequestParam("ppassword") byte[] ppassword,
                    @RequestParam("penabled") boolean penabled,
                    @RequestParam("pcreationdate") Date pcreationdate){
        sd_owners Owners = new sd_owners(pfirstname,plastname,pemail,ppassword,penabled,pcreationdate);
        owners_services.addThings(Owners);
    }
    @GetMapping(path = "/select")
    public List<sd_owners> traigaTodo(){
        return owners_services.demeTodo();
    }

    //método para añadir un diseño más de un Owner
    @GetMapping(path = "/adddesign")
    public void addDesign(@RequestParam("ptitle") String ptitle,
                          @RequestParam("pdescription") String pdescription,
                          @RequestParam("powneremail") String powneremail){

        sd_owners Owner = owners_services.findByEmail(powneremail);
        sd_designs Ownerdesign = new sd_designs(ptitle,pdescription);
        Owner.addDesigns(Ownerdesign);
        owners_services.save(Owner);
    }
    //método para añadir un problema más de un Owner
    @GetMapping(path = "/addproblem")
    public void addProblem(@RequestParam("ptitle") String ptitle,
                           @RequestParam("pdescription") String pdescription,
                           @RequestParam("powneremail") String powneremail){

        sd_owners Owner = owners_services.findByEmail(powneremail);
        sd_problems Ownerproblem = new sd_problems(ptitle,pdescription);
        Owner.addProblems(Ownerproblem);
        owners_services.save(Owner);
    }
    @GetMapping(path = "/addproblemAndDesign")
    public void addOwnerProblemDesign(
            @RequestParam("designtitle") String designtitle,
            @RequestParam("designdescription") String designdescription,
            @RequestParam("problemtitle") String problemtitle,
            @RequestParam("problemdescription") String problemdescription,
            @RequestParam("powneremail") String powneremail) throws SystemException {
        sd_designs Ownerdesign = new sd_designs(designtitle,designdescription);
        sd_problems Ownerproblem = new sd_problems(problemtitle,problemdescription);
        sd_owners Owner = owners_services.findByEmail(powneremail);
        owners_services.addOwnerProblemDesigns(Owner,Ownerproblem,Ownerdesign);
    }
}
