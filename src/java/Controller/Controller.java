/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Persistence.Member;
import Persistence.PersistenceController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 * Der Controller übernimmt die Haupt Buissnis Logic der Anwendung und ist damit
 * zentrale Schnistelle zwischen Frontend und Persistence
 * 
 * @author Marcel Schulte und Timo Laser
 */
@Named
@Dependent
public class Controller implements Serializable {
    /**
     * List aller Eniträge. 
    */
    private List<Persistence.Entry> entries;
    
    /**
     * Lsite aller Nutzer
     */
    private List<Persistence.Member> users;
    
    /**
     * Hauptschnitstelle zur Persetierung. Übernimmt Anfragen zum lesen und scheiben an
     * die Datenbank.
     */
    private PersistenceController pc = new PersistenceController();
    
    /**
     *  Initialisiert alle Variablen nach dem das Objekt erzeugt wurde.
     */
    @PostConstruct
    private void init(){
        this.entries = new ArrayList<>();
        this.users = new ArrayList<>();
    }
    /**
     * Aktualisiert die Liste der Eintrage mit den aktuellen Daten aus der Datenbank
     * 
     * @return Bei Erfolg wird "SUCCESS" zurück gegeben, bei fehlschalg "Could not get entries!".
     */
    private String updateEntryList(){
        List<Persistence.Entry> entries = pc.getAllEntries();
        if(entries != null)
        {
            this.entries = entries;
        }
        else
        {
            return Constants.ErrorMessages.CANT_GET_ENTRIES;
        }
        return Constants.General.SUCCESS;
    }
    /**
     *  Gibt die Liste aller Einträge zurück, nachdem sie mit den Daten aus der 
     * Datenbank aktualisiert wurde.
     * 
     * @return Gibt eine List mit Entry zurück
     * @see Entry
     */
    public List<Persistence.Entry> getEntries(){
        updateEntryList();
        return this.entries;
    }
    /**
     * Git alle Einträge zurück die von einem bestimmten Nutzer erstellt wurden.
     * 
     * @param username Der Nutzername des Nutzers
     * @return Eine List mit Entry
     * @see Entry
     */
     public List<Persistence.Entry> getMemberEntries(String username){
        return this.pc.getEntriesByMember(username);
    }
    /**
     * Fügt einen neuen Eintrag hinzu und versucht diesen zu persistieren
     * 
     * @param entry das Entry object das hinzugefügt wrden soll
     * @return Bei erfolg wird "SUCCESS" zurückgegeben, ansonsten die Fehlermeldung des Persitence Controller
     */
    public String addEntry(Persistence.Entry entry){   
        String returnMessage = this.pc.persitObject(entry);
        if(returnMessage.equals(Constants.General.SUCCESS)){
             this.entries.add(entry);
        }
        
        return returnMessage;
    }
    
    /**
     * Entfernt einen Eintrag aus der Liste und der Datenbank. 
     * 
     * @param id Die ID des Eintrags
     * @return Bei erfolg wird "SUCCESS" zurückgegeben, wird der Eintrg zu der ID nicht gefunden wird "Entry not Found!" zurückgegeben, kann der Eintrag nicht entfernt werden wird "Removing the Entry failed!" zurückgegeben
     */
    public String deleteEntry(int id){
        Persistence.Entry target = null;
        if(( target = this.pc.findEntry(id) ) != null) {
          return this.pc.deleteObject(target) ? Constants.General.SUCCESS : Constants.ErrorMessages.CANT_DELETE_ENTRY;
        }
        return Constants.ErrorMessages.CANT_FIND_ENTRY;
    }
    /**
     * Liefert den Eintrag mit der übergebenen ID
     * 
     * @param id Die ID des Eintags
     * @return Bei Erfolg wird das Entry Object zu dem Eintrag zurückgegeben, ansonsten null
     */
    public Persistence.Entry getEntry(int id){
        updateEntryList();
        for(Persistence.Entry entry : this.getEntries())
        {
            if(entry.getId() == id)
                return entry;
        }
        return null;
    }
    /**
     * Aktualiert die Liste der Nutze mit den aktuellen Daten aus der Datenbank
     * 
     * @return Bei Erfolg wird "SUCCESS" zurück gegeben, bei fehlschalg "Could not get users!".
     */
    private String updateUserList(){
        List<Persistence.Member> users = pc.getAllUsers();
        if(users != null)
        {
            this.users = users;
            return Constants.ErrorMessages.CANT_GET_USERS;
        }
        return Constants.General.SUCCESS;
    }
    
    
    /**
     *  Gibt die Liste aller Nutzer zurück, nachdem sie mit den Daten aus der 
     * Datenbank aktualisiert wurde.
     * 
     * @return Gibt eine List mit Member zurück
     * @see Member
     */
    public List<Persistence.Member> getMembers(){
        this.updateUserList();
        return this.users;
    }
    
    /**
     * Fügt einen neuen Nutzer hinzu und versucht diesen zu persistieren
     * 
     * @param user das Member object das hinzugefügt wrden soll
     * @return Bei erfolg wird "SUCCESS" zurückgegeben, ansonsten die Fehlermeldung des Persitence Controller
     */
    public String addUser(Persistence.Member user){   
        String returnMessage = this.pc.persitObject(user);
        if(returnMessage.equals(Constants.General.SUCCESS)){
             this.users.add(user);
        }
        return returnMessage;
    }
    
