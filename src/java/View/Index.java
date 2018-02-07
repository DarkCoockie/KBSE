/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marcel
 */
@Named
@RequestScoped
public class Index implements Serializable{
    
    @Inject Session.ViewController vc;
    @Inject Hints hints;
    
    
    public List<Persistence.Entry> getEntries()
    {
        return this.vc.getEntries();
    }
    
    public String newEntry()
    {
        return Constants.Constants.NEW_ENTRY_PAGE;
    }
    
    public void incrementEntry(int id)
    {
        String returnMessage = this.vc.incrementEntry(id);
        if(!returnMessage.equals(Constants.Constants.SUCCESS))
        {
            this.hints.addHint(returnMessage);
        }
    }
    
    public void decrementEntry(int id)
    {
        String returnMessage = this.vc.decrementEntry(id);
        if(!returnMessage.equals(Constants.Constants.SUCCESS))
        {
            this.hints.addHint(returnMessage);
        }
    }
    
    public String deleteEntry(int id){
        String erg;
        if( !(erg = this.vc.deleteEntry(id)).equals(Constants.Constants.SUCCESS)){
           hints.addHint(erg);
           return Constants.Constants.INDEX_PAGE;
       }
       return Constants.Constants.INDEX_PAGE;
    }
    
    public boolean userHasPoints()
    {
        Persistence.Member user = this.vc.getUser();
        if(user == null)
        {
            this.hints.addHint(Constants.ErrorMessages.CANT_FIND_USER);
            return false;
        }
        
        return user.getPoints() > 0;
    }
    
    public boolean isAuthor(int id)
    {
        Persistence.Member user = this.vc.getUser();
        if(user == null)
        {
            this.hints.addHint(Constants.ErrorMessages.CANT_FIND_USER);
            return true;
        }
        Persistence.Entry entry = this.vc.getEntry(id);
        if(entry == null)
        {
            this.hints.addHint(Constants.ErrorMessages.CANT_FIND_ENTRY);
            return true;
        }
        
        if(entry.getUser().equals(user.getName()))
        {
           return true;
        }
        
        return false;
    }
}
