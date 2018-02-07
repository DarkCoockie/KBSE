/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Marcel
 */
@SessionScoped
public class ViewController implements Serializable{
    @Inject Controller.Controller controler;
    private String username = "";

    /**
     * Set the identifier for the current session.
     * 
     * @param username identifier for the user
     * @return returns Constants.SUCCESS if input is valid,
     *         returns an error message otherwise
     */
    public String login(String username)
    {
        if(!this.checkInput(username, Constants.ForbiddenSigns.USERNAME))
        {
            return Constants.ErrorMessages.INVALID_INPUT;
        }
        this.username = username;
        
        this.controler.addUser(new Persistence.Member(username));
        
        return Constants.Constants.SUCCESS;
    }
    
    public void logout()
    {
        this.username = "";
    }
    
    public boolean loggedIn()
    {
        return !this.username.equals("");
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
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
    
    public List<Persistence.Entry> getEntries()
    {
        return this.controler.getEntries();
    }
    
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
        
        this.controler.addEntry(entry);
        
        return Constants.Constants.SUCCESS;
    }
    
    public String incrementEntry(int id)
    {
        Persistence.Entry entry = this.controler.getEntry(id);
        if(entry == null)
            return Constants.ErrorMessages.CANT_FIND_ENTRY;
        
        Persistence.Member user = this.controler.getUser(this.username);
        if(user == null)
            return Constants.ErrorMessages.CANT_FIND_USER;
        
        return user.incRating(entry);
    }
    
    public String decrementEntry(int id)
    {
        Persistence.Entry entry = this.controler.getEntry(id);
        if(entry == null)
            return Constants.ErrorMessages.CANT_FIND_ENTRY;
        
        Persistence.Member user = this.controler.getUser(this.username);
        if(user == null)
            return Constants.ErrorMessages.CANT_FIND_USER;
        
        return user.decRating(entry);
    }
    
    public String deleteEntry(int id)
    {
        return this.controler.deleteEntry(id);
    }
    
    public Persistence.Member getUser(String username)
    {
        return this.controler.getUser(username);
    }
    
    public Persistence.Entry getEntry(int id)
    {
        return this.controler.getEntry(id);
    }
}
