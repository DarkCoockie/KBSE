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
 *
 * @author Marcel
 */
@Entity
public class Member implements Serializable {
    @Id
    private String name;
    private int points = 10;
    private Map<Integer, Integer> ratings;

    public Member() {
    }

    public Member(String name)
    {
        this.name = name;
        this.ratings = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Map<Integer, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(Map<Integer, Integer> ratings) {
        this.ratings = ratings;
    }
    
    public String incRating(Persistence.Entry entry)
    {
        Integer rating = this.ratings.get(entry.getId());
        if(rating != null)
        {
            this.ratings.put(entry.getId(), (int) rating +1);
            --this.points;
            entry.setStars(entry.getStars() + 1);
        }
        else
        {
            this.ratings.put(entry.getId(), 1);
            --this.points;
            entry.setStars(entry.getStars() + 1);
        }
        
        return Constants.Constants.SUCCESS;
    }
    
    public String decRating(Persistence.Entry entry)
    {
        Integer rating = this.ratings.get(entry.getId());
        if(rating == null)
        {
            return Constants.ErrorMessages.CANT_FIND_ENTRY;
        }
        else
        {
            this.ratings.put(entry.getId(), (int) rating -1);
            ++this.points;
            entry.setStars(entry.getStars() - 1);
        }
        
        if(rating.equals(1))
            this.ratings.remove(entry.getId());
        
        return Constants.Constants.SUCCESS;
    }
}
