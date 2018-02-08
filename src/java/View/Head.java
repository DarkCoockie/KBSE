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
 * @author Timo
 */
@Named
@RequestScoped
public class Head implements Serializable{
    
    public String getHome(){
        return Constants.Constants.INDEX_PAGE;
    }
    
    public String getMemberPage(){
        return Constants.Constants.NEW_ENTRY_PAGE;
    }
}
