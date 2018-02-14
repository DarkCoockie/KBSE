/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RESTServer;

import Controller.Controller;
import Persistence.Member;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * Klasse für die Verarbeitung von Anfagen an die RES API für Member
 * 
 * @author Timo Laser
 */
@RequestScoped
@Path("/member")
public class RestMember { 
    
     /**
     * Schnitstelle zur Hauptlogik und Persistence
     */
    @Inject
    private Controller controller;
    
    /**
     * Nimmt Anfragen direkt an /member entgegen und gibt alle Member zurück
     * 
     * @return Gibt Ein JsonObject mit einem JsonAray mit allen Member zurück, kann leer sein
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMembers(){
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for(Member m : this.controller.getMembers()){
            arrayBuilder.add(memberToJson(m));
        }
        return Response.ok(objectBuilder.add("data", arrayBuilder.build()).build()).header("Access-Control-Allow-Origin", "*").build();
    }
    
   /**
     * Gibt ein Member zu dem übergebenen Nutzernamen zurück
     * 
     * @param memberName Der Nutzername des Mebers
     * @return Gibt ein JsonObject zurück, bei Erfolg mit den Daten des Member, ansonsten mit einer Fehlermeldung
     */
    @GET
    @Path("/{memberName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEntrysByUserName(@PathParam("memberName") String memberName){
       Member m;
       if((m = this.controller.getMember(memberName)) != null){
           return Response.ok(this.memberToJson(m)).header("Access-Control-Allow-Origin", "*").build();
       }
        return Response.ok(Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_DELETE_USER).build()).header("Access-Control-Allow-Origin", "*").build();
    }
    
    /**
     * Fügt einen Member via POST Request hinzu. Die Funktion erwartet ein JsonObjekt
     * mit "name"
     * 
     * @param o Das JsonObjekt mit den zur erzeugung des Meber benötigten Daten
     * @return Gibt einen leeren String zurück
     */
    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMember(JsonObject o){
        this.controller.addUser(new Member(o.getString("name")));
        return Response.ok("").header("Access-Control-Allow-Origin", "*").build();
    }
    
    /**
     * Aktualisiert die Anzahl der Punkte des Nutzers. Erwartet ein JsonObjekt mit
     * "name" und "points"
     * 
     * @param o Das JsonObjekt mit "name" und "points"
     * @return Gibt ein JsonObject zurück, bei Erfolg mit den Daten des Member, ansonsten mit einer Fehlermeldung
     */
    @PUT
    @Path("/updatePoints")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMemberPoints(JsonObject o){
        Member m  = this.controller.getMember(o.getString("name"));
        if(m != null){
            m.setPoints(o.getInt("points"));
            this.controller.mergeMember(m);
            return Response.ok(memberToJson(m)).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.ok(Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_DELETE_USER).build()).header("Access-Control-Allow-Origin", "*").build();
    }
    /**
     * 
     * Erhöht die Anzahl Punkte des Nutzers. Der Nutzername muss im Pfad zur
     * Resource enthalten sein: "/member/{userName}/incPoints"
     * 
     * @param name Nutzername des Nutzers
     * @return Gibt ein JsonObject zurück, bei Erfolg mit den Daten des Member, ansonsten mit einer Fehlermeldung
     */
    @GET
    @Path("/{memberId}/incPoints")
    @Produces(MediaType.APPLICATION_JSON)
    public Response incMemberPoints(@PathParam("memberId") String name){
         Member m  = this.controller.getMember(name);
        if(m != null){
            m.setPoints(m.getPoints()+1);
            this.controller.mergeMember(m);
            return Response.ok(memberToJson(m)).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.ok(Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_DELETE_USER).build()).header("Access-Control-Allow-Origin", "*").build();
    }
    
     /**
     * 
     * Veringert die Anzahl Punkte des Nutzers. Der Nutzername muss im Pfad zur
     * Resource enthalten sein: "/member/{userName}/decPoints"
     * 
     * @param name Nutzername des Nutzers
     * @return Gibt ein JsonObject zurück, bei Erfolg mit den Daten des Member, ansonsten mit einer Fehlermeldung
     */
    @GET
    @Path("/{memberId}/decPoints")
    @Produces(MediaType.APPLICATION_JSON)
    public Response decMemberPoints(@PathParam("memberId") String name){
         Member m  = this.controller.getMember(name);
        if(m != null){
            m.setPoints(m.getPoints()-1);
            this.controller.mergeMember(m);
            return Response.ok(memberToJson(m)).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.ok(Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_DELETE_USER).build()).header("Access-Control-Allow-Origin", "*").build();
    }
    
    /**
     * 
     * Erstellt einen neuen Nutzer. Ist bereits ein Nutzer mit dem Nutzernamen
     * vorhanden wird kein neuer Nutzer erzeugt
     * 
     * @param name Nutzername des Nutzers
     * @return Gibt ein JsonObjekt mir "status" ok zurück
     */
    @GET
    @Path("/{memberName}/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("memberName") String name){
        Member m = new Member(name);
        this.controller.addUser(m);
        return Response.ok(Json.createObjectBuilder().add("status", "ok").add("name", m.getName()).build()).header("Access-Control-Allow-Origin", "*").build();
    }
    
    /**
     * Konvertiert ein Member Objekt in ein JsonObjekt
     * 
     * @param m Das zu konvertierende Member Objekt
     * @return Ein JsonObjekt mit "name" und "points"
     */
    public JsonObject memberToJson(Member m){
        return Json.createObjectBuilder()
                .add("name", m.getName())
                .add("points", m.getPoints())
                .build();
    }
    
}
