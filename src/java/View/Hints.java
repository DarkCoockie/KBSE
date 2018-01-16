/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Marcel
 */
@Named
@SessionScoped
public class Hints implements Serializable {
    private List<String> hints;
    
    @PostConstruct
    private void init()
    {
        this.hints = new ArrayList<>();
    }
    
    public List<String> getHints()
    {
        List<String> hints = new ArrayList<>(this.hints);
        this.clear();
        return hints;
    }
    
    public void addHint(String hint)
    {
        this.hints.add(hint);
    }
    
    public void removeHint(String hint)
    {
        this.hints.remove(hint);
    }
    
    public void removeHints(List<String> hints)
    {
        for(String hint: hints)
        {
            this.removeHint(hint);
        }
    }
    
    public void clear()
    {
        this.hints.clear();
    }
}
