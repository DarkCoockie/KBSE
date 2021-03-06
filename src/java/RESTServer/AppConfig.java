/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RESTServer;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Timo Laser
 */
@ApplicationPath("/resources")
public class AppConfig extends Application {
    
    
    /**
     * 
     * @return Ein Set aller REST Klassen
     */
    @Override
    public Set<Class<?>> getClasses(){
    Set<Class<?>> resources = new java.util.HashSet<>();
    try { // customize Jersey 2.0 JSON provider:
        Class jsonProvider = Class
        .forName("org.glassfish.jersey.moxy.json.MoxyJsonFeature");
        resources.add(jsonProvider);
    } catch (ClassNotFoundException ex) {}
    
    addRestResourceClasses(resources);
    return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(RESTServer.RestEntry.class);
        resources.add(RESTServer.RestMember.class);
    }

    
}
