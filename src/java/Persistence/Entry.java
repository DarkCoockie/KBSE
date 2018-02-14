/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entry Entity-Class für die Persistence
 * @author Marcel
 */
@Entity
public class Entry implements Serializable {
    
    /**
     * Eindeutige ID des Eitrags. Wird durch die Datenbank gesetzt
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    /**
     * Titel des Eintags
     */
    private String name;
    
    /**
     * Beschreibung über die Website
     */
    private String description;
    
    /**
     * Eindeutige URL zu der Website
     */
    private String url;
    
    /**
     * Nutzername des Nutzers der diesen Eintrag erstellt hat
     */
    private String userName;
    
    /**
     * Anzahl der Punkte/Stene die diese Website hat
     */
    private int stars = 0;
    
    public Entry(){}
    
    
    /**
     * 
     * 
     * @param name
     * @param description
     * @param url
     * @param userName 
     */
    public Entry(String name, String description, String url, String userName){
        this.name = name;
        this.description = description;
        this.url = url;
        this.userName = userName;
    }
    
    /**
     * @return Names des Nutzers welcher den Eintrag erstellt hat
     */
    public String getUser() {
        return userName;
    }

    /**
     * @param user Nutzeranem zum setzten des Nutzernamens für den Eintrag
     */
    public void setUser(String user) {
        this.userName = user;
    }

    /**
     * @return Titel des Eintrags 
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Titel der für den Eintrag gesetzt werden soll
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return Die ID des Eintrags 
     */
    public int getId() {
        return id;
    }

    /**
     * @return Bescheibung des Eintrags
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description Zu setzende Beschreibung für den Eintrag
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Die URL zur Website des Eintrags 
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url Zu setzende URL zur Website des Eintrags 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return Azahl Stene des Eintrags
     */
    public int getStars() {
        return stars;
    }

    /**
     * @param stars Zu setzende Stern Anzahl
     */
    public void setStars(int stars) {
        this.stars = stars;
    }
    
    /**
     * @return true wenn anzahl sterne größer als 0, sonst false
     */
    public boolean hasStars()
    {
        return this.stars > 0;
    }
}
