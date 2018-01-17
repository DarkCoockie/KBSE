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
public class User implements Serializable{
    @Id
    private String username;
    private Map<Integer, Integer> ratings; // id, stars
    private int stars;
    
    public User(String username)
    {
        this.username = username;
        this.ratings = new HashMap<>();
        this.stars = 10;
    }
    
    public String incRating(int id)
    {
        if(this.stars <= 0)
        {
            return Constants.ErrorMessages.LIMIT;
        }
        
        Integer value = this.ratings.get(id);
        if(value == null)
        {
            this.ratings.put(id, 1);
        }
        else
        {
            this.ratings.put(id, value + 1);
        }
        this.stars--;
        return Constants.Constants.SUCCESS;
    }
    
    public String decRating(int id)
    {
        if(this.stars >= 10)
        {
            return Constants.ErrorMessages.LIMIT;
        }
        
        Integer value = this.ratings.get(id);
        if(value == null)
        {
            return Constants.ErrorMessages.ENTRY_NOT_FOUND;
        }
        else if(value <= 0)
        {
            return Constants.ErrorMessages.LIMIT;
        }
        this.ratings.put(id, value - 1);
        if(value == 1)
        {
            this.ratings.remove(id);
        }
        
        this.stars++;
        
        return Constants.Constants.SUCCESS;
    }

    public Map<Integer, Integer> getRatings() {
        return ratings;
    }
    
    public Integer getRating(int id)
    {
        return this.ratings.get(id);
    }

    public int getStars() {
        return stars;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
