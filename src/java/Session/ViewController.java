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
 *
 * @author Marcel
 */
@SessionScoped
public class ViewController implements Serializable{
    @Inject Controller.Controller controler;
    private String username = "";
    private String searchOption;
    private String searchString;

    /**
     * Set the identifier for the current session.
     * 
     * @param username identifier for the user
     * @return returns Constants.SUCCESS if input is valid,
     *         returns an error message otherwise
     */
    
    @PostConstruct
    private void init()
    {
        this.searchOption = Constants.OrderBy.NAME;
        this.searchString = "";
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    
    public String getSearchOption() {
        return searchOption;
    }
    
    public void setSearchOption(String searchOption) {
        this.searchOption = searchOption;
    }

    public String login(String username) {
        if(!this.checkInput(username, Constants.ForbiddenSigns.USERNAME))
        {
            return Constants.ErrorMessages.INVALID_INPUT;
        }
        this.username = username;
        
        this.controler.addUser(new Persistence.Member(username));
        
        return Constants.General.SUCCESS;
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
    
    public List<Persistence.Entry> getEntriesStatic()
    {
        return this.controler.getEntriesStatic();
    }
    
    public List<Persistence.Entry> getMemberEntries()
    {
        return this.controler.getMemberEntries(this.username);
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
        
        return Constants.General.SUCCESS;
    }
    
    public String incrementEntry(int id)
    {
        return this.controler.incrementEntry(id, this.username);
    }
    
    public String decrementEntry(int id)
    {
       return this.controler.decrementEntry(id, this.username);
    }
    
    public String deleteEntry(int id)
    {
        return this.controler.deleteEntry(id);
    }
    
    public Persistence.Member getUser(String username)
    {
        return this.controler.getMember(username);
    }
    
    public Persistence.Entry getEntry(int id)
    {
        return this.controler.getEntry(id);
    }
    
    public List<Persistence.Entry> filter(String search)
    {
        List<Persistence.Entry> results = new ArrayList<>();
        
        for(Persistence.Entry entry : this.controler.getEntriesStatic())
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
