/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
    private List<Persistence.User> users;
    private PersistenceController pc = new PersistenceController();
    
    @PostConstruct
    private void init(){
        this.entries = new ArrayList<>();
    }
    
    private void updateEntryList(){
        this.entries = pc.getAllEntrys();
    }
    
    public List<Persistence.Entry> getEntries(){
        updateEntryList();
        return this.entries;
    }
    
    public void addEntry(Persistence.Entry entry){   
        if(this.pc.persitObject(entry).equals(Constants.Constants.SUCCESS)){
             this.entries.add(entry);
        }
    }
    
    public String deleteEntry(int id){
        Persistence.Entry target = null;
        if(( target = this.pc.findEntry(id) ) != null) {
          return this.pc.deleteObject(target) ? Constants.Constants.SUCCESS : Constants.ErrorMessages.CANT_DELETE_ENTRY;
        }
        return Constants.ErrorMessages.CANT_FIND_ENTRY;
    }
    
    public Persistence.Entry getEntry(int id)
    {
        for(Persistence.Entry entry : this.getEntries())
        {
            if(entry.getId() == id)
                return entry;
        }
        return null;
    }
    
    private void updateUserList(){
        this.entries = pc.getAllEntrys();
    }
    
    public List<Persistence.User> getUsers(){
        this.updateUserList();
        return this.users;
    }
    
    public void addUser(Persistence.User user){   
        if(this.pc.addUser(user) == Constants.Constants.SUCCESS){
             this.users.add(user);
        }
    }
    
    public String deleteUser(String name){
        Persistence.User target = null;
        if(( target = this.pc.findUser(name) ) != null) {
          return this.pc.deleteObject(target) ? Constants.Constants.SUCCESS : Constants.ErrorMessages.CANT_DELETE_USER;
        }
        return Constants.ErrorMessages.CANT_FIND_ENTRY;
    }
    
    public Persistence.User getUser(String name)
    {
        for(Persistence.User user : this.getUsers())
        {
            if(user.getName().equals(name))
                return user;
        }
        return null;
    }
}

