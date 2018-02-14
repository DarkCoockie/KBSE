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
 *
 * @author Marcel
 */
@Named
@Dependent
public class Controller implements Serializable {
    private List<Persistence.Entry> entries;
    private List<Persistence.Member> users;
    private PersistenceController pc = new PersistenceController();
    
    @PostConstruct
    private void init(){
        this.entries = new ArrayList<>();
        this.users = new ArrayList<>();
    }
    
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
    
    public List<Persistence.Entry> getEntries(){
        updateEntryList();
        return this.entries;
    }
    
     public List<Persistence.Entry> getMemberEntries(String username){
        return this.pc.getEntriesByMember(username);
    }
    
    public String addEntry(Persistence.Entry entry){   
        String returnMessage = this.pc.persitObject(entry);
        if(returnMessage.equals(Constants.General.SUCCESS)){
             this.entries.add(entry);
        }
        
        return returnMessage;
    }
    
    public String deleteEntry(int id){
        Persistence.Entry target = null;
        if(( target = this.pc.findEntry(id) ) != null) {
          return this.pc.deleteObject(target) ? Constants.General.SUCCESS : Constants.ErrorMessages.CANT_DELETE_ENTRY;
        }
        return Constants.ErrorMessages.CANT_FIND_ENTRY;
    }
    
    public Persistence.Entry getEntry(int id){
        updateEntryList();
        for(Persistence.Entry entry : this.getEntries())
        {
            if(entry.getId() == id)
                return entry;
        }
        return null;
    }
    
    private String updateUserList(){
        List<Persistence.Member> users = pc.getAllUsers();
        if(users != null)
        {
            this.users = users;
            return Constants.ErrorMessages.CANT_GET_USERS;
        }
        return Constants.General.SUCCESS;
    }
    
    public List<Persistence.Member> getMembers(){
        this.updateUserList();
        return this.users;
    }
    
    public String addUser(Persistence.Member user){   
        String returnMessage = this.pc.persitObject(user);
        if(returnMessage.equals(Constants.General.SUCCESS)){
             this.users.add(user);
        }
        return returnMessage;
    }
    
    public String mergeMember(Member m){
        return this.pc.mergeUser(m);
    }
    
    public String deleteUser(String name){
        Member target = null;
        if(( target = this.pc.findUser(name) ) != null) {
          return this.pc.deleteObject(target) ? Constants.General.SUCCESS : Constants.ErrorMessages.CANT_DELETE_USER;
        }
        return Constants.ErrorMessages.CANT_FIND_ENTRY;
    }
    
    public Member getMember(String name)
    {
        for(Member user : this.getMembers())
        {
            if(user.getName().equals(name))
                return user;
        }
        return null;
    }
    
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

