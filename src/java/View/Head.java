/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * Gibt Konstanten für die Navigation zurück
 * 
 * @author Timo
 */
@Named
@RequestScoped
public class Head implements Serializable{
    
    /**
     * @return Den Pfad zur Index Seite
     */
    public String getHome(){
        return Constants.General.INDEX_PAGE;
    }
    
    /**
     * @return Den Pfad zur NewEntry Seite
     */
    public String getMemberPage(){
        return Constants.General.NEW_ENTRY_PAGE;
    }
}
