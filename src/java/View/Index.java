/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *  Frontend Klasse für die Index.xhtml
 * 
 * @author Marcel Schulte
 */
@Named
@RequestScoped
public class Index implements Serializable{
    /**
     * Schnitstelle zu Hauptlogik
     */
    @Inject Session.ViewController vc;
    /**
     * Schnitstelle zu Hinweis Organisation
     */
    @Inject Hints hints;
    
    /**
     * @return Liste mit allen Entries
     */
    public List<Persistence.Entry> getEntries()
    {
        return this.vc.getEntries();
    }
    
    /**
     * @return Gibt den Pfad zur NewEntry Seite zurück
     */
    public String newEntry()
    {
        return Constants.General.NEW_ENTRY_PAGE;
    }
    
    /**
     * Gibt die Entry-ID an den ViewController weiter um die Anzahl der Sterne des
     * Entries zu erhöhen und fügt die Antwort des ViewControllers zur Hinweis Liste
     * hinzu
     * 
     * @param id Die ID des Entries
     * @return null
     * @see ViewController
     */
    public String incrementEntry(int id)
    {
        String returnMessage = this.vc.incrementEntry(id);
        if(!returnMessage.equals(Constants.General.SUCCESS))
        {
            this.hints.addHint(returnMessage);
        }
        return null;
    }
    
     
    /**
     * Gibt die Entry-ID an den ViewController weiter um die Anzahl der Sterne des
     * Entries zu veringern und fügt die Antwort des ViewControllers zur Hinweis Liste
     * hinzu
     * 
     * @param id Die ID des Entries
     * @return null
     * @see ViewController
     */
    public String decrementEntry(int id)
    {
        String returnMessage = this.vc.decrementEntry(id);
        if(!returnMessage.equals(Constants.General.SUCCESS))
        {
            this.hints.addHint(returnMessage);
        }
        return null;
    }
    
    /**
     * Überprüft ob der Nutzer mehr als 0 Punkte besitzt. Wird der Nutzer mit dem
     * übergebenen Nutzernamen nicht gefunden wird Constants.ErrorMessages.CANT_FIND_USER
     * zur Hinweis Liste hinzugefügt und false zurückgegeben
     * 
     * @param memberName Der Nutzername des Nutzers
     * @return Besitzt der Nutzer mehr als 0 Pinkte wird true zurückgegeb, sons fals
     */
    public boolean userHasPoints(String memberName)
    {
        Persistence.Member user = this.vc.getMember(memberName);
        if(user == null)
        {
            this.hints.addHint(Constants.ErrorMessages.CANT_FIND_USER);
            return false;
        }
        
        return user.getPoints() > 0;
    }
    
    /**
     * Überprüft ob der Nutzer mit dem Nutzernamen "memberName" ersteller des Entries
     * mit der "entryId" ist. Wird der Nutzer mit dem übergebenen Nutzernamen nicht
     * gefunden wird Constants.ErrorMessages.CANT_FIND_USER zur Hinweis Liste
     * hinzugefügt und false zurückgegeben. Wird das Entry mit der
     * übergebenen Entry-ID nicht gefunden wird Constants.ErrorMessages.CANT_FIND_ENTRY
     * zur Hinweis Liste hinzugefügt und false zurückgegeben.
     * 
     * @param entryId
     * @param memberName
     * @return Ist der "membername" gleich dem Nutzernamen des Entry Objekts wird
     *         true zurück, sonst false
     */
    public boolean isAuthor(int entryId, String memberName)
    {
        Persistence.Member member = this.vc.getMember(memberName);
        if(member == null)
        {
            this.hints.addHint(Constants.ErrorMessages.CANT_FIND_USER);
            return true;
        }
        Persistence.Entry entry = this.vc.getEntry(entryId);
        if(entry == null)
        {
            this.hints.addHint(Constants.ErrorMessages.CANT_FIND_ENTRY);
            return true;
        }
        
        return entry.getUser().equals(member.getName());
    }
    
    /**
     * Überprüft ober der Nutzer mit dem übergebenen Nutzernamen bereits Punkte an 
     * das Entry mit der übergebenen Entry-ID vergeben hat. Wird der Nutzer mit 
     * dem übergebenen Nutzernamen nicht gefunden wird Constants.ErrorMessages.CANT_FIND_USER
     * zur Hinweis Liste hinzugefügt und false zurückgegeben.
     * 
     * @param id Id des Entries
     * @param memberName Nutzername des Nutzers
     * @return Wird die Entry-Id in der ratings Map des Nutzers gefunden wird true
     *         zurückgegeben, sonst false
     */
    public boolean userHasSpentPoints(int id, String memberName)
    {
        Persistence.Member user = this.vc.getMember(memberName);
        if(user == null)
        {
            this.hints.addHint(Constants.ErrorMessages.CANT_FIND_USER);
            return false;
        }
        return user.getRatings().get(id) != null;
    }
}
