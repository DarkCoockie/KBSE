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
    public JsonObject getAllMembers(){
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        for(Member m : this.controller.getMembers()){
            arrayBuilder.add(memberToJson(m));
        }
        return objectBuilder.add("data", arrayBuilder.build()).build();
    }  
    
    @GET
    @Path("/{memberName}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAllEntrysByUserName(@PathParam("memberName") String memberName){
       Member m;
       if((m = this.controller.getMember(memberName)) != null){
           return this.memberToJson(m);
       }
        return Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_DELETE_USER).build();
    }
    
    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addMember(JsonObject o){
        this.controller.addUser(new Member(o.getString("name")));
        return "";
    }
    
    @PUT
    @Path("/updatePoints")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject updateMemberPoints(JsonObject o){
        Member m  = this.controller.getMember(o.getString("name"));
        if(m != null){
            m.setPoints(o.getInt("points"));
            this.controller.mergeMember(m);
            return memberToJson(m);
        }
        return Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_DELETE_USER).build();
    }
    
    @GET
    @Path("/{memberId}/decPoints")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject decMemberPoints(@PathParam("memberId") String name){
         Member m  = this.controller.getMember(name);
        if(m != null){
            m.setPoints(m.getPoints()-1);
            this.controller.mergeMember(m);
            return memberToJson(m);
        }
        return Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_DELETE_USER).build();
    }
    
    @GET
    @Path("/{memberId}/incPoints")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject incMemberPoints(@PathParam("memberId") String name){
         Member m  = this.controller.getMember(name);
        if(m != null){
            m.setPoints(m.getPoints()+1);
            this.controller.mergeMember(m);
            return memberToJson(m);
        }
        return Json.createObjectBuilder().add("error", Constants.ErrorMessages.CANT_DELETE_USER).build();
    }
    
    public JsonObject memberToJson(Member m){
        return Json.createObjectBuilder()
                .add("name", m.getName())
                .add("points", m.getPoints())
                .build();
    }
    
}