    /**
     * Gibt das Meber Object an den Psistence Controller weiter welcher versucht
     * den Nutzer eintrag in der Datenbank zu aktualisieen
     * 
     * @param m der zu aktualisiende Nutzer als Member Object
     * @return Gibt die Meldung des Persistence Controllers zurück
     * @see Member, PersistenceController
     */
    public String mergeMember(Member m){
        return this.pc.mergeUser(m);
    }
    
     /**
     * Entfernt einen Nutzer aus der Liste und der Datenbank. 
     * 
     * @param name Der nutzername des zu löschenden Nutzers
     * @return Bei erfolg wird "SUCCESS" zurückgegeben, wird der Eintrg zu dem Nutzernamen nicht gefunden wird "User not Found!" zurückgegeben, kann der Eintrag nicht entfernt werden wird "Removing the User failed!" zurückgegeben
     */
    public String deleteUser(String name){
        Member target = null;
        if(( target = this.pc.findUser(name) ) != null) {
          return this.pc.deleteObject(target) ? Constants.General.SUCCESS : Constants.ErrorMessages.CANT_DELETE_USER;
        }
        return Constants.ErrorMessages.CANT_FIND_ENTRY;
    }
    
    /**
     * Liefert den Nuter als Member Object mit dem übergebenen Nutzernamen
     * 
     * @param name Der Nutzernae des Nutzers
     * @return Bei Erfolg wird das Member Object zu dem Nutzer zurückgegeben, ansonsten null
     */
    public Member getMember(String name)
    {
        for(Member user : this.getMembers())
        {
            if(user.getName().equals(name))
                return user;
        }
        return null;
    }
    /**
     *  Erhöht den Wert der stars des Eintrags mit der übergegbenen ID um eins und
     *  veringert den Wert der Punkte des Nutzers mit dem übergebenen Nutzernamen um eins, wenn
     *  überpüft wurde ob der Nutzer ausreichend Punkte über hat und der Eintrag
     *  nicht von ihm selber erstellt wurde.
     * 
     * @param id Die ID des Eintrags desen stars erhöht weden sollen
     * @param username Der Nutzername des Nutzers
     * @return  Bei Erfolg wird "SUCCESS" zurückgegeben,
     *          wid der Eintrag nicht gefunden wird "Entry not Found!" zurückgegeben,
     *          wird der Nutzer nicht gefunden wird "Member not Found!" zurückgegeben,
     *          hat der Nutzer nicht ausreichend Pukte über wird "No Points left for Member" urückgegeben,
     *          Wurde der Eintrag von dem Nutzer erstellt wird "You cannot vote for your own entry" zurückgegeben
     */
     public String incrementEntry(int id, String username)
    {
        Persistence.Entry entry = this.getEntry(id);
        if(entry == null)
            return Constants.ErrorMessages.CANT_FIND_ENTRY;
        
        Persistence.Member user = this.getMember(username);
        if(user == null)
            return Constants.ErrorMessages.CANT_FIND_USER;
        
        if(user.getPoints() <= 0)
            return Constants.ErrorMessages.NO_USER_POINTS_LEFT;
        
         if(entry.getUser().equals(user.getName()))
            return Constants.ErrorMessages.USER_CANT_VOTE_OWN_ENTRY;
        
         String msg = user.incRating(entry);
         if( !msg.equals(Constants.General.SUCCESS) ) return msg;
         msg = this.pc.mergeUser(user);
         if(!msg.equals(Constants.General.SUCCESS)) return msg;
         return this.pc.mergeEntry(entry);
    }
    
     
      /**
     *  Veringert den Wert der stars des Eintrags mit der übergegbenen ID um eins und
     *  erhöht den Wert der Punkte des Nutzers mit dem übergebenen Nutzernamen um eins, wenn
     *  überpüft wurde ob der Nutzer ausreichend Punkte über hat und der Eintrag
     *  nicht von ihm selber erstellt wurde.
     * 
     * @param id Die ID des Eintrags desen stars veringert weden sollen
     * @param username Der Nutzername des Nutzers
     * @return  Bei Erfolg wird "SUCCESS" zurückgegeben,
     *          wid der Eintrag nicht gefunden wird "Entry not Found!" zurückgegeben,
     *          wird der Nutzer nicht gefunden wird "Member not Found!" zurückgegeben,
     *          ist der stars Wet des Eintrags bereits 0 wird "Entry stars allerady 0" urückgegeben,
     *          Wurde der Eintrag von dem Nutzer erstellt wird "You cannot vote for your own entry" zurückgegeben
     */
    public String decrementEntry(int id, String username)
    {
        Persistence.Entry entry = this.getEntry(id);
        if(entry == null)
            return Constants.ErrorMessages.CANT_FIND_ENTRY;
        
        if(entry.getStars() <= 0)
            return Constants.ErrorMessages.ENTRY_STATS_ARE_NULL;
        
        Persistence.Member user = this.getMember(username);
        if(user == null)
            return Constants.ErrorMessages.CANT_FIND_USER;
        
        if(entry.getUser().equals(user.getName()))
            return Constants.ErrorMessages.USER_CANT_VOTE_OWN_ENTRY;
        
         String msg = user.decRating(entry);
         if( !msg.equals(Constants.General.SUCCESS) ) return msg;
         msg = this.pc.mergeUser(user);
         if(!msg.equals(Constants.General.SUCCESS)) return msg;
         return this.pc.mergeEntry(entry);
    }
    
}

