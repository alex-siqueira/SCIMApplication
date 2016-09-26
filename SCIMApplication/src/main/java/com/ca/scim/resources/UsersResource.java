package com.ca.scim.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.ca.scim.datatypes.Resources;
import com.ca.scim.datatypes.User;
import com.ca.scim.datatypes.UserSearch;
import com.ca.scim.repository.UserRepository;
import com.google.gson.Gson;

@Path ("/Users")
public class UsersResource {
	
    @Context
    private UriInfo context;	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getUsers(@DefaultValue("*") @QueryParam("filter") String filter, @DefaultValue("userName") @QueryParam("attributes") String attributes){
		Response response = null;
		
		UserRepository userRepo = UserRepository.getInstance();
		List<User> users = userRepo.getUsers(filter);

		if (users != null && !users.isEmpty()){
			if (attributes.split(",").length > 1) {
				Resources<User> resources = new Resources<User>();
				resources.setResources(users);
				response = Response.ok(getJsonFromUser(resources)).build();				
			} else {
				Resources<UserSearch> resources = new Resources<UserSearch>();
				resources.setResources(userRepo.getUsersSearch(filter));
				response = Response.ok(getJsonFromUser(resources)).build();	
			}
		} else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getUser(@PathParam("id") String id){
		Response response = null;
		
		UserRepository userRepo = UserRepository.getInstance();
		User user = userRepo.getUser(id);
		
		if (user != null){
			response = Response.ok(getJsonFromUser(user)).build();
		} else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		return response;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(String body){
		Response response = null;

		User user = getUserFromJson(body);
		UserRepository userRepo = UserRepository.getInstance();
		
		try {
			response = Response.created(userRepo.addUser(user)).build();
		} catch (Exception ex){
			response = Response.serverError().build();
		}
		
		return response;
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteUser(@PathParam("id") String id){
		Response response = null;
		
		UserRepository userRepo = UserRepository.getInstance();
		User user = userRepo.getUser(id);
		
		if (user != null){
			userRepo.deleteUser(user);
			response = Response.noContent().build();
		} else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		return response;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response editUser(@PathParam("id") String id, String body){
		Response response = null;

		User user = getUserFromJson(body);		
		UserRepository userRepo = UserRepository.getInstance();
		
		if (userRepo.editUser(user)) {
			response = Response.noContent().build();
		} else {
			response = Response.status(Status.NOT_FOUND).build();
		}
		
		return response;
	}
	
	private String getJsonFromUser(Object obj){
		Gson gson = new Gson();
		return (gson.toJson(obj));
	}
	
	private User getUserFromJson(String json){
		Gson gson = new Gson();
		return (gson.fromJson(json, User.class));
	}
}
