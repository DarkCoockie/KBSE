/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 * Schnitstelle zwischen Frontend und Hauptlogik
 * 
 * @author Marcel schulte
 */
@SessionScoped
public class ViewController implements Serializable{
    /**
     *  Schnitstelle zur Hauptlogik und Persitence
     */
    @Inject
    Controller.Controller controler;
    
    /**
     *  Nutzername des eingeloggten Nutzers.
     * Ist kein Nutzer eingeloggt ist der String leer
     */
    private String memberName = "";
    
    private String searchOption;
    private String searchString;
    
    /**
     * 
     */
    @PostConstruct
    private void init()
    {
        this.searchOption = Constants.OrderBy.NAME;
        this.searchString = "";
    }

    /**
     * 
     * @return Die Such-Zeichenkette
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * 
     * @param searchString Die zu setzende Such-Zeichenkette
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    
    /**
     * 
     * @return Die Suchoptionen Zeichenkette
     */
    public String getSearchOption() {
        return searchOption;
    }
    
    /**
     * 
     * @param searchOption Die zu setzende Suchoptionen Zeichenkette
     */
    public void setSearchOption(String searchOption) {
        this.searchOption = searchOption;
    }

    /**
     * Überprüft den übergebenen Nutzernamen. Wird der Name akzeptier wird er in
     * this.memberName gespeichert und ein neuer Nutzer hinzugefügt. Existiert bereits
     * ein Nutzer mit dem Nutzernamen wird kein neuer Nutzer erzeugt 
     * 
     * @param username der Nutzername
     * @return Wird der übergebene Nutzername nicht akzeptier wid 
     *         Constants.ErrorMessages.INVALID_INPUT zurückgegeben, sons die 
     *         Meldung des Controllers
     * @see Controller
     */
    public String login(String username) {
        if(!this.checkInput(username, Constants.ForbiddenSigns.USERNAME))
        {
            return Constants.ErrorMessages.INVALID_INPUT;
        }
        this.memberName = username;
        
        return this.controler.addUser(new Persistence.Member(username));
    }
    
    /**
     * Setzt this.memberName zu einem leern String
     */
    public void logout()
    {
        this.memberName = "";
    }
    
    /**
     * Überprüft den wert von this.memberName
     * 
     * @return ist memberName nicht leer wird true zurückgegeben, sonst false
     */
    public boolean loggedIn()
    {
        return !this.memberName.equals("");
    }
    
    /**
     * 
     * @return gibt this.memberName zurück
     */
    public String getMemberName()
    {
        return this.memberName;
    }
    
    /**
     * Überprüft ob der übergebene String input ein zeichen aus dem String forbidden
     * enthält.
     * 
     * @param input Die zu überprüfende Zeichenkette
     * @param forbidden Die zeichenkette mit denverbotenen zeichen
     * @return Enthält input ein Zeichen aus forbidden wird false zurückgegeben, sonst true
     */
    private boolean checkInput(String input, String forbidden)
    {
        for(int i=0; i < forbidden.length(); i++)
        {
            if(input.contains(forbidden.substring(i, i+1)))
            {
                return false;
            }
        }
        
        return !input.equals("");
    }
    
    /**
     * 
     * @return Eine Liste mit Entry Objekten
     */
    public List<Persistence.Entry> getEntries()
    {
        return this.controler.getEntries();
    }
    
    /**
     * 
     * @return Eine Liste mit Entry Objekten die von dem eingeloggten Nutzer erstellt wurden
     */
    public List<Persistence.Entry> getMemberEntries()
    {
        return this.controler.getMemberEntries(this.memberName);
    }
    
    /**
     * Überprüft Name, url und description von entry auf verbotene Zeichen.
     * Werden keine Verbotenen Zeichen gefunden wird das Entry Objekt an den
     * Controller weiter gegeben wo es persitiert wird
     * 
     * @param entry Dsa neuen Entry Objekt
     * @return Wird in den Attributen von entry ein verbotenes Zeichen gefunden 
     *         Constants.ErrorMessages.INVALID_INPUT mit der nennung des attributs
     *         zurückgegeben, sonst die Meldung des Controllers
     * @see Controller
     */
    public String addEntry(Persistence.Entry entry)
    {
        if(!this.checkInput(entry.getName(), Constants.ForbiddenSigns.WEBSITE_NAME))
        {
            return "[Name]" + Constants.ErrorMessages.INVALID_INPUT;
        }
        
        if(!this.checkInput(entry.getUrl(), Constants.ForbiddenSigns.WEBSITE_URL))
        {
            return "[URL]" + Constants.ErrorMessages.INVALID_INPUT;
        }
        
        if(!this.checkInput(entry.getDescription(), Constants.ForbiddenSigns.WEBSITE_DESCRIPTION))
        {
            return "[Beschreibung]" + Constants.ErrorMessages.INVALID_INPUT;
        }
        
        return this.controler.addEntry(entry);
    }
    
    /**
     * Gibt die Entry-Id und den Nutzernamen des eingeloggten Nutzers an den
     * Controller weiter um die Anzahl der Sterne des Entries zu erhöhen
     * 
     * @param id ID des Entries
     * @return  Gibt die Meldung des Controllers zurück
     * @see Controller
     */
    public String incrementEntry(int id)
    {
        return this.controler.incrementEntry(id, this.memberName);
    }
    
    /**
     * Gibt die Entry-Id und den Nutzernamen des eingeloggten Nutzers an den
     * Controller weiter um die Anzahl der Sterne des Entries zu veringern
     * 
     * @param id ID des Entries
     * @return  Gibt die Meldung des Controllers zurück
     * @see Controller
     */
    public String decrementEntry(int id)
    {
       return this.controler.decrementEntry(id, this.memberName);
    }
    
    /**
     * Gibt die Entry-Id an den Controller weiter um das Entry zu entfernen
     * 
     * @param id Die ID des Entries
     * @return Gibt die Meldung des Controllers zurück
     * @see Controller
     */
    public String deleteEntry(int id)
    {
        return this.controler.deleteEntry(id);
    }
    
    /**
     * Gibt den Nutzernamen an den Controller weiter um nach dem Member zu suchen
     * 
     * @param memberName Der Nutzername des Nutzers
     * @return Die Meldung des Controllers
     * @see Controller
     */
    public Persistence.Member getMember(String memberName)
    {
        return this.controler.getMember(memberName);
    }
    
     /**
     * Gibt die Entry-ID an den Controller weiter um nach dem Entry zu suchen
     * 
     * @param id Die ID des Entries
     * @return Die Meldung des Controllers
     * @see Controller
     */
    public Persistence.Entry getEntry(int id)
    {
        return this.controler.getEntry(id);
    }
    
    /**
     * Succht in Name, nutzername, url und beschreibung aller Entries nach dem 
     * search String und gibt eine Liste mit allen treffern zurück
     * 
     * @param search nach dem zu suchenden String
     * @return Eine Liste mit allen Entries die search enthalten
     */
    public List<Persistence.Entry> filter(String search)
    {
        List<Persistence.Entry> results = new ArrayList<>();
        
        for(Persistence.Entry entry : this.controler.getEntries())
        {
            if(entry.getName().contains(search)
                    || entry.getUser().contains(search)
                    || entry.getDescription().contains(search)
                    || entry.getUrl().contains(search)
                )
            {
                results.add(entry);
            }
        }
        
        return results;
    }
}
