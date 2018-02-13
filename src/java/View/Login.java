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
 * @author Marcel
 */
@Named
@RequestScoped
public class Login {
    
    @Inject Session.ViewController vc;
    @Inject Hints hints;
    
    private String username;
    
    public boolean loggedIn()
    {
        return this.vc.loggedIn();
    }
    
    public String getUsername()
    {
        return this.vc.getUsername();
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String login()
    {
        String message = this.vc.login(this.username);
        if(!message.equals(Constants.General.SUCCESS))
        {
            this.hints.addHint(message);
        }
        
        return Constants.General.INDEX_PAGE;
    }
    
    public String logout()
    {
        this.vc.logout();
        return Constants.General.INDEX_PAGE;
    }
    
}
