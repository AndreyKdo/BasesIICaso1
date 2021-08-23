package Hibernate.Util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory factory;

        private static SessionFactory buildFactory() {
            // ServiceRegistry da a Hibernate la capacidad de agregar las configuraciones del archivo
            // hibernate.cfg.xml que contiene la información para el connection pool
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                    configure("hibernate.cfg.xml").build();

            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        }

        public static SessionFactory getSessionFactory() {
            if(factory == null) factory = buildFactory();
            return factory;
        }
    }
