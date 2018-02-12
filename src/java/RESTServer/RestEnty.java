/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RESTServer;

import Controller.Controller;
import Persistence.Entry;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Timo
 */
@RequestScoped
@Path("/entry")
public class RestEnty {
    
    @Inject
    private Controller controller;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAllEntrys(){
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for(Entry e : this.controller.getEntries()){
            arrayBuilder.add(entrytoJson(e));
        }
        return objectBuilder.add("data", arrayBuilder.build()).build();
    }
    
    @GET
    @Path("/user/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAllEntrysByUserName(@PathParam("userName") String userName){
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for(Entry e : this.controller.getMemberEntries(userName)){
            arrayBuilder.add(entrytoJson(e));
        }
        return objectBuilder.add("data", arrayBuilder.build()).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{entryId}")
    public JsonObject getEntryById(@PathParam("entryId") int id){
        Entry e;
        if((e = this.controller.getEntry(id)) != null){
            return this.entrytoJson(e);
        }
        return Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_FIND_ENTRY).build();
    }
    
    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addEnty(JsonObject o){
        //TODO Bestätiegungs- oder Fehlermeldung zurück geben
        this.controller.addEntry(new Entry(o.getString("name"), o.getString("description"), o.getString("url"), o.getString("username")));
        return "";
    }
    
    
    @DELETE
    @Path("/{entryId}")
    @Produces("text/plain")
    public String deleteEntry(@PathParam("entryID") int id){
        Entry e;
        if((e = this.controller.getEntry(id)) != null){
            return this.controller.deleteEntry(id);
        }
        return Constants.ErrorMessages.CANT_FIND_ENTRY;
    }
    
    private JsonObject entrytoJson(Entry e){
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        return objectBuilder
                .add("name", e.getName())
                .add("description", e.getDescription())
                .add("url", e.getUrl())
                .add("stars", e.getStars())
                .add("username", e.getUser())
                .build();
    }
    
}
