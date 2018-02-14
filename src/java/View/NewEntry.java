/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Persistence.Entry;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * Frontend Klasse für newEntry.xhtml
 * 
 * @author Marcel Schulte
 */
@Named
@RequestScoped
public class NewEntry implements Serializable {
    /**
     * Schnitstelle zu Hauptlogik
     */
    @Inject
    Session.ViewController vc;
    /**
     * Schnitstelle zu Hinweis Organisation
     */
    @Inject 
    Hints hints;
    
    /**
     * Zwischengespeichertes Entry Objeckt zur weitern verarbeitung
     */
    private Persistence.Entry entry;
    
    public NewEntry(){
       
    }
    
    /**
     * Initialisiert alle Vaibalen nachdem die Klasse erzeugt wurde und überprüft
     * ob ein Nutzer beim Aufruf der Seite eingelogt ist, wenn nicht wird 
     * automatisch zur Index Seite zurück geleitet.
     */
    @PostConstruct
    private void init()
    {
        if(this.vc.getMemberName()!= null && this.vc.getMemberName().equals("")) this.goBackToHome();
        this.entry = new Persistence.Entry();   
    }

    /**
     * @return this.entry;
     */
    public Entry getEntry() {
        return this.entry;
    }

    /**
     * @param entry Das zu setzende Entry Objekt
     */
    public void setEntry(Entry entry) {
        this.entry = entry;
    }
     
    /**
     * Sendet den Nutzer autmotisch zurück zur Index Seite
     */
    public void goBackToHome(){
         FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, Constants.General.INDEX_PAGE);
    }
    
    /**
     * Setzt den Nutzernamen des eingeloggten Nutzers in this.entry und leitet
     * this.entry an den ViewController weiter um es zu persistieren. Bei einem 
     * Fehlschlag wir die meldung des ViewController zur Hinweis Liste hinzugefügt
     * 
     * @return Bei Erfolg wid der Pfad zur Index Seite zurückgegeben, sonst null
     */
    public String confirm()
    {
        this.entry.setUser(this.vc.getMemberName());
        String message = this.vc.addEntry(this.entry);
        if(message.equals(Constants.General.SUCCESS))
        {
            return Constants.General.INDEX_PAGE;
        }
        this.hints.addHint(message);
        
        return null;
    }
    
    /**
     * @return Liste mit Entry Objekten die der eingeloggte Nutzer estellt hat
     */
    public List<Persistence.Entry> getEntries()
    {
        return this.vc.getMemberEntries();
    }
    
    /**
     * 
     * @param id Die Entry-Id des zu löschenden Entries
     * @return Den Pfad zur Index Seite
     */
    public String deleteEntry(int id){
      String erg;
      if( !(erg = this.vc.deleteEntry(id)).equals(Constants.General.SUCCESS)){
         hints.addHint(erg);
         return Constants.General.INDEX_PAGE;
     }
     return Constants.General.INDEX_PAGE;
    }
}
