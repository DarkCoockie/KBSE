/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Persistence.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
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
    
    private String searchString;
    private List<Persistence.Entry> displayedEntries;
    
    private List<String> searchOptions;
    private String currentSearchOption;
    
    @PostConstruct
    private void init()
    {
        this.populateSearchOptions();
        this.currentSearchOption = this.vc.getSearchOption();
        this.displayedEntries = this.vc.getEntries();
        this.searchString = this.vc.getSearchString();
        this.filter(this.searchString);
    }
    
    @PreDestroy
    private void demo()
    {
        this.vc.setSearchOption(this.currentSearchOption);
        this.vc.setSearchString(this.searchString);
    }
    

    public List<String> getSearchOptions() {
        return searchOptions;
    }

    public void setSearchOptions(List<String> searchOptions) {
        this.searchOptions = searchOptions;
    }

    public String getCurrentSearchOption() {
        return currentSearchOption;
    }

    public void setCurrentSearchOption(String currentSearchOption) {
        this.currentSearchOption = currentSearchOption;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Entry> getSearchResults() {
        return displayedEntries;
    }

    public void setSearchResults(List<Entry> searchResults) {
        this.displayedEntries = searchResults;
    }
    
    private void populateSearchOptions()
    {
        this.searchOptions = new ArrayList<>();
        this.searchOptions.add(Constants.OrderBy.NAME);
        this.searchOptions.add(Constants.OrderBy.RATING);
        this.searchOptions.add(Constants.OrderBy.USER);
    }
    
    public String filterEvent(ValueChangeEvent e)
    {
        return this.filter(e.getNewValue().toString());
    }
    
    private String filter(String value)
    {
        String filter = value.trim();
        
        if(filter.equals(""))
        {
            this.displayedEntries = this.vc.getEntries();
        }
        else
        {
            this.displayedEntries = this.vc.filter(filter);
        }
        
        if(this.displayedEntries.size() > 0)
        {
            this.sortByOption(this.currentSearchOption);
        }
        
        return Constants.General.INDEX_PAGE_NO_REDIRECT + "?value=#{searchBean.searchString}";
    }
    
    public void sortByOptionEvent(ValueChangeEvent e)
    {
        this.sortByOption(e.getNewValue().toString());
    }
    
    private void sortByOption(String option)
    {
        if(this.displayedEntries.size() > 0 && option != null)
        {
            if(option.equals(Constants.OrderBy.NAME))
            {
                Collections.sort(this.displayedEntries, (Persistence.Entry p1, Persistence.Entry p2) 
                    -> p1.getName().compareTo(p2.getName()));
            }
            else if(option.equals(Constants.OrderBy.RATING))
            {
                Collections.sort(this.displayedEntries, (Persistence.Entry e1, Persistence.Entry e2) 
                    -> e2.getStars() - e1.getStars());
            }
            else if(option.equals(Constants.OrderBy.USER))
            {
                Collections.sort(this.displayedEntries, (Persistence.Entry e1, Persistence.Entry e2) 
                    -> e1.getUser().compareTo(e2.getUser()));
            }
            else
            {
                this.displayedEntries = this.vc.getEntries();
            }
        }
    }
}
