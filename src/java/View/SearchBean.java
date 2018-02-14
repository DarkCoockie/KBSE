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
 * Schnittstelle fuer Such- und Sortier-Funktionen
 * @author Marcel
 */
@ManagedBean
@RequestScoped
@Named
public class SearchBean {
    
    @Inject Hints hints;
    @Inject Session.ViewController vc;
    
    private String searchString;
    private List<Persistence.Entry> displayedEntries;
    
    private List<String> sortingOptions;
    private String currentSortingOption;
    
    @PostConstruct
    private void init()
    {
        this.populateSearchOptions();
        this.currentSortingOption = this.vc.getSearchOption();
        this.displayedEntries = this.vc.getEntries();
        this.searchString = this.vc.getSearchString();
        this.filter(this.searchString);
    }
    
    @PreDestroy
    private void demo()
    {
        this.vc.setSearchOption(this.currentSortingOption);
        this.vc.setSearchString(this.searchString);
    }
    
    /**
     *
     * @return      Liste moeglicher Sortier-Optionen
     */
    public List<String> getSortingOptions() {
        return sortingOptions;
    }
    
    /**
     *
     * @param sortingOptions Liste moeglicher Sortier-Optionen
     */
    public void setSotingOptions(List<String> sortingOptions) {
        this.sortingOptions = sortingOptions;
    }
    
    /**
     *
     * @return      Die aktive Sortier-Option
     */
    public String getCurrentSortingOption() {
        return currentSortingOption;
    }

    /**
     *
     * @param currentSortingOption Die aktive Sortier-Option
     */
    public void setCurrentSortingOption(String currentSortingOption) {
        this.currentSortingOption = currentSortingOption;
    }

    /**
     *
     * @return      Der String zur Filterung der Eintraege
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     *
     * @param searchString  Der String zur Filterung der Eintraege
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     *
     * @return      Liste der anzuzeigenden Einträge
     */
    public List<Entry> getDisplayedEntries() {
        return displayedEntries;
    }

    /**
     *
     * @param displayedEntries Liste der anzuzeigenden Einträge
     */
    public void setDisplayedEntries(List<Entry> displayedEntries) {
        this.displayedEntries = displayedEntries;
    }
    
    /**
     * Fuellt die Such-Optionen mit Standardwerten
     * @see Constants.OrderBy
     */
    private void populateSearchOptions()
    {
        this.sortingOptions = new ArrayList<>();
        this.sortingOptions.add(Constants.OrderBy.NAME);
        this.sortingOptions.add(Constants.OrderBy.RATING);
        this.sortingOptions.add(Constants.OrderBy.USER);
    }
    
    /**
     * Empfaenger fuer die das Event der Aenderung des searchString
     * @param e
     * @return null
     */
    public String filterEvent(ValueChangeEvent e)
    {
        this.filter(e.getNewValue().toString());
        return null;
    }
    
    /**
     * Filtert die Eintraege
     * @param value String, nach dem in den Eintraegen gesucht werden soll
     */
    private void filter(String value)
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
            this.sortByOption(this.currentSortingOption);
        }
    }
    
    /**
     * Empfaenger fuer die das Event der Aenderung der aktive Sortier-Option
     * @param e
     * @return null
     */
    public String sortByOptionEvent(ValueChangeEvent e)
    {
        this.sortByOption(e.getNewValue().toString());
        return null;
    }
    
    /**
     * Sortiert die Eintraege nach dem angegebenen Kriterium
     * @param option
     */
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
                this.hints.addHint(Constants.ErrorMessages.INVALID_INPUT);
            }
        }
    }
}
