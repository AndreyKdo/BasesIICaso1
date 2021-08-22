package com.example.bases2orm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class sd_owners_services {
    @Autowired
    private final sd_owners_repository repoOwner;

    public sd_owners_services(sd_owners_repository repoOwner) {
        this.repoOwner = repoOwner;
    }

    public void addThings(sd_owners pOwner){
        repoOwner.save(pOwner);
    }
    public List<sd_owners> demeTodo(){
        return repoOwner.findAll();
    }
}
