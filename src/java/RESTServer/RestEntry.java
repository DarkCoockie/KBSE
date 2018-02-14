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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * Klasse für die Verarbeitung von Anfagen an die RES API für Entrys
 * 
 * @author Timo Laser
 */
@RequestScoped
@Path("/entry")
public class RestEntry {

    /**
     * Schnitstelle zur Hauptlogik und Persistence
     */
    @Inject
    private Controller controller;

    /**
     * Nimmt Anfragen direkt an /entry entgegen und gibt alle Entries zurück
     * 
     * @return Gibt Ein JsonObject mit einem JsonAray mit allen Entrys zurück, kann leer sein
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEntrys() {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Entry e : this.controller.getEntries()) {
            arrayBuilder.add(entrytoJson(e));
        }
        return Response.ok(objectBuilder.add("data", arrayBuilder.build()).build()).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Nimmt Anfragen an /entry/{username} entgegen
     * und gibt alle Entries die von dem Nutzer erstellt wuden zurück
     * 
     * @param userName Der Nutzename des Nutzers
     * @return  Gibt ein JsonObject mit einem JsonArray zurück, welches alle oder
     *          keine Entries die von dem Nutzer erstellt wurden zurück
     */
    @GET
    @Path("/user/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEntrysByUserName(@PathParam("userName") String userName) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Entry e : this.controller.getMemberEntries(userName)) {
            arrayBuilder.add(entrytoJson(e));
        }
        return Response.ok(objectBuilder.add("data", arrayBuilder.build()).build()).header("Access-Control-Allow-Origin", "*").build();

    }

    /**
     * Gibt ein Entry zu der übergebenen ID zurück
     * 
     * @param id Die ID des Entries
     * @return Gibt ein JsonObject zurück, bei Erfolg mit den Daten des Entry, ansonsten mit einer Fehlermeldung
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{entryId}")
    public Response getEntryById(@PathParam("entryId") int id) {
        Entry e;
        if ((e = this.controller.getEntry(id)) != null) {
            return Response.ok(this.entrytoJson(e)).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.ok(Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_FIND_ENTRY).build()).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Erstellt ein neues Entry via POST Anfage.
     * Erwartet ein JsonObject mit "name", "url", description" und "username"
     * 
     * @param o Ein JsonObject mit allen nötigen Entry Daten
     * @return Gibt einen leeren String zurück
     */
    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEntyPost(JsonObject o) {
        //TODO Bestätiegungs- oder Fehlermeldung zurück geben
        this.controller.addEntry(new Entry(o.getString("name"), o.getString("description"), o.getString("url"), o.getString("username")));
        return Response.ok("").header("Access-Control-Allow-Origin", "*").build();
    }
    
    /**
     * Erstellt ein neues Entry via GET Anfage.
     * Erwartet "name", "url", description"
     * und "username" als Query parameter
     * 
     * @param name Der Titel 
     * @param url URL zur Website 
     * @param description Beschreibung der Bewertung
     * @param userName Nutzername der das Entry erstellt
     * @return Gibt einen leeren String zurück
     */
    @GET
    @Path("/create")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEnty(@QueryParam("name") String name, @QueryParam("url") String url, @QueryParam("desc") String description, @QueryParam("user") String userName) {
        //TODO Bestätiegungs- oder Fehlermeldung zurück geben
        this.controller.addEntry(new Entry(name, description, url, userName));
        return Response.ok("").header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Erhöht Anzahl Sterne eines Entries. Die Entry-Id und der Nutzername müssen
     * im Pfad zu Resource enthalten sein: "entry/[entryId]/incStars/{userName}"
     * 
     * @param name Nutzername des Nutzers der die Anzahl der Sterne erhöht
     * @param id ID des Entries desen Sterne erhöht werden sollen
     * @return Bei fehlendem Nutzernamen wird "userName required!" zurückgegeben
     *         sosnt die Meldung des Controllers
     * @see Controller
     */
    @GET
    @Path("/{entryId}/incStars/{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response incEntryStart(@PathParam("userName") String name, @PathParam("entryId") int id) {
        if (name != null) {
            if (name != "") {
                return Response.ok(this.controller.incrementEntry(id, name)).header("Access-Control-Allow-Origin", "*").build();
            }
        }
        return Response.ok("userName required!").header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Veringert Anzahl Sterne eines Entries. Die Enry-Id und der Nutzername müssen
     * im Pfad zu Resource enthalten sein: "entry/[entryId]/decStars/{userName}"
     * 
     * @param name Nutzername des Nutzers der die Anzahl der Sterne veringert
     * @param id ID des Entries desen Sterne veringert werden sollen
     * @return Bei fehlendem Nutzernamen wird "userName required!" zurückgegeben
     *         sosnt die Meldung des Controllers
     * @see Controller
     */
    @GET
    @Path("/{entryId}/decStars/{userName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response decEntryStart(@PathParam("userName") String name, @PathParam("entryId") int id) {
        if (name != null) {
            if (name != null) {
                return Response.ok(this.controller.decrementEntry(id, name)).header("Access-Control-Allow-Origin", "*").build();
            }
        }
        return Response.ok("userName required!").header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Entfernt ein Entry. Entry-Id muss im Pfad zur Resource enthalten sein
     * /entry/delete/{entryId}
     * 
     * @param id Id des zu entfernen Entries
     * @return wird kein Entry zu der ID gefunden wird "Entry not Found!" zurückgegeben, ansonsten 
     *         die Meldung des Controllers
     * @see Controller
     */
    @GET
    @Path("/delete/{entryId}")
    @Produces("text/plain")
    public Response deleteEntry(@PathParam("entryId") int id) {
        Entry e;
        if ((e = this.controller.getEntry(id)) != null) {
            return Response.ok(this.controller.deleteEntry(id)).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.ok(Constants.ErrorMessages.CANT_FIND_ENTRY).header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Konvertiert ein Entry Objekt in ein JsonObjekt 
     *  
     * @param e Das zu konvertierende Entry Objekt
     * @return Ein JsonObjekt mit allen Entry Daten
     */
    private JsonObject entrytoJson(Entry e) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        return objectBuilder
                .add("id", e.getId())
                .add("name", e.getName())
                .add("description", e.getDescription())
                .add("url", e.getUrl())
                .add("stars", e.getStars())
                .add("username", e.getUser())
                .build();
    }

}
