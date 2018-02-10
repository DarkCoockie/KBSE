/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marcel
 */
@Named
@RequestScoped
public class NewEntry implements Serializable {
    @Inject Session.ViewController vc;
    
    @Inject Hints hints;
    
    private Persistence.Entry entry;
    
    public NewEntry(){
       
    }
    
    @PostConstruct
    private void init()
    {
        if(this.vc.getUsername() != null && this.vc.getUsername().equals("")) this.goBackToHome();
        this.entry = new Persistence.Entry();   
    }
    
    public void goBackToHome(){
         FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, Constants.General.INDEX_PAGE);
    }
    
    public String confirm()
    {
        this.entry.setUser(this.vc.getUsername());
        String message = this.vc.addEntry(this.entry);
        if(message.equals(Constants.General.SUCCESS))
        {
            return Constants.General.INDEX_PAGE;
        }
        this.hints.addHint(message);
        
        return null;
    }
    
    public List<Persistence.Entry> getEntries()
    {
        return this.vc.getMemberEntries();
    }
    
      public String deleteEntry(int id){
        String erg;
        if( !(erg = this.vc.deleteEntry(id)).equals(Constants.General.SUCCESS)){
           hints.addHint(erg);
           return Constants.General.INDEX_PAGE;
       }
       return Constants.General.INDEX_PAGE;
    }
    
    // Entry Get and Set ------------------------------------------------------
    
    public String getEntryName() {
        return this.entry.getName();
    }

    public void setEntryName(String name) {
        this.entry.setName(name);
    }
    
    public int getEntryId() {
        return this.entry.getId();
    }

    public String getEntryDescription() {
        return this.entry.getDescription();
    }

    public void setEntryDescription(String description) {
        this.entry.setDescription(description);
    }

    public String getEntryUrl() {
        return this.entry.getUrl();
    }

    public void setEntryUrl(String url) {
        this.entry.setUrl(url);
    }
}
