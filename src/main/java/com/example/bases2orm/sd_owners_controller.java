package com.example.bases2orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class sd_owners_controller {
    @Autowired
    private final sd_owners_services controllerOwner;

    public sd_owners_controller(sd_owners_services controllerOwner) {
        this.controllerOwner = controllerOwner;
    }
    @GetMapping(path = "/add")
    public void add(@RequestParam("pfirstname") String pfirstname,
                    @RequestParam("plastname") String plastname,
                    @RequestParam("pemail") String pemail,
                    @RequestParam("ppassword") byte[] ppassword,
                    @RequestParam("penabled") boolean penabled,
                    @RequestParam("pcreationdate") Date pcreationdate){
        sd_owners Owners = new sd_owners(pfirstname,plastname,pemail,ppassword,penabled,pcreationdate);
        controllerOwner.addThings(Owners);
    }
    @GetMapping(path = "/select")
    public List<sd_owners> traigaTodo(){
        return controllerOwner.demeTodo();
    }
}
