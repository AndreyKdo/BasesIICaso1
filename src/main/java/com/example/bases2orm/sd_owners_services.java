package com.example.bases2orm;

import Hibernate.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class sd_owners_services {
    @Autowired
    private final sd_owners_repository repoOwner;//instancia del repositorio (interfaz para hacer consultas con Spring)

    public sd_owners_services(sd_owners_repository repoOwner) {
        this.repoOwner = repoOwner;
    }
    //métodos de prueba para inserción y consulta
    public void addThings(sd_owners pOwner){
            repoOwner.save(pOwner);
    }
    public List<sd_owners> demeTodo(){
        return repoOwner.findAll();
    }
    //método abstracto encargado de obtener un owner por el email
    public sd_owners findByEmail(String email){return repoOwner.findByEmail(email);}
    //para guardar cambios según el Owner. Aplica para cuando se le agrega un nuevo design o problema asociado
    public void save(sd_owners owner) {
        repoOwner.save(owner);
    }
    //******************** Transacción respuesta del punto 3 del Caso 1 *********************************//
    //se hace uso del connection pooling management de Hibernate
    @Transactional//se indica a Spring que debe ser considerado como transacción
    public void addOwnerProblemDesigns(sd_owners pOwner,sd_problems pProblem,sd_designs pDesign) throws SystemException {
        Transaction tran = null;//se inicializa la transacción como no existente

        try{
            //Se obtiene el pooling y la sesión asociada a la instancia que desea ejecutar la transacción
            //*referenciarse a Hibernate.util e hibernate.cfg, así como a las dependencias del pom.xml
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            tran = session.beginTransaction();//se inicia la transacción, a partir de aquí puede que haya rollback
            //se llama a los métodos del owner para asociarle el nuevo design y el nuevo problem obtenidos
            pOwner.addDesigns(pDesign);
            pOwner.addProblems(pProblem);
            session.save(pOwner);//se guardan los cambios en la base de datos
            tran.commit();
        }catch (Exception e){
            if (tran!=null) tran.rollback();
            System.out.println("Error during transaction: "+e.toString());
        }
    }
}
