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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getAllEntrys() {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Entry e : this.controller.getEntries()) {
            arrayBuilder.add(entrytoJson(e));
        }
        return Response.ok(objectBuilder.add("data", arrayBuilder.build()).build()).header("Access-Control-Allow-Origin", "*").build();
    }

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

    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEnty(JsonObject o) {
        //TODO Bestätiegungs- oder Fehlermeldung zurück geben
        this.controller.addEntry(new Entry(o.getString("name"), o.getString("description"), o.getString("url"), o.getString("username")));
        return Response.ok("").header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @Path("/{entryId}/incStars")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response incEntryStart(JsonObject o, @PathParam("entryId") int id) {
        if (o != null) {
            if (o.getString("userName") != null) {
                return Response.ok(this.controller.incrementEntry(id, o.getString("userName"))).header("Access-Control-Allow-Origin", "*").build();
            }
        }
        return Response.ok("userName required!").header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @Path("/{entryId}/decStars")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response decEntryStart(JsonObject o, @PathParam("entryId") int id) {
        if (o != null) {
            if (o.getString("userName") != null) {
                return Response.ok(this.controller.decrementEntry(id, o.getString("userName"))).header("Access-Control-Allow-Origin", "*").build();
            }
        }
        return Response.ok("userName required!").header("Access-Control-Allow-Origin", "*").build();
    }

    @DELETE
    @Path("/{entryId}")
    @Produces("text/plain")
    public Response deleteEntry(@PathParam("entryID") int id) {
        Entry e;
        if ((e = this.controller.getEntry(id)) != null) {
            return Response.ok(this.controller.deleteEntry(id)).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.ok(Constants.ErrorMessages.CANT_FIND_ENTRY).header("Access-Control-Allow-Origin", "*").build();
    }

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
