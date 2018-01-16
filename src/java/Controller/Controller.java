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
    
    @PostConstruct
    private void init()
    {
        this.entries = new ArrayList<>();
    }
    
    public List<Persistence.Entry> getEntries()
    {
        return this.entries;
    }
    
    public void addEntry(Persistence.Entry entry)
    {
        this.entries.add(entry);
    }
}
