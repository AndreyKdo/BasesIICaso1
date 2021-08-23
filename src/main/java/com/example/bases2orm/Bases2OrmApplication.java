package com.example.bases2orm;

import Hibernate.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Bases2OrmApplication {

    @Bean
    public CommandLineRunner inicializar() { return args -> {
        // inicialización del pool
        System.out.println("Session del connection pooling creado");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        // obtenemos la session actualmente utilizada de hibernate
        // donde el sessionFactory es único y la session solo ocupamos la actual
        // por lo que se aplica el patrón Singleton respecto al connection pooling

        //Aquí iría la transaccion

        // Aquí podemos poner ejemplos con lo de la relacion 1 a N y dar ejemplos
        // como una insercion usando también el mismo pool
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(Bases2OrmApplication.class, args);
    }

}
