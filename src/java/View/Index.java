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
    
    public List<Persistence.Entry> getEntries()
    {
        return this.vc.getEntries();
    }
    
    public String newEntry()
    {
        return Constants.Constants.NEW_ENTRY_PAGE;
    }
}