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
    @Inject Controller.Controller controller;
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
        if(this.controller.getUser(username) == null)
        {
            this.controller.addUser(username);
        }
        
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
        for(int i=0; i < forbidden.length()-1; i++)
        {
            if(input.
                    contains(
                            forbidden.
                                    substring(i, i+1)))
            {
                return false;
            }
        }
        
        return !input.equals("");
    }
    
    public List<Persistence.Entry> getEntries()
    {
        return this.controller.getEntries();
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
        
        this.controller.addEntry(entry);
        
        return Constants.Constants.SUCCESS;
    }
    
    public String incrementStars(int id)
    {
        Persistence.Entry entry = this.controller.getEntry(id);
        if(entry == null)
        {
            return Constants.ErrorMessages.ENTRY_NOT_FOUND;
        }
        
        String returnVal = this.controller.getUser(this.username).incRating(id);
        if(returnVal.equals(Constants.Constants.SUCCESS))
        {
            entry.setStars(entry.getStars() + 1);
        }
        
        return returnVal;
    }
    
    public String decrementStars(int id)
    {
        Persistence.Entry entry = this.controller.getEntry(id);
        if(entry == null)
        {
            return Constants.ErrorMessages.ENTRY_NOT_FOUND;
        }
        
        String returnVal = this.controller.getUser(this.username).decRating(id);
        if(returnVal.equals(Constants.Constants.SUCCESS))
        {
            entry.setStars(entry.getStars() - 1);
        }
        
        return returnVal;
    }
    
    public boolean userHasStars()
    {
        return this.getUserStars() > Constants.Values.STARS_MIN;
    }
    
    public boolean userHasMaxStars()
    {
        return this.getUserStars() == Constants.Values.STARS_MAX;
    }
    
    public boolean userSpendStars(int id)
    {
        Persistence.User user = this.controller.getUser(this.username);
        if(user == null)
        {
            return false;
        }
        return user.getRating(id) != null;
    }
    
    public int getUserStars()
    {
        Persistence.User user = this.controller.getUser(this.username);
        if(user == null)
        {
            return -1;
        }
        return user.getStars();
    }
    
    public boolean userCreatedEntry(int id)
    {
        Persistence.Entry entry = this.controller.getEntry(id);
        if(entry == null)
        {
            return false;
        }
        
        return entry.getUser().equals(this.username);
    }
}
