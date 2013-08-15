/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Objetos.Casa;
import Objetos.Cliente;
import Objetos.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author luz
 */
public class ManejadorDatos {
    
    private static ManejadorDatos instanciaMC=null;
    
    // SINGLETON
    public static ManejadorDatos getInstance(){
        if (instanciaMC == null)
            instanciaMC = new ManejadorDatos();
        return instanciaMC;
    }
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public ManejadorDatos() {
        //para probar vamos a crear algunos clientes y casa
        if (emf == null) {
            this.emf = Persistence.createEntityManagerFactory("LosLagos_v1.1PU");
            this.em = emf.createEntityManager();
        }else if (!this.emf.isOpen()){
            this.emf = Persistence.createEntityManagerFactory("LosLagos_v1.1PU");
            this.em = emf.createEntityManager();
        }
    }
    //clase encargada de administrar los datos de la base de datos
    //datos relacionados a los clietes.

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
 
    public void closeEm(){
        if (this.em != null && em.isOpen())
            this.em.close();
        if (this.emf != null && em.isOpen())
            this.emf.close();
    }
    

    public void persist(Object object) {
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
        }
    }


    
}