package com.example.bases2orm;


import Hibernate.Util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface sd_owners_repository extends JpaRepository <sd_owners, BigInteger> {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();


    sd_owners findByEmail(String email);//para buscar por el email a un owner
}