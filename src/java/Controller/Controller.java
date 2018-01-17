/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author Marcel
 */
@Named
@Dependent
public class Controller implements Serializable {
    private List<Persistence.Entry> entries;
    private List<Persistence.User> users;
    
    @PostConstruct
    private void init()
    {
        this.entries = new ArrayList<>();
        this.users = new ArrayList<>();
    }
    
    public List<Persistence.Entry> getEntries()
    {
        return this.entries;
    }
    
    public void addEntry(Persistence.Entry entry)
    {
        this.entries.add(entry);
    }
    
    public Persistence.Entry getEntry(int id)
    {
        for(Persistence.Entry entry: this.entries)
        {
            if(entry.getId() == id)
            {
                return entry;
            }
        }
        return null;
    }
    
    public List<Persistence.User> getUsers()
    {
        return this.users;
    }
    
    public void addUser(String username)
    {
        this.users.add(new Persistence.User(username));
    }
    
    public Persistence.User getUser(String username)
    {
        for(Persistence.User user: this.users)
        {
            if(user.getUsername().equals(username))
            {
                return user;
            }
        }
        return null;
    }
}
