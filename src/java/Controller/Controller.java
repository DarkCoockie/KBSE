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
        if(this.pc.addEntry(entry) == Constants.Constants.SUCCESS){
             this.entries.add(entry);
        }
    }
    
    public String deleteEntry(int id){
        Persistence.Entry target = null;
        if(( target = this.pc.findEntry(id) ) != null) {
          return this.pc.deleteEntry(target) ? Constants.Constants.SUCCESS : Constants.ErrorMessages.CANT_DELETE_ENTRY;
        }
        return Constants.ErrorMessages.CANT_FIND_ENTRY;
    }
}

