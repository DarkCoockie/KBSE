/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Timo Laser
 */

public class PersistenceController implements Serializable{
    private EntityManager em;
    
    
    public PersistenceController(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("KBSE_ProjektPU");
        this.em = emf.createEntityManager();
       // emf.close();
    }
    
    public Entry findEntry(int id){
        return this.em.find(Entry.class, id);
    }
    
    public String mergeEntry(Entry e){
        Entry eTemp = this.em.find(e.getClass(), e.getId());
        if(eTemp == null){
            return Constants.ErrorMessages.CANT_MERGE_ENTRY;
        }
        this.em.getTransaction().begin();
        this.em.merge(e);
        this.em.getTransaction().commit();
        
        return Constants.Constants.SUCCESS;
    }
    
    public String addEntry(Entry e){
        this.em.getTransaction().begin();
        try{
            this.em.persist(e);
            this.em.getTransaction().commit();
        }catch(Exception ex){
            if(this.em.getTransaction().isActive()){
                this.em.getTransaction().rollback();
                return Constants.ErrorMessages.CANT_PERSIST_ENTRY;
            }
        }
        return Constants.Constants.SUCCESS;
    }
    
    public boolean deleteEntry(Entry e){
        boolean erg = true;
        this.em.getTransaction().begin();
        try{
            this.em.remove(e);
            this.em.getTransaction().commit();
        }catch(Exception ex){ 
            erg = false;
        }
        return erg;
    }
    
    public List<Entry> getAllEntrys(){
        List<Entry> list = new ArrayList<>();
        //list.addAll(em.createNamedQuery("Entry.all", Entry.class).getResultList());
        
       list.addAll(em.createQuery("SELECT e FROM Entry e", Entry.class).getResultList());
        
        return list;
    }
    
}
