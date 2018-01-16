/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
    @Inject Hints allHints;
    
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
    
    public void login()
    {
        String message = this.vc.login(this.username);
        if(!message.equals(Constants.Constants.SUCCESS))
        {
            this.allHints.addHint(message);
        }
    }
    
    public void logout()
    {
        this.vc.logout();
    }
    
}
