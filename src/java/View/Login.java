/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * 
 * @author Marcel
 */
@Named
@RequestScoped
public class Login {
    /**
     * Schnitstelle zur Hauptlogik
     */
    @Inject Session.ViewController vc;
      /**
     * Schnitstelle zu Hinweis Organisation
     */
    @Inject Hints hints;
    
    /**
     * Zwischenablage für Nutzername
     */
    private String memberName;
    
    /**
     * Leitet den Login aufruf an den ViewController weiter
     * 
     * @return Die Meldung des ViewControllers
     */
    public boolean loggedIn()
    {
        return this.vc.loggedIn();
    }
    
    /**
     * @return Den Nutzernamen des eingeloggten Nutzers
     */
    public String getUsername()
    {
        return this.vc.getMemberName();
    }
    
    /**
     * @param memberName Den zu setzenden Nutzernamen
     */
    public void setUsername(String memberName)
    {
        this.memberName = memberName;
    }
    
    /**
     * Gibt this.memberName an den ViewController weiter um den Nutzer einzuloggen.
     * Bei einem Fehlschalg wird die Meldung des ViewControlles an die Hinweis Liste 
     * angefügt.
     * 
     * @return Den Pfad zu Index Seite
     */
    public String login()
    {
        String message = this.vc.login(this.memberName);
        if(!message.equals(Constants.General.SUCCESS))
        {
            this.hints.addHint(message);
        }
        
        return Constants.General.INDEX_PAGE;
    }
    
    /**
     * Loggt den Nutzer aus
     * 
     * @return Den Pfad zu Index Seite
     */
    public String logout()
    {
        this.vc.logout();
        return Constants.General.INDEX_PAGE;
    }
    
}
