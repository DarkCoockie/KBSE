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
    
    public void incrementStars(int id)
    {
        String returnVal = this.vc.incrementStars(id);
        if(!returnVal.equals(Constants.Constants.SUCCESS))
        {
            this.hints.addHint(returnVal);
        }
    }
    
    public void decrementStars(int id)
    {
        String returnVal = this.vc.decrementStars(id);
        if(!returnVal.equals(Constants.Constants.SUCCESS))
        {
            this.hints.addHint(returnVal);
        }
    }
    
    public boolean userHasStars()
    {
        return this.vc.userHasStars();
    }
    
    public boolean userHasMaxStars()
    {
        return this.vc.userHasMaxStars();
    }
    
    public boolean userSpendStars(int id)
    {
        return this.vc.userSpendStars(id);
    }
}
