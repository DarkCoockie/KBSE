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
    private Map<Integer, Integer> entries = new HashMap<>();

    public Member() {
    }

    public Member(String name)
    {
        this.name = name;
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

    public Map<Integer, Integer> getEntries() {
        return entries;
    }

    public void setEntries(Map<Integer, Integer> entries) {
        this.entries = entries;
    }
    
}
