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
 * schnitstelle zur Persitence/Datenbank
 * @author Timo Laser
 */

public class PersistenceController implements Serializable{
    /**
     * Übermittelt Anfagen/Abfragen an die Datenbank
     */
    private EntityManager em;
    
    public PersistenceController(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constants.General.PROJECT_PU_NAME);
        this.em = emf.createEntityManager();
       // emf.close();
    }
    
    /**
     * Sucht nach einem Entry mit der übergebenen id in der Datenbank
     * 
     * @param id ID des Eintrags
     * @return Bei erfolg wird das entspechende Entry Objkt zurückgegeben, sonst null
     */
    public Entry findEntry(int id){
        return this.em.find(Entry.class, id);
    }
    
    /**
     * Sucht nach allen Entrys eines Nutzers mit dem übergebenen Nutzernamen in 
     * der Datenbank. Werden keine Entrys zu dem Nutzernamen gefunden wird eine 
     * leere Liste zurückgegeben
     * 
     * @param username Nutzername des Nutzers
     * @return Gibt eine Liste mit Entrys zurück, diese kann leer sein 
     */
    public List<Entry> getEntriesByMember(String username){
        List<Entry> list = new ArrayList<>();
        list.addAll(em.createQuery("SELECT e FROM Entry e WHERE e.userName = :user", Entry.class)
                .setParameter("user", username)
                .getResultList());
        
        return list;
    }
    
 /**
     * Sucht nach einem Member mit dem übergebenen Nutzernamen in der Datenbank
     * 
     * @param id ID des Eintrags
     * @return Bei erfolg wird das entspechende Member Objkt zurückgegeben, sonst null
     */
    public Member findUser(String memberName){
        return this.em.find(Member.class, memberName);
    }
    
    /**
     * Aktualisiert ein Entry in der Datenbank
     * 
     * @param e Zu aktualisierendes Entry Object
     * @return Bei Erfolg wird "SUCCESS" zurückgegeben, sons "Entry can not be merged!"
     */
    public String mergeEntry(Entry e){
        Entry eTemp = this.em.find(e.getClass(), e.getId());
        if(eTemp == null){
            return Constants.ErrorMessages.CANT_MERGE_ENTRY;
        }
        this.em.getTransaction().begin();
        this.em.merge(e);
        this.em.getTransaction().commit();
        
        return Constants.General.SUCCESS;
    }
    
    /**
     * Aktualisiert einen Nutzer in der Datenbank
     * 
     * @param u Zu aktualsierendes Member Objekt
     * @return Bei Erfolg wird "SUCCESS" zurückgegeben, sons "Member can not be merged!"
     */
     public String mergeUser(Member u){
        Member uTemp = this.em.find(u.getClass(), u.getName());
        if(uTemp == null){
            return Constants.ErrorMessages.CANT_MERGE_USER;
        }
        this.em.getTransaction().begin();
        this.em.merge(u);
        this.em.getTransaction().commit();
        
        return Constants.General.SUCCESS;
    }
    
     /**
      * Persistiert ein beilibiges Entity Object in der Datenbank
      * 
      * @param o Das Entity Object das persistiert weden soll
      * @return Bei Erfolg wird "SUCCESS" zurückgegeben, sons "Object can not be persisted!"
      */
    public String persitObject(Object o){
        this.em.getTransaction().begin();
        try{
            this.em.persist(o);
            this.em.getTransaction().commit();
        }catch(Exception ex){
            if(this.em.getTransaction().isActive()){
                this.em.getTransaction().rollback();
                return Constants.ErrorMessages.CANT_PERSIST_OBJECT;
            }
        }
        return Constants.General.SUCCESS;
    }
    
    /**
     * Entfernt ein belibiges Entity Object aus der Datenbank
     * 
     * @param o Das zu entfernende Object
     * @return Bei erfolg wird true zurückgegeben, sonst false
     */
    public boolean deleteObject(Object o){
        boolean erg = true;
        this.em.getTransaction().begin();
        try{
            this.em.remove(o);
            this.em.getTransaction().commit();
        }catch(Exception ex){ 
            erg = false;
        }
        return erg;
    }
    
    /**
     * Gibt eine Liste aller in der Datenbank stehenden Entrys zurück, werden keine 
     * Entrys gefunden ist die Liste leer
     * 
     * @return Eine List mit Entry Objekten, diese kann leer sein
     */
    public List<Entry> getAllEntries(){
        List<Entry> list = new ArrayList<>();
        //list.addAll(em.createNamedQuery("Entry.all", Entry.class).getResultList());
        
       list.addAll(em.createQuery("SELECT e FROM Entry e", Entry.class).getResultList());
        
        return list;
    }
    
     /**
     * Gibt eine Liste aller in der Datenbank stehenden Member zurück, werden keine 
     * Member gefunden ist die Liste leer
     * 
     * @return Eine List mit Member Objekten, diese kann leer sein
     */
     public List<Member> getAllUsers(){
        List<Member> list = new ArrayList<>();
        //list.addAll(em.createNamedQuery("Entry.all", Entry.class).getResultList());
        
       list.addAll(em.createQuery("SELECT m FROM Member m", Member.class).getResultList());
        
        return list;
    }
    
}
