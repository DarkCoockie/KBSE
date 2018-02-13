/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constants;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Marcel
 */
public class General {
    public final static String SUCCESS = "success";
    
    public final static String PROJECT_PU_NAME = "KBSE_ProjektPU";
    
    public final static String NEW_ENTRY_PAGE = "/newEntry.xhtml?faces-redirect=true";
    public final static String INDEX_PAGE = "/index.xhtml?faces-redirect=true";
    public final static String INDEX_PAGE_NO_REDIRECT = "/index.xhtml";
    
    public final static List<String> SEARCHOPTIONS = Arrays.asList("Name", "Punkte", "Benutzer", "");
}
