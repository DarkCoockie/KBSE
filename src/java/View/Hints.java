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
 * Klasse zu organisation von Hinweis Meldungen
 * @author Marcel
 */
@Named
@SessionScoped
public class Hints implements Serializable {
    /**
     * Liste mit Hinweisen
     */
    private List<String> hints;
    
    /**
     * Initialisiert alle Varibalen nachdem die Klasse erzeugt wurde
     */
    @PostConstruct
    private void init()
    {
        this.hints = new ArrayList<>();
    }
    
    /**
     * Gibt alle zwischengespeicherte Hinweise zurück und leer die Liste der Hinweise
     * 
     * @return Liste mit Strings 
     */
    public List<String> getHints()
    {
        List<String> hints = new ArrayList<>(this.hints);
        this.clear();
        return hints;
    }
    
    /**
     * Fügt einen hinweis zur Liste hinzu
     * 
     * @param hint Der hinzuzufügende Hinweis
     */
    public void addHint(String hint)
    {
        this.hints.add(hint);
    }
    
    /**
     * Entfernt einen Hinweis aus der Liste
     * 
     * @param hint Der zu entfernende Hinweis
     */
    public void removeHint(String hint)
    {
        this.hints.remove(hint);
    }
    
    /**
     * Entfernt alle Hinweise aus der Liste die ind hints sthen
     * 
     * @param hints Eine Liste mit den zu entferneden Hinweisen als String
     */
    public void removeHints(List<String> hints)
    {
        for(String hint: hints)
        {
            this.removeHint(hint);
        }
    }
    
    /**
     * leert die Hinweisliste
     */
    public void clear()
    {
        this.hints.clear();
    }
}
