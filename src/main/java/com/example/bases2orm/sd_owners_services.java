package com.example.bases2orm;

import Hibernate.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
    }
    public List<sd_owners> demeTodo(){
        return repoOwner.findAll();
    }
    public sd_owners findByEmail(String email){return repoOwner.findByEmail(email);}
    //para guardar cambios según el Owner. Aplica para cuando se le agrega un nuevo design asociado
    public void save(sd_owners owner) {
        repoOwner.save(owner);
    }
}
