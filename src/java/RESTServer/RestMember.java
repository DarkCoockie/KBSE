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
 * @author Timo
 */
@RequestScoped
@Path("/member")
public class RestMember { 
    
    @Inject
    private Controller controller;
    
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
    
    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMember(JsonObject o){
        this.controller.addUser(new Member(o.getString("name")));
        return Response.ok("").header("Access-Control-Allow-Origin", "*").build();
    }
    
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
    
    @GET
    @Path("/{memberName}/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("memberName") String name){
        Member m = new Member(name);
        this.controller.addUser(m);
         return Response.ok(Json.createObjectBuilder().add("status", "ok").add("name", m.getName()).build()).header("Access-Control-Allow-Origin", "*").build();
    }
    
    
    public JsonObject memberToJson(Member m){
        return Json.createObjectBuilder()
                .add("name", m.getName())
                .add("points", m.getPoints())
                .build();
    }
    
}
