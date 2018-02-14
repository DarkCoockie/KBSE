/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *  Meber Entity-Class fü die Persistence
 * @author Marcel Schulte und Timo Laser
 */
@Entity
public class Member implements Serializable {

    /**
     * Eindeutiger Nutzername
     */
    @Id
    private String name;
    
    /**
     * (Rest)Menge an Punkten die vergeben werden können
     */
    private int points = 10;
    
    /**
     * Auflistung aller Einträge fü die Punkte vergeben wuden
     * Key ist id des Eintrags, value ist anzahl verebener Punkte
     */
    private Map<Integer, Integer> ratings;

    public Member() {
    }
    
    public Member(String name) {
        this.name = name;
        this.ratings = new HashMap<>();
    }

    /**
     * @return nutzernae des Nutzers
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Zu setzender Nutzername des Nutzers
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Anzahl Punkte des Nutzers
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points Zu setzende anzahl Punkte des Nutzers
     */
    public void setPoints(int points) {
        this.points = points;
    }
    
    /**
     * @return Map mit den vergebenen Punkten fü einen bestimmten Eintrag
     */
    public Map<Integer, Integer> getRatings() {
        return ratings;
    }

    /**
     * @param ratings Zu setzende Map für die vergabe der Punkte für bestimmte Einträge
     */
    public void setRatings(Map<Integer, Integer> ratings) {
        this.ratings = ratings;
    }

    /**
     * Erhöht den Wert des Eintrags für den der Nutzer Punkte vergben hat und
     * aktualisiert die Map
     * 
     * @param entry Das Entry Object des Eintrags desen Sterne erhöht werden sollen
     * @return Gibt "SUCCESS" zurück
     */
    public String incRating(Persistence.Entry entry) {
        Integer rating = this.ratings.get(entry.getId());
        if (rating != null) {
            this.ratings.put(entry.getId(), (int) rating + 1);
            --this.points;
            entry.setStars(entry.getStars() + 1);
        } else {
            this.ratings.put(entry.getId(), 1);
            --this.points;
            entry.setStars(entry.getStars() + 1);
        }

        return Constants.General.SUCCESS;
    }

    /**
     * Veringert den Wert des Eintrags für den der Nutzer Punkte vergben hat und
     * aktualisiert die Map
     * 
     * @param entry Das Entry Object des Eintrags desen Sterne veringert werden sollen
     * @return Gibt "SUCCESS" zurück
     */
    public String decRating(Persistence.Entry entry) {
        Integer rating = this.ratings.get(entry.getId());
        if (rating == null) {
            return Constants.ErrorMessages.CANT_FIND_ENTRY;
        } else {
            this.ratings.put(entry.getId(), (int) rating - 1);
            ++this.points;
            entry.setStars(entry.getStars() - 1);
        }

        if (rating.equals(1)) {
            this.ratings.remove(entry.getId());
        }

        return Constants.General.SUCCESS;
    }
}
