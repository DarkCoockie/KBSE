/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Persistence.Entry;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Marcel
 */
@ManagedBean
@RequestScoped
@Named
public class SearchBean {
    
    @Inject Session.ViewController vc;
    
    @ManagedProperty(value= "#{param.searchString}")
    private String searchString;
    private List<Persistence.Entry> searchResults;

    @PostConstruct
    private void init()
    {
        this.searchResults =  vc.getEntries();
    }
    
    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Entry> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Entry> searchResults) {
        this.searchResults = searchResults;
    }
    
    public String searchByTitle(String title)
    {
        title = title.trim();
        
        if(title.equals(""))
        {
            this.searchResults = this.vc.getEntriesStatic();
        }
        else
        {
            this.searchResults = this.vc.searchByTitle(title);
        }
        return Constants.General.INDEX_PAGE_NO_REDIRECT + "?value=#{searchBean.searchString}";
    }
    
    public String searchStringValueChanged(AjaxBehaviorEvent event)
    {
        return searchByTitle(this.searchString);
    }

}
